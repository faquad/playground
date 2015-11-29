/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.tree.tri;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.collections4.trie.PatriciaTrie;

/**
 *
 * @author kimyoung
 */
public class Main {

    public static void main(String[] args) {
        new Main().test1();
    }

    void test1() {
        PatriciaTrie<O> t = new PatriciaTrie<>();

        AtomicLong id = new AtomicLong();
        D r = new D(id.incrementAndGet()); //1
        r.children.add(new M(id.incrementAndGet())); //1.2
        r.children.add(new M(id.incrementAndGet())); //1.3
        r.children.add(new F(id.incrementAndGet())); //1.4

        D f = new D(id.incrementAndGet()); //1.5
        f.children.add(new F(id.incrementAndGet())); //1.5.6
        f.children.add(new M(id.incrementAndGet()));//1.5.7
        r.children.add(f);

        add(t, r, "");
        FT ft = new FT(r);
        
        search(t, "1");
        System.out.println("FT: " + ft.findByKey("1"));
        System.out.println("==============");
        search(t, "1.2");
        System.out.println("FT: " + ft.findByKey("1.2"));
        System.out.println("==============");
        search(t, "1.3");
        System.out.println("FT: " + ft.findByKey("1.3"));
        System.out.println("==============");
        search(t, "1.4");
        System.out.println("FT: " + ft.findByKey("1.4"));
        System.out.println("==============");
        search(t, "1.5");
        System.out.println("FT: " + ft.findByKey("1.5"));
        System.out.println("==============");
        search(t, "1.5.6");
        System.out.println("FT: " + ft.findByKey("1.5.6"));
        System.out.println("==============");
        search(t, "1.5.7");
        System.out.println("FT: " + ft.findByKey("1.5.7"));
        
        System.out.println("==================");
        
        //System.out.println("from 1, 3 layers:"  + ft.findSubs("1", 3));
        System.out.println("from 1, 1 layers:"  + ft.findSubs("1", 1));
        //System.out.println("from 1.5, 1 layers:"  + ft.findSubs("1.5", 1));
        //System.out.println("from 1.5, 2 layers:"  + ft.findSubs("1.5", 2));
        
        
        /*
        searchWithId(t, 1l);
        searchWithId(t, 2l);
        searchWithId(t, 3l);
        searchWithId(t, 4l);
        searchWithId(t, 5l);
        searchWithId(t, 6l);
        searchWithId(t, 7l);
        */
        
    }

    void searchWithId(PatriciaTrie<O> t, long id){
        O e = t.get(id+"");
        if (e!=null){
            System.out.println(" found (root) ?" );
        }else{
            Map.Entry<String, O> entry = t.select("." + id);
            System.out.println("found byID:" + entry);
        }
    }
    
    void search(PatriciaTrie<O> t, String k) {
        O e = t.get(k);
        if (e == null) {
            System.out.println(k + " not found");
        } else {
            System.out.println("found " + e.toString());
            String childrenPref= k+".";
            SortedMap m = t.prefixMap(childrenPref); // tailMap(k);
            System.out.println("prefixmap(children) of  " + k + ":" + m.toString());
//            SortedMap m2 = t.headMap(k);
//            System.out.println("headmap of  " + k + ":" + m2.toString());                     
        }
    }

    void add(PatriciaTrie t, D d, String p) {
        String _p = "".equals(p) ? (d.id + "") : (p + "." + d.id);
        t.put(_p, d);
        d.children.stream().forEach((n) -> {
            String np = _p + "." + n.id;
            if (n instanceof D) {
                add(t, (D) n, _p);
            } else {
                t.put(np, n);
            }
        });        
    }
    
    class O {

        protected final long id;
        protected final String n;

        O(long id) {
            this.id = id;
            n = getClass().toString() + " " + id;
        }

        @Override
        public String toString() {
            return "[" + n + "]";
        }

    }

    class F extends O {

        F(long id) {
            super(id);
        }
    }

    class D extends O {

        private List<O> children;

        D(long id) {
            super(id);
            children = new ArrayList<>();
        }

    }

    class M extends O {

        public M(long id) {
            super(id);
        }
    }
    
    class FT implements FSTree<String, O>{
        PatriciaTrie<O> pt;
        Map<Long,String> paths;
        String rootKey;
        
        public FT(O root) {
            Preconditions.checkArgument(root instanceof D);
            pt=new PatriciaTrie<>();
            paths=new ConcurrentHashMap<>();
            this.rootKey = add(root, "");
        }

        @Override
        public String getRootKey() {
            return rootKey;
        }
        
        
        @Override
        public String findKey(O n) {
            return paths.get(n.id);
        }

        @Override
        public O findByKey(String k) {
            return pt.get(k);
        }

        @Override
        public String addToNode(String k, O n) {
            O p= pt.get(k);
            Preconditions.checkNotNull(p);
            return add(n, k);
        }

        @Override
        public Collection<O> findSubs(String k, int depth) {
            SortedMap<String, O> sorted = pt.prefixMap(k+".");
            final int l=k.split("\\.").length;
            return Maps.filterKeys(sorted, (String input) -> input.split("\\.").length-l <=depth).values();
        }
        
        String add(O n, String p) {
            String _p = "".equals(p) ? (n.id + "") : (p + "." + n.id);

            pt.put(_p, n);
            paths.put(n.id, _p);

            if (n instanceof D) {
                D d = (D) n;
                d.children.stream().forEach((c) -> {
                    add(c, _p);
                });
            }
            System.out.println(n + "->" + _p);
            return _p;
        }
    }
}
