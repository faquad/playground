/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.eventLog;

/**
 *
 * @author khiemnguyen
 */
public class Sequence {
    final String type;
    
    //currentValue
    final long current;

    public Sequence(String type, long current) {
        this.type = type.trim().toLowerCase();
        this.current = current;
    }

    public String getType() {
        return type;
    }

    public long getCurrent() {
        return current;
    }

}
