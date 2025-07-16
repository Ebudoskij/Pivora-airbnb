package org.ebudoskyi.houserent.Exceptions.CustomExceptions;

import java.time.DateTimeException;

public class BookingDateException extends DateTimeException {
    private final Long propertyId;
    public BookingDateException(String message,  Long propertyId) {
        super(message);
        this.propertyId = propertyId;
    }

    public Long getPropertyId() {
        return propertyId;
    }
}
