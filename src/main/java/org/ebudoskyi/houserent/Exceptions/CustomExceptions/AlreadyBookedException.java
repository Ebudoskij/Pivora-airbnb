package org.ebudoskyi.houserent.Exceptions.CustomExceptions;

public class AlreadyBookedException extends RuntimeException{
    public AlreadyBookedException(String message) {
        super(message);
    }
}
