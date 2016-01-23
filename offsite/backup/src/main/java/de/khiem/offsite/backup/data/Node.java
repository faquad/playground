/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.backup.data;

/**
 *
 * @author kimyoung
 */
public abstract  class Node<T> {
    
    protected  long id;
    protected T name;
    long created;
    
    protected Member creator;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public Member getCreator() {
        return creator;
    }

    public void setCreator(Member creator) {
        this.creator = creator;
    }
    
}
