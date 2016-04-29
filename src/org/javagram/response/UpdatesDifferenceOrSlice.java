package org.javagram.response;

import org.javagram.response.object.MessagesMessage;
import org.javagram.response.object.User;
import org.javagram.response.object.updates.Update;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.TLAbsUpdate;
import org.telegram.api.TLAbsUser;
import org.telegram.api.updates.TLAbsDifference;
import org.telegram.api.updates.TLDifference;
import org.telegram.api.updates.TLDifferenceSlice;
import org.telegram.api.updates.TLState;

import java.util.*;

/**
 * Created by HerrSergio on 23.04.2016.
 */
public class UpdatesDifferenceOrSlice implements UpdatesAbsDifference {

    private boolean slice;
    private UpdatesState state;
    private ArrayList<MessagesMessage> newMessages = new ArrayList<>();
    private  ArrayList<Update> otherUpdates = new ArrayList<>();
    private HashSet<User> users = new HashSet<>();

    public UpdatesDifferenceOrSlice(TLAbsDifference tlAbsDifference, Map<Integer, User> users) {

        if(users == null)
            users = new HashMap<>();

        List<TLAbsUser> tlAbsUsers;
        List<TLAbsMessage> tlAbsMessages;
        List<TLAbsUpdate> tlAbsUpdates;
        TLState tlState;

        if(tlAbsDifference instanceof TLDifference) {
            TLDifference tlDifference = (TLDifference)tlAbsDifference;
            tlAbsUsers = tlDifference.getUsers();
            tlAbsMessages = tlDifference.getNewMessages();
            tlAbsUpdates = tlDifference.getOtherUpdates();
            tlState = tlDifference.getState();
            this.slice = false;
        } else if(tlAbsDifference instanceof TLDifferenceSlice) {
            TLDifferenceSlice tlDifferenceSlice = (TLDifferenceSlice)tlAbsDifference;
            tlAbsUsers = tlDifferenceSlice.getUsers();
            tlAbsMessages = tlDifferenceSlice.getNewMessages();
            tlAbsUpdates = tlDifferenceSlice.getOtherUpdates();
            tlState = tlDifferenceSlice.getIntermediateState();
            this.slice = true;
        } else {
            throw new InconsistentDataException();
        }

        Helper.acceptTLAbsMessages(this.newMessages, tlAbsUsers, tlAbsMessages, users, this.users);
        Helper.acceptTLUpdates(this.otherUpdates, tlAbsUpdates, Helper.createMessagesMap(newMessages), users, this.users);
        this.state = new UpdatesState(tlState);
    }

    public boolean isSlice() {
        return slice;
    }

    public UpdatesState getIntermediateState() {
        return state;
    }

    public UpdatesState getState() {
        return state;
    }

    public ArrayList<MessagesMessage> getNewMessages() {
        return newMessages;
    }

    public HashSet<User> getUsers() {
        return users;
    }
}
