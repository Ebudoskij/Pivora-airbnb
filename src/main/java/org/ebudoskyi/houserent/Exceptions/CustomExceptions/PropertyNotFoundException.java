package org.ebudoskyi.houserent.Exceptions.CustomExceptions;

public class PropertyNotFoundException extends RuntimeException{
    public PropertyNotFoundException(String message) {
        super(message);
    }
}
