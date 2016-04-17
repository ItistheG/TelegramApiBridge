package org.javagram.response.object;

import org.telegram.api.TLUserRequest;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class UserRequest extends User {

    public UserRequest(TLUserRequest userRequest) {
        super(userRequest);
    }
}
