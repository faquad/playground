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
public abstract  class Node {
    protected  long id;
    protected byte[] name;
    long created;
    
    protected Member creator;
    
}
