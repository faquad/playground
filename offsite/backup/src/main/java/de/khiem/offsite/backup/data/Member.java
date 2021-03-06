package de.khiem.offsite.backup.data;

/**
 *
 * @author kimyoung
 */
public class Member<T> {
    long uid;
    T name;
    
    PERM permission;
    TYPE type;
    
    long adminId;
    
    public static enum PERM {
        OWNER,MANAGER,MEMBER,EX
    }
    
    public static enum TYPE {
        ADMIN,EMP,GUEST,PRIV
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public PERM getPermission() {
        return permission;
    }

    public void setPermission(PERM permission) {
        this.permission = permission;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }
}
