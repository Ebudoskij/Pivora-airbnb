package org.ebudoskyi.houserent.Exceptions.CustomExceptions;

public class BookingNotFoundException extends RuntimeException{
    public BookingNotFoundException(String message) {
        super(message);
    }
}
