package de.khiem.offsite.backup.data;

/**
 *
 * @author kimyoung
 */
public class BackupOwner {
    long uid;
    
    byte[] salt;
    byte[] username;
    byte[] credential;
    
    byte[] secret;
    byte[] pubRSA;
    byte[] privRSA;
    
    byte[] enterpriseSecret;
    
    
}
