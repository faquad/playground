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
public class BoxFile extends Node{
    long size;
    FileLocation location;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public FileLocation getLocation() {
        return location;
    }

    public void setLocation(FileLocation location) {
        this.location = location;
    }
    
    
}
