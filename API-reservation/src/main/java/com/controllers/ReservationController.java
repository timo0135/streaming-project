package com.controllers;

import com.dtos.*;
import com.services.MovieService;
import com.services.PaymentService;
import com.services.ReservationService;
import com.services.ReviewService;
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
    private final ReviewService reviewService;
    private final MovieService movieService;

    public ReservationController(ReservationServiceImpl reservationService, PaymentService paymentService, ReviewService reviewService, MovieService movieService) {
        this.reservationService = reservationService;
        this.paymentService = paymentService;
        this.reviewService = reviewService;
        this.movieService = movieService;
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
     * Get payment for a reservation
     */
    @GetMapping("/{id}/payments")
    public ResponseEntity<PaymentDto> getReservationPayment(@PathVariable Long id) {
        ReservationDto reservation = reservationService.getReservationById(id);

        PaymentDto payment = paymentService.getPaymentById(reservation.getPaiementId());

        return ResponseEntity.ok(payment);
    }

    /**
     * Get the review for a reservation
     */
    @GetMapping("/{id}/reviews")
    public ResponseEntity<ReviewDto> getReservationReview(@PathVariable Long id) {
        ReservationDto reservation = reservationService.getReservationById(id);

        ReviewDto review = reviewService.getReviewById(reservation.getEvaluationId());

        return ResponseEntity.ok(review);
    }

    /**
     * Get the film for a reservation
     */
    @GetMapping("/{id}/movies")
    public ResponseEntity<MovieDto> getReservationMovie(@PathVariable Long id) {
        ReservationDto reservation = reservationService.getReservationById(id);

        MovieDto movie = movieService.getMovieById(reservation.getMovieId());
        return ResponseEntity.ok(movie);
    }
}
