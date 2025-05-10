package org.ebudoskyi.houserent.mapper;

import org.ebudoskyi.houserent.dto.BookingDTO;
import org.ebudoskyi.houserent.model.Booking;

import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {
    public BookingDTO toDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO(
                booking.getId(),
                booking.getProperty().getId(),
                booking.getTenant().getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getTotalPrice(),
                booking.getStatus()
        );
        return bookingDTO;
    }

    public Booking toEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setStatus(bookingDTO.getStatus());
        return booking;
    }

    public List<BookingDTO> toDTOList(List<Booking> booking) {
        if (booking == null) {
            return null;
        }
        return booking.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Booking>  toEntityList(List<BookingDTO> bookingDTO) {
        if (bookingDTO == null) {
            return null;
        }
        return bookingDTO.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
