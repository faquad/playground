
package de.khiem.offsite.tree.im;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.stevewedig.blog.apache.commons.lang3.StringUtils;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.node_graph.TreeClass;
import com.stevewedig.blog.value_objects.ValueMixin;
import de.khiem.offsite.tree.BoxNode;
import de.khiem.offsite.tree.NodeType;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author kimyoung
 */
public class BoxTree {
     
    
    Map<String, Node> nodes = HashBiMap.create();            
    Set<String> unreads=new HashSet<>();
    
    void nextNode(BoxNode parent, BoxNode child){
       String pPath;
       if (parent==null){
           pPath = "/" ;
       }else {
           Node pN = nodes.get(parent.getId());
           pPath = pN.path();
       }
       this.nextNode(pPath, child);
    }
    
    void nextNode(String parentPath, BoxNode child){
        TNode n = new TNode(parentPath, child.getId(), child.getType());
        nodes.put(child.getId()  ,n);
    }
    
    void buildTree(){
    //    NodeTree tree =new NodeTree(null, null)
    }
    
    void move(String id, String newParent) {
        //get children from tree, for each child update path, 
        //query grandCh in tree, update according toi
    }

    ;
    void add() {
    }

    ;
    void delete(String nodeId) {
        //find all children from tree. -> ids, remove from nodes, rebuild tree 
    }

    boolean isUnread(String parent, String id, NodeType t){
        
        return false;
    }
    
    int unread(String id){
        return 0;
    }
    
    void addUnreadFile(String id, String parent){
        
    }
    
    void addUnreadNote(String id, String parent){
    
    }
    
    void addUnreadChat(String id){
    
    }
    
    ;
    
    
    static class TNode extends ValueMixin implements Node {

        final NodeType type;
        final String id;
        final String path;

        private Optional<String> parentPath;
        private ImmutableList<String> pathParts;
        private ImmutableSet<String> parentIds;

        private TNode(final String parentPath, final String id, final NodeType type) {
            this.type = type;
            this.id = id;
            if (NodeType.FOLDER.equals(type)) {
                this.path = parentPath + id + "/";
            } else {
                this.path = parentPath + id;
            }
        }

        
        @Override
        public boolean isRoot() {
            return "/".equals(path);
        }

        @Override
        protected Object[] fields() {
            return array("id", id);
        }

        @Override
        public String path() {
            return path;
        }

        @Override
        public NodeType type() {
            return this.type;
        }

        @Override
        public ImmutableSet<String> parentIds() {
            if (parentIds == null) {

                if (isRoot()) {
                    parentIds = ImmutableSet.of();
                } else {
                    parentIds = ImmutableSet.of(parentPath().get());
                }
            }
            return parentIds;
        }

        @Override
        public String id() {
            return this.id;
        }

        @Override
        public Optional<String> parentPath() {
            if (parentPath == null) {

                if (isRoot()) {
                    parentPath = Optional.absent();
                } else if (pathParts().size() == 1) {
                    parentPath = Optional.of("/");
                } else {
                    List<String> parts = Lists.newArrayList(pathParts());
                    parts.remove(parts.size() - 1);
                    parentPath = Optional.of('/' + Joiner.on('/').join(parts) + '/');
                }

            }
            return parentPath;
        }

        public ImmutableList<String> pathParts() {

            if (pathParts == null) {

                if (isRoot()) {
                    pathParts = ImmutableList.of();
                } else {
                    String inner = StringUtils.strip(path(), "/");

                    pathParts = ImmutableList.copyOf(Splitter.on('/').split(inner));
                }
            }
            return pathParts;
        }

    }
    
    static class NodeTree extends TreeClass<String,Node>{
        public NodeTree(IdTree<String> idTree, ImmutableBiMap<String, Node> id__node) {
            super(idTree, id__node, false);
        }
    }
}
