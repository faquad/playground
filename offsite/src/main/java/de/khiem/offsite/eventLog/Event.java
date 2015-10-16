package de.khiem.offsite.eventLog;

public class Event {	
    Long itemId;

    public Event() {
        this.itemId = null;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    
    
    public Event(long id) {
        this.itemId = id;
    }
    
    //Node: readNode, deleteNode, moveNode,renameNode  -> requires NodeType (folder, file,note), user, nodeId, boxId, eventually nodeName
    //Box : open close delete create upgrade rename update_otherAttr
    //BoxUser: modify (from -> to)
   
    //Chat: read, delete, dl, write
    //journal: dl, read
       

}
