
package de.khiem.offsite.backup.data;

/**
 *
 * @author kimyoung
 */
public class BoxConfig {
    boolean dr;
    
    boolean fileR;
    boolean fileW;
    boolean chatRW;
    
    boolean contingent;

    public BoxConfig() {
    }

    public boolean isDr() {
        return dr;
    }

    public void setDr(boolean dr) {
        this.dr = dr;
    }

    public boolean isFileR() {
        return fileR;
    }

    public void setFileR(boolean fileR) {
        this.fileR = fileR;
    }

    public boolean isFileW() {
        return fileW;
    }

    public void setFileW(boolean fileW) {
        this.fileW = fileW;
    }

    public boolean isChatRW() {
        return chatRW;
    }

    public void setChatRW(boolean chatRW) {
        this.chatRW = chatRW;
    }

    public boolean isContingent() {
        return contingent;
    }

    public void setContingent(boolean contingent) {
        this.contingent = contingent;
    }
    
    
    
}
