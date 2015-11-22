/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.eventlog.events;

/**
 *
 * @author khiemnguyen
 */
public class EventFactory {
    public static enum BOXEVENT_ENUM {
        //
        UNKNOWN(0),
        CREATE(1),
        DELETE(2),
        MODIFY_USER(3),
        CHANGE_DEFAULT_PERM(4),
        CLOSE(5),
        REOPEN(6),
        RENAME(7),
        TO_DR(8),
        CHANGE_GENERIC(9);
        final int i;

        private BOXEVENT_ENUM(int i) {
            this.i = i;
        }
    }

    public static enum NODETYPE_ENUM {

        UNKOWN(0),
        FOLDER(1),
        FILE(2),
        NOTE(3),
        CHAT(4),
        JOURNAL(5);
        final int i;

        private NODETYPE_ENUM(int i) {
            this.i = i;
        }
    }

    public static enum NODEEVENT_ENUM {

        UNKNOWN(0),
        CREATE(1),
        DELETE(2),
        RENAME(3),
        LOCK(4),
        UNLOCK(5),
        MOVE(6),
        CHANGE_DR_OPT(7),
        DL_OR_VIEW(8),
        CHANGE_GENERIC(9)
        ;
        final int i;

        private NODEEVENT_ENUM(int i) {
            this.i = i;
        }

    }

    public void registerDataTypes(Class<? extends BoxEvent>[] events) {

    }

}
