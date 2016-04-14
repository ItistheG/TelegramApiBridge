package org.javagram.response.object;


import org.telegram.api.*;
import org.telegram.api.messages.TLMessages;

import java.util.Date;

/**
 * Created by Danya on 09.03.2016.
 */
public class Message
{
    private int id;
    private int fromId;
    private Integer toId;
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
        if(absMessage instanceof TLMessage)
        {
            TLMessage tlMessage = (TLMessage) absMessage;

            fromId = tlMessage.getFromId();
            TLAbsPeer peer = tlMessage.getToId();
            if (peer instanceof TLPeerUser) {
                toId = ((TLPeerUser) peer).getUserId();
            }
            out = tlMessage.getOut();
            unread = tlMessage.getUnread();
            date = new Date(tlMessage.getDate());
            message = tlMessage.getMessage();
        }
        else if(absMessage instanceof  TLMessageForwarded)
        {
            TLMessageForwarded tlMessageForwarded = (TLMessageForwarded) absMessage;

            fromId = tlMessageForwarded.getFromId();
            TLAbsPeer peer = tlMessageForwarded.getToId();
            if (peer instanceof TLPeerUser) {
                toId = ((TLPeerUser) peer).getUserId();
            }
            out = tlMessageForwarded.getOut();
            unread = tlMessageForwarded.getUnread();
            date = new Date(tlMessageForwarded.getDate());
            message = tlMessageForwarded.getMessage();

            fwdFromId = tlMessageForwarded.getFwdFromId();
            fwdData = new Date(tlMessageForwarded.getDate());
        }
      /*  else if(absMessage instanceof TLMessageService)
        {
            TLMessageService tlMessageService = (TLMessageService) absMessage;

            fromId = tlMessageService.getFromId();
            TLAbsPeer peer = tlMessageService.getToId();
            if (peer instanceof TLPeerUser) {
                toId = ((TLPeerUser) peer).getUserId();
            }

            out = tlMessageService.getOut();
            unread = tlMessageService.getUnread();
            date = new Date(tlMessageService.getDate());
        } */
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

    public Integer getToId() {
        return toId;
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
}
