package com.controllers;

import com.dtos.DisplayResponseDto;
import com.dtos.PaymentDto;
import com.dtos.ReservationDto;
import com.services.PaymentService;
import com.services.ReservationService;
import com.services.impl.ReservationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final PaymentService paymentService;

    public ReservationController(ReservationServiceImpl reservationService, PaymentService paymentService) {
        this.reservationService = reservationService;
        this.paymentService = paymentService;
    }

    @GetMapping
    public DisplayResponseDto<List<ReservationDto>> getReservations() {
        DisplayResponseDto<List<ReservationDto>> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("collection");
        displayResponseDto.setData(reservationService.getAllReservations());

        return displayResponseDto;
    }

    @GetMapping("/{id}")
    public DisplayResponseDto<ReservationDto> getReservation(@PathVariable Long id){
        DisplayResponseDto<ReservationDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(reservationService.getReservationById(id));

        return displayResponseDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<ReservationDto> saveReservation(final @RequestBody ReservationDto reservationDto){
        DisplayResponseDto<ReservationDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(reservationService.saveReservation(reservationDto));

        return displayResponseDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DisplayResponseDto<ReservationDto> updateReservation(@PathVariable Long id, final @RequestBody ReservationDto reservationDto){
        DisplayResponseDto<ReservationDto> displayResponseDto = new DisplayResponseDto<>();

        displayResponseDto.setMessage("success");
        displayResponseDto.setType("item");
        displayResponseDto.setData(reservationService.updateReservation(id, reservationDto));

        return displayResponseDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteReservation(@PathVariable Long id){
        return reservationService.deleteReservation(id);
    }

    /**
     * Get all reservations made by the user
     */
    @GetMapping("/{id}/payments")
    public ResponseEntity<PaymentDto> getReservationPayments(@PathVariable Long id) {
        ReservationDto reservation = reservationService.getReservationById(id);

        PaymentDto payment = paymentService.getPaymentById(reservation.getPaiementId());

        return ResponseEntity.ok(payment);
    }
}
