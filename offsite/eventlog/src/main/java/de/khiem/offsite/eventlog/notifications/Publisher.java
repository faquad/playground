/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.eventlog.notifications;

import java.util.Optional;

/**
 *
 * @author khiemnguyen
 */
public interface Publisher {
    
    Optional<Auth> getAuthority(String subject, String resource);
    
    void setAuthority(Auth auth);
    
    void removeResouce(String resourceId);
    
    Optional<Session> getSession(String sessionId);
    void setSession(Session session);
    void removeSession(String sessionId);

    void close();
    
    class Permission {
        
    }
    
    class Auth {
        String subjectId;
        String resourceId;
        int type;
        Permission perm;

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }
        

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Permission getPerm() {
            return perm;
        }

        public void setPerm(Permission perm) {
            this.perm = perm;
        }        
    }
    
    class Session {
        String sessionId;
        String subjectId;

        public Session(String sessionId, String subjectId) {
            this.sessionId = sessionId;
            this.subjectId = subjectId;
        }

        
        
        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }
        
        
    }
    
}
