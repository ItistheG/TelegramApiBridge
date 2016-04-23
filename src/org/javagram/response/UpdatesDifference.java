package org.javagram.response;

import org.javagram.response.object.Message;
import org.javagram.response.object.MessagesMessage;
import org.javagram.response.object.User;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.TLAbsUser;
import org.telegram.api.updates.TLAbsDifference;
import org.telegram.api.updates.TLDifference;
import org.telegram.api.updates.TLDifferenceEmpty;
import org.telegram.api.updates.TLDifferenceSlice;

import java.util.*;

/**
 * Created by HerrSergio on 23.04.2016.
 */
public class UpdatesDifference extends ArrayList<MessagesMessage> {

    private boolean slice;
    private boolean empty;
    private int seq;
    private Date date;
    private UpdatesState state;

    public UpdatesDifference(TLAbsDifference tlAbsDifference, Map<Integer, User> users) {

        if(users == null)
            users = new HashMap<>();

        if(tlAbsDifference instanceof TLDifference) {
            TLDifference tlDifference = (TLDifference)tlAbsDifference;
            acceptTLAbsMessages(tlDifference.getUsers(), tlDifference.getNewMessages(), users);
            state = new UpdatesState(tlDifference.getState());
        } else if(tlAbsDifference instanceof TLDifferenceEmpty) {
            TLDifferenceEmpty tlDifferenceEmpty = (TLDifferenceEmpty)tlAbsDifference;
            this.empty = true;
            this.seq = tlDifferenceEmpty.getSeq();
            this.date = Message.intToDate(tlDifferenceEmpty.getDate());
        } else if(tlAbsDifference instanceof TLDifferenceSlice) {
            TLDifferenceSlice tlDifferenceSlice = (TLDifferenceSlice)tlAbsDifference;
            acceptTLAbsMessages(tlDifferenceSlice.getUsers(), tlDifferenceSlice.getNewMessages(), users);
            this.slice = true;
            state = new UpdatesState(tlDifferenceSlice.getIntermediateState());
        } else {
            throw new InconsistentDataException();
        }

    }

    private void acceptTLAbsMessages(List<TLAbsUser> tlAbsUserList, List<TLAbsMessage> tlAbsMessageList, Map<Integer, User> users) {

        for(TLAbsUser tlAbsUser : tlAbsUserList) {
            User user = User.createUser(tlAbsUser);
            users.putIfAbsent(user.getId(), user);
        }

        for(TLAbsMessage tlAbsMessage : tlAbsMessageList) {
            MessagesMessage messagesMessage = new MessagesMessage(tlAbsMessage, users);
            this.add(messagesMessage);
        }
    }

    public boolean isSlice() {
        return slice;
    }

    public boolean isEmptyDifference() {
        return empty;
    }

    public int getSeq() {
        return seq;
    }

    public Date getDate() {
        return date;
    }

    public UpdatesState getIntermediateState() {
        return state;
    }

    public UpdatesState getState() {
        return state;
    }
}
