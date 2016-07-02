package org.javagram.response.object.inputs;

import org.telegram.api.TLInputContact;

/**
 * Created by HerrSergio on 01.07.2016.
 */
public class InputContact {
    private long clientId;
    private String phone;
    private String firstName;
    private String lastName;

    public InputContact(long clientId, String phone, String firstName, String lastName) {
        this.clientId = clientId;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getClientId() {
        return clientId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhone() {
        return phone;
    }

    public TLInputContact createTLInputContact() {
        return new TLInputContact(clientId, phone, firstName, lastName);
    }
}
