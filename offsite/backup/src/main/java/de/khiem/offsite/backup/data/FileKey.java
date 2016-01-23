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
public class FileKey {
    byte[] iv;
    byte[] key;

    public FileKey() {
    }

    public FileKey(byte[] iv, byte[] key) {
        this.iv = iv;
        this.key = key;
    }

    
    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }
    
}
