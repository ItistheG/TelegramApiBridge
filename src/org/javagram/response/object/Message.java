package org.javagram.response.object;


import com.sun.deploy.ref.Helpers;
import org.javagram.response.ExpectedInconsistentDataException;
import org.javagram.response.InconsistentDataException;
import org.telegram.api.*;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.TLMessage;
import org.telegram.api.TLMessageEmpty;
import org.telegram.api.messages.*;
import static org.javagram.response.Helper.*;

import java.util.Date;

/**
 * Created by Danya on 09.03.2016.
 */
public class Message
{
    private int id;
    private int fromId;
    private Integer toPeerUserId;
    private boolean out;
    private boolean unread;
    private Date date;
    private String message;

    private Integer fwdFromId;
    private Date fwdData;

    public Message(TLAbsMessage absMessage)
    {
        id = absMessage.getId();

        //Знаю, что говнокод, но у предка нет полей, кроме id.
        if(absMessage instanceof TLMessage) {
            TLMessage tlMessage = (TLMessage) absMessage;

            fromId = tlMessage.getFromId();
            TLAbsPeer peer = tlMessage.getToId();
            if (peer instanceof TLPeerUser) {
                toPeerUserId = ((TLPeerUser) peer).getUserId();
            } else if (peer instanceof TLPeerChat){
                throw new ExpectedInconsistentDataException();
            } else {
                throw  new InconsistentDataException();
            }
            out = tlMessage.getOut();
            unread = tlMessage.getUnread();
            date = intToDate(tlMessage.getDate());
            message = tlMessage.getMessage();
        }
        else if(absMessage instanceof  TLMessageForwarded)
        {
            TLMessageForwarded tlMessageForwarded = (TLMessageForwarded) absMessage;

            fromId = tlMessageForwarded.getFromId();
            TLAbsPeer peer = tlMessageForwarded.getToId();
            if (peer instanceof TLPeerUser) {
                toPeerUserId = ((TLPeerUser) peer).getUserId();
            } else if (peer instanceof TLPeerChat){
                throw new ExpectedInconsistentDataException();
            } else {
                throw  new InconsistentDataException();
            }
            out = tlMessageForwarded.getOut();
            unread = tlMessageForwarded.getUnread();
            date = intToDate(tlMessageForwarded.getDate());
            message = tlMessageForwarded.getMessage();

            fwdFromId = tlMessageForwarded.getFwdFromId();
            fwdData = intToDate(tlMessageForwarded.getDate());
        }
        else if(absMessage instanceof TLMessageService)
        {
            TLMessageService tlMessageService = (TLMessageService) absMessage;

            fromId = tlMessageService.getFromId();
            TLAbsPeer peer = tlMessageService.getToId();
            if (peer instanceof TLPeerUser) {
                toPeerUserId = ((TLPeerUser) peer).getUserId();
            } else if (peer instanceof TLPeerChat){
                throw new ExpectedInconsistentDataException();
            } else {
                throw  new InconsistentDataException();
            }
            out = tlMessageService.getOut();
            unread = tlMessageService.getUnread();
            date = intToDate(tlMessageService.getDate());
            message = tlMessageService.getAction().toString();
        }
        else if(absMessage instanceof TLMessageEmpty)
        {

        }
        else
        {
            throw new IllegalArgumentException("Unsupported Message type");
        }
    }

    public int getId() {
        return id;
    }

    public int getFromId() {
        return fromId;
    }

    public boolean isOut() {
        return out;
    }

    public boolean isUnread() {
        return unread;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public boolean isForwarded() {
        return fwdFromId != null;
    }

    public Date getFwdData() {
        return fwdData;
    }

    public int getFwdFromId() {
        return fwdFromId;
    }

    public Integer getToPeerUserId() {
        return toPeerUserId;
    }


}
