
package de.khiem.offsite.tree.nf;

import com.netflix.nfgraph.OrdinalIterator;
import com.netflix.nfgraph.build.NFBuildGraph;
import com.netflix.nfgraph.compressed.NFCompressedGraph;
import com.netflix.nfgraph.spec.NFGraphSpec;
import com.netflix.nfgraph.spec.NFNodeSpec;
import com.netflix.nfgraph.spec.NFPropertySpec;
import com.netflix.nfgraph.util.OrdinalMap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kimyoung
 */
public class BoxGraph {
    final String NODE_TYPE ="N";
    NFBuildGraph g ;
    OrdinalMap<Node> nodes =new OrdinalMap<>();
    
    public BoxGraph() {
        g = createGr();
    }
    
    private void buid() throws IOException{
        AtomicLong id = new AtomicLong(0);

        FolderNode root=new FolderNode(id.incrementAndGet(), 0l, "Root");
        addHierrchie(null, root);
        
        FolderNode f1=new FolderNode(id.incrementAndGet(), root.id, "Folder 1");
        addHierrchie(root, f1);
        
        FolderNode f2=new FolderNode(id.incrementAndGet(), root.id, "Folder 2");
        addHierrchie(root, f2);
        
        
        FolderNode f11=new FolderNode(id.incrementAndGet(), f1.id, "F1.1");
        addHierrchie(f1, f11);
        
        
        FolderNode f12=new FolderNode(id.incrementAndGet(), f1.id, "F1.2");
        addHierrchie(f1, f12);
        
        
        FolderNode f21=new FolderNode(id.incrementAndGet(), f2.id, "F2.1");
        addHierrchie(f2, f21);
        
        
        FolderNode f22=new FolderNode(id.incrementAndGet(), f2.id, "F2.2");
        addHierrchie(f2, f22);
        
        FileNode fi1 = new FileNode(id.incrementAndGet(), f11.id, "file1");
        addHierrchie(f11, fi1);
        
        NoteNode n1 = new NoteNode(id.incrementAndGet(), f12.id, "node1");
        addHierrchie(f12, n1);
        
        
        FileNode fi2 = new FileNode(id.incrementAndGet(), f21.id, "file2");
        addHierrchie(f21, fi2);
        
        NoteNode n2 = new NoteNode(id.incrementAndGet(), f22.id, "node2");
        addHierrchie(f22, n2);
        
        
        NFCompressedGraph c = g.compress();
        byte[] blob;
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()){
            c.writeTo(os);
            blob = os.toByteArray();
            System.out.println("compressGraph size:" + blob.length);
        }

        System.out.println("downwards...");
        downwards(c, root);

        System.out.println("==============");
        System.out.println("backwards...");

        upwards(c, fi2);
        upwards(c, f22);
    }
    
    private void downwards(NFCompressedGraph c, Node parent){        
        int nodeId = nodes.get(parent);
        OrdinalIterator it = c.getConnectionIterator(NODE_TYPE, nodeId, "children");
        int nodeOrd = it.nextOrdinal();
        
        while (nodeOrd !=OrdinalIterator.NO_MORE_ORDINALS){
            Node n = nodes.get(nodeOrd);
            System.out.println("parent:" + parent.toString() + ", chilren:" + n.toString());
            downwards(c, n);
            nodeOrd=it.nextOrdinal();
        }
    }
    
    
    private void upwards(NFCompressedGraph c, Node child){        
        int cOrd = nodes.get(child);
        OrdinalIterator it = c.getConnectionIterator(NODE_TYPE, cOrd, "parent");
        int nodeOrd = it.nextOrdinal();
        
        while (nodeOrd !=OrdinalIterator.NO_MORE_ORDINALS){
            Node n = nodes.get(nodeOrd);
            System.out.println("node:" + child.toString() + ", parent:" + n.toString());
            downwards(c, n);
            nodeOrd=it.nextOrdinal();
        }
    }
    
    void addHierrchie(FolderNode parent, Node node){
        boolean hasparent = parent!=null;
        if (hasparent){
            int pInd = nodes.add(parent);
            int cInd = nodes.add(node);
            g.addConnection(NODE_TYPE, pInd, "children", cInd);
            g.addConnection(NODE_TYPE, cInd, "parent",pInd);
        //
        }else {
             nodes.add(node);
        }
    }
    
    
    
    NFBuildGraph createGr(){
        NFGraphSpec schema = new NFGraphSpec(
                new NFNodeSpec(NODE_TYPE, 
                          new NFPropertySpec("parent", NODE_TYPE, NFPropertySpec.SINGLE),
                           new NFPropertySpec("children", NODE_TYPE, NFPropertySpec.MULTIPLE)
                )
        );
        return new NFBuildGraph(schema);
    }
    
    
    class FileNode extends Node{

        public FileNode(long id, long parent, String name) {
            super(id, parent, name);
        }
        
                
    }
    
    class FolderNode extends Node {

        public FolderNode(long id, long parent, String name) {
            super(id, parent, name);
        }
     
     }
     
    class NoteNode extends Node {

        public NoteNode(long id, long parent, String name) {
            super(id, parent, name);
        }
     
     }
    
    class Node {
        final long id;
        final long parent;
        final String name;

        public Node(long id, long parent, String name) {
            this.id = id;
            this.parent = parent;
            this.name = name;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Node other = (Node) obj;
            if (this.id != other.id) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Node{" + "id=" + id + ", parent=" + parent + ", name=" + name + '}';
        }
        
        
    }
    
    
    public static void main(String[] args) {
        try {
            new BoxGraph().buid();
        } catch (IOException ex) {
            Logger.getLogger(BoxGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
