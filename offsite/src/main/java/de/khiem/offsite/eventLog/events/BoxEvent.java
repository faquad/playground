/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.eventLog.events;

/**
 *
 * @author khiemnguyen
 */
public interface BoxEvent {
    /**
    metadata required to do query from DB
    **/
    long getTs();
    long getSeqNumber();
    long getUid();
    long getBoxId();
    
    EventFactory.BOXEVENT_ENUM getBoxEventType();
    EventFactory.NODETYPE_ENUM getNodeType();
    EventFactory.NODEEVENT_ENUM getNodeEventType(); 
    
    /**
    serialized data
    **/
    byte[] getEventData();
}
