package org.ebudoskyi.houserent.Exceptions.CustomExceptions;

public class IllegalBookingAccessException extends RuntimeException{
    public IllegalBookingAccessException(String message) {
        super(message);
    }
}
