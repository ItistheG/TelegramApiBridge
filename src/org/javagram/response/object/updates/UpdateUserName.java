package org.javagram.response.object.updates;

import org.javagram.response.object.User;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateUserName implements Update {
    private int userId;
    private String firstName;
    private String lastName;
   // private String username;

    public UpdateUserName(int userId, String firstName, String lastName/*, String username*/) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        //this.username = username;
    }

    public int getUser() {
        return userId;
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
