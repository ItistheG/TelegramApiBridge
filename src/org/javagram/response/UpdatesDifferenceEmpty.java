package org.javagram.response;

import org.javagram.response.object.Message;
import org.telegram.api.updates.TLDifferenceEmpty;

import java.util.Date;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdatesDifferenceEmpty implements UpdatesAbsDifference {

    private int seq;
    private Date date;

    public UpdatesDifferenceEmpty(TLDifferenceEmpty tlDifferenceEmpty) {
        this.seq = tlDifferenceEmpty.getSeq();
        this.date = Message.intToDate(tlDifferenceEmpty.getDate());
    }

    public int getSeq() {
        return seq;
    }

    public Date getDate() {
        return date;
    }
}
