package org.ebudoskyi.houserent.Exceptions.CustomExceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
