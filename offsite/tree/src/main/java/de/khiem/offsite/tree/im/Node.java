/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.tree.im;
import com.google.common.base.Optional;
import com.stevewedig.blog.digraph.node.UpNode;
import de.khiem.offsite.tree.NodeType;

/**
 *
 * @author kimyoung
 */
public interface Node extends UpNode<String>{
    String path();
    NodeType type();
    boolean isRoot();
    Optional<String> parentPath();
    static final String CHAT_FOLDER="/_c";
    static final String MEMBER_FOLDER = "/_u";
    
    
}
