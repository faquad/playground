package de.khiem.offsite.backup.data;

import java.util.List;

/**
 *
 * @author kimyoung
 */
public class Backup {

    long created;

    BackupOwner owner;
    List<Box> boxes;

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public BackupOwner getOwner() {
        return owner;
    }

    public void setOwner(BackupOwner owner) {
        this.owner = owner;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

}
