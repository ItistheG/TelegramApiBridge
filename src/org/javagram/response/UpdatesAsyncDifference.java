package org.javagram.response;

import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import org.javagram.response.object.updates.Update;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by HerrSergio on 01.07.2016.
 */
public class UpdatesAsyncDifference {
    private UpdatesState state;
    private ArrayList<Message> newMessages = new ArrayList<>();
    private ArrayList<Update> otherUpdates = new ArrayList<>();
    private HashSet<User> users = new HashSet<>();

    public UpdatesAsyncDifference(UpdatesState state, ArrayList<Message> newMessages, ArrayList<Update> otherUpdates, HashSet<User> users) {
        this.state = state;
        this.newMessages = newMessages;
        this.otherUpdates = otherUpdates;
        this.users = users;
    }

    public UpdatesState getState() {
        return state;
    }

    public ArrayList<Message> getNewMessages() {
        return newMessages;
    }

    public ArrayList<Update> getOtherUpdates() {
        return otherUpdates;
    }

    public HashSet<User> getUsers() {
        return users;
    }
}
