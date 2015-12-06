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
public class Member {
    long uid;
    byte[] name;
    
    PERM permission;
    TYPE type;
    
    long adminId;
    
    public static enum PERM {
        OWNER,MANAGER,MEMBER,EX
    }
    
    public static enum TYPE {
        ADMIN,EMP,GUEST,PRIV
    }
    
}
