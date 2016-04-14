package org.javagram.response;

/**
 * Created by HerrSergio on 14.04.2016.
 */
public class InconsistentDataException extends RuntimeException {

    public InconsistentDataException(String message) {
        super(message);
    }

    public InconsistentDataException() {

    }
}
