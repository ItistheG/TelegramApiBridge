package org.javagram.response.object.updates;

import org.javagram.response.object.User;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateUserName implements Update {
    private User user;
    private String firstName;
    private String lastName;
   // private String username;

    public UpdateUserName(User user, String firstName, String lastName/*, String username*/) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        //this.username = username;
    }

    public User getUser() {
        return user;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /*public String getUsername() {
        return username;
    }*/
}
