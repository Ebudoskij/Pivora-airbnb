package org.ebudoskyi.houserent.Exceptions.CustomExceptions;

public class IllegalPropertyAccessException extends RuntimeException{
    public IllegalPropertyAccessException(String message) {
        super(message);
    }
}
