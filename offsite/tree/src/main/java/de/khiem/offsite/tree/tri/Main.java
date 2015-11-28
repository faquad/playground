/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.tree.tri;

import com.google.common.primitives.Longs;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.collections4.trie.KeyAnalyzer;
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

        search(t, "1");
        search(t, "1.2");
        search(t, "1.3");
        search(t, "1.4");
        search(t, "1.5");
        search(t, "1.5.6");
        search(t, "1.5.7");

    }

    void search(PatriciaTrie<O> t, String k) {
        O e = t.get(k);
        if (e == null) {
            System.out.println(k + " not found");
        } else {
            System.out.println("found " + e.toString());
            SortedMap m = t.tailMap(k);
            System.out.println("tailmap of  " + k + ":" + m.toString());
            SortedMap m2 = t.headMap(k);
            System.out.println("headmap of  " + k + ":" + m2.toString());
            
            
            
        }
    }

    void add(PatriciaTrie t, D d, String p) {
        String _p = "".equals(p) ? (d.id + "") : (p + "." + d.id);
        t.put(_p, d);
        for (O n : d.children) {
            String np = _p + "." + n.id;
            if (n instanceof D) {
                add(t, (D) n, _p);
            } else {
                t.put(np, n);
            }
        }
    }

    
    class KA extends KeyAnalyzer<byte[]>{

        @Override
        public int bitsPerElement() {
            return Longs.BYTES*8;
        }

        @Override
        public int lengthInBits(byte[] key) {
            return key.length*8;
        }

        @Override
        public boolean isBitSet(byte[] arg0, int arg1, int arg2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int bitIndex(byte[] arg0, int arg1, int arg2, byte[] arg3, int arg4, int arg5) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isPrefix(byte[] arg0, int arg1, int arg2, byte[] arg3) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
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
}
