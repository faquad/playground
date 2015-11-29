/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.tree.tri;

import java.util.Collection;


public interface FSTree <K,N>{
    
    K getRootKey();
    K findKey(N n);
    N findByKey(K k);
    
    K addToNode(K k, N n);
     
    Collection<N> findSubs(K k, int depth);
}
