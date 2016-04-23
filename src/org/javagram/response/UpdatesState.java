package org.javagram.response;

import org.javagram.response.object.MessagesMessage;
import org.telegram.api.updates.TLState;

import java.util.Date;

/**
 * Created by HerrSergio on 23.04.2016.
 */
public class UpdatesState {

    private int pts;
    private int qts;
    private Date date;
    private int seq;
    private int unreadCount;

    public  UpdatesState(TLState tlState) {
        this.pts = tlState.getPts();
        this.qts = tlState.getQts();
        this.date = MessagesMessage.intToDate(tlState.getDate());
        this.seq = tlState.getSeq();
        this.unreadCount = tlState.getUnreadCount();
    }

    public int getPts() {
        return pts;
    }

    public int getQts() {
        return qts;
    }

    public Date getDate() {
        return date;
    }

    public int getSeq() {
        return seq;
    }

    public int getUnreadCount() {
        return unreadCount;
    }
}
