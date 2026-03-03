package src.main.java.com.services;

import src.main.java.com.dtos.ReservationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Arrays;
import java.util.List;

@Service
public class ReservationService {

    @Value("${reservation.api.url}")
    private String reservationApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // GET /reservations
    public List<ReservationDto> getAllReservations() {
        ReservationDto[] reservations = restTemplate.getForObject(
                reservationApiUrl + "/reservations", ReservationDto[].class);
        return Arrays.asList(reservations);
    }

    // GET /reservations/{id}
    public ReservationDto getReservationById(String id) {
        return restTemplate.getForObject(
                reservationApiUrl + "/reservations/" + id, ReservationDto.class);
    }

    // POST /reservations
    public ReservationDto createReservation(ReservationDto reservation) {
        return restTemplate.postForObject(
                reservationApiUrl + "/reservations", reservation, ReservationDto.class);
    }

    // PUT /reservations/{id}
    public ReservationDto updateReservation(String id, ReservationDto reservation) {
        HttpEntity<ReservationDto> request = new HttpEntity<>(reservation);
        ResponseEntity<ReservationDto> response = restTemplate.exchange(
                reservationApiUrl + "/reservations/" + id,
                HttpMethod.PUT, request, ReservationDto.class);
        return response.getBody();
    }

    // DELETE /reservations/{id}
    public void deleteReservation(String id) {
        restTemplate.delete(reservationApiUrl + "/reservations/" + id);
    }

    // GET /reservations/{id}/payments
    public Object getPaymentsByReservation(String id) {
        return restTemplate.getForObject(
                reservationApiUrl + "/reservations/" + id + "/payments", Object.class);
    }

    // GET /reservations/{id}/movies
    public Object getMoviesByReservation(String id) {
        return restTemplate.getForObject(
                reservationApiUrl + "/reservations/" + id + "/movies", Object.class);
    }

    // GET /reservations/{id}/reviews
    public Object getReviewsByReservation(String id) {
        return restTemplate.getForObject(
                reservationApiUrl + "/reservations/" + id + "/reviews", Object.class);
    }
}