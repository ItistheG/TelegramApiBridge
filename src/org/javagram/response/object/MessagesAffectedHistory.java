package org.javagram.response.object;

import org.telegram.api.messages.TLAffectedHistory;

/**
 * Created by HerrSergio on 21.04.2016.
 */
public class MessagesAffectedHistory {
    private int pts;
    private int seq;
    private int offset;

    public MessagesAffectedHistory(TLAffectedHistory tlAffectedHistory) {
        this.pts = tlAffectedHistory.getPts();
        this.seq = tlAffectedHistory.getSeq();
        this.offset = tlAffectedHistory.getOffset();
    }


    public int getPts() {
        return pts;
    }

    public int getSeq() {
        return seq;
    }

    public int getOffset() {
        return offset;
    }
}
