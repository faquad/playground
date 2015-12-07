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

    public BackupOwner() {
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getUsername() {
        return username;
    }

    public void setUsername(byte[] username) {
        this.username = username;
    }

    public byte[] getCredential() {
        return credential;
    }

    public void setCredential(byte[] credential) {
        this.credential = credential;
    }

    public byte[] getSecret() {
        return secret;
    }

    public void setSecret(byte[] secret) {
        this.secret = secret;
    }

    public byte[] getPubRSA() {
        return pubRSA;
    }

    public void setPubRSA(byte[] pubRSA) {
        this.pubRSA = pubRSA;
    }

    public byte[] getPrivRSA() {
        return privRSA;
    }

    public void setPrivRSA(byte[] privRSA) {
        this.privRSA = privRSA;
    }

    public byte[] getEnterpriseSecret() {
        return enterpriseSecret;
    }

    public void setEnterpriseSecret(byte[] enterpriseSecret) {
        this.enterpriseSecret = enterpriseSecret;
    }
    
    
    
}
