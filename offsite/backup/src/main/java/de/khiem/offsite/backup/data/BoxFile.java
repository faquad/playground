package de.khiem.offsite.backup.data;

/**
 *
 * @author kimyoung
 */
public class BoxFile extends Node{
    long size;
    FileLocation location;
    FileKey key;
    
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

    public FileKey getKey() {
        return key;
    }

    public void setKey(FileKey key) {
        this.key = key;
    }
}
