package org.ebudoskyi.houserent.Exceptions.CustomExceptions;

import java.time.DateTimeException;

public class BookingDateException extends DateTimeException {
    public BookingDateException(String message) {
        super(message);
    }
}
