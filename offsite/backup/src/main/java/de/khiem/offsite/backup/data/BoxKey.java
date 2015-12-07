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
public class BoxKey {
   byte[] iv; 
   byte[] masterKey;
   byte[] metaKey;
   
   //search,journal

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public byte[] getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(byte[] masterKey) {
        this.masterKey = masterKey;
    }

    public byte[] getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(byte[] metaKey) {
        this.metaKey = metaKey;
    }
   
   
}
