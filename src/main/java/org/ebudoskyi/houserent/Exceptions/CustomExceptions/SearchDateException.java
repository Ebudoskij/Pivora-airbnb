package org.ebudoskyi.houserent.Exceptions.CustomExceptions;

import java.time.DateTimeException;

public class SearchDateException extends DateTimeException {
    public SearchDateException(String message) {
        super(message);
    }
}
