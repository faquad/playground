/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.eventLog;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author khiemnguyen
 */
public interface EventQueue {
    void put(Event event) throws IOException;
    void put(List<Event> events) throws IOException;
    
    void doWork(Handler h);

    byte[] toBytes(Event event);
    Event fromBytes(byte[] bytes);
    
    interface Handler {
        void process (Event e);
    }
    
}
