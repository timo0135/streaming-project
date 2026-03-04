package com.services;

import com.dtos.ReservationDto;

import java.util.List;

public interface ReservationService {

    ReservationDto saveReservation(ReservationDto reservationDto);

    ReservationDto getReservationById(Long reservationId);

    boolean deleteReservation(Long reservationId);

    List<ReservationDto> getAllReservations();

    ReservationDto updateReservation(Long reservationId, ReservationDto reservationDto);
}
