package com.services.impl;

import com.dtos.ReservationDto;
import com.mappers.ReservationMapper;
import com.repositories.ReservationRepository;
import com.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("ReservationService")
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public ReservationDto saveReservation(ReservationDto reservationDto) {
        var reservation = reservationMapper.toEntity(reservationDto);
        var savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toDto(savedReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDto getReservationById(Long reservationId) {
        var reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La reservation avec l'ID %d n'existe pas", reservationId)));
        return reservationMapper.toDto(reservation);
    }

    @Override
    public boolean deleteReservation(Long reservationId) {
        var reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La reservation avec l'ID %d n'existe pas", reservationId)));
        reservationRepository.deleteById(reservation.getId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @Override
    public ReservationDto updateReservation(Long reservationId, ReservationDto reservationDto) {
        var reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("La reservation avec l'ID %d n'existe pas", reservationId)));

        var modifiedReservation = reservationMapper.toEntity(reservationDto);
        if(modifiedReservation.getDescription() == null && modifiedReservation.getDate_debut() == null && modifiedReservation.getDate_fin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one field (amount or status) must be provided for update");
        }
        if(modifiedReservation.getDescription() != null && !modifiedReservation.getDescription().isEmpty()){
            reservation.setDescription(modifiedReservation.getDescription());
        }
        if(modifiedReservation.getDate_debut() != null && !modifiedReservation.getDate_debut().isEmpty()){
            reservation.setDate_debut(modifiedReservation.getDate_debut());
        }
        if(modifiedReservation.getDate_fin() != null && !modifiedReservation.getDate_fin().isEmpty()){
            reservation.setDate_fin(modifiedReservation.getDate_fin());
        }
        reservation.setMovieId(modifiedReservation.getMovieId());
        reservation.setEvaluationId(modifiedReservation.getEvaluationId());
        reservation.setUserId(modifiedReservation.getUserId());
        reservation.setPaiementId(modifiedReservation.getPaiementId());

        var savedReservation = reservationRepository.save(reservation);

        return reservationMapper.toDto(savedReservation);
    }
}
