/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.backup.data;

import java.util.List;

/**
 *
 * @author kimyoung
 */
public class Box {
   
    long id;
    byte[] name;
    byte[] author;
    long created;
    
    BoxKey key;
    
    Member owner;
    List<Member> members;
    
    BoxConfig config; 

    BoxFolder root;
    
    List<Chat> chats;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public byte[] getAuthor() {
        return author;
    }

    public void setAuthor(byte[] author) {
        this.author = author;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public BoxKey getKey() {
        return key;
    }

    public void setKey(BoxKey key) {
        this.key = key;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public BoxConfig getConfig() {
        return config;
    }

    public void setConfig(BoxConfig config) {
        this.config = config;
    }

    public BoxFolder getRoot() {
        return root;
    }

    public void setRoot(BoxFolder root) {
        this.root = root;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
    
    
    
}
