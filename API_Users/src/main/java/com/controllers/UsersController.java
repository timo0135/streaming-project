package src.main.java.com.controllers;

import src.main.java.com.dtos.UsersDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.main.java.com.services.ReservationService;
import src.main.java.com.dtos.ReservationDto;
import src.main.java.com.services.impl.UsersServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersServiceImpl usersService;
    private final ReservationService reservationService;

    public UsersController(UsersServiceImpl userService, ReservationService reservationService) {
        this.usersService = userService;
        this.reservationService = reservationService;
    }

    /**
     * <p>Get all users in the system</p>
     * @return List<UsersDto>
     */
    @GetMapping
    public List<UsersDto> getAllUser() {
        return usersService.getAllUser();
    }

    /**
     * Method to get the user based on the ID
     */
    @GetMapping("/{id}")
    public UsersDto getUserById(@PathVariable Long id){
        return usersService.getUserById(id);
    }

    /**
     * Create a new user in the system
     */
    @PostMapping
    public UsersDto saveUser(final @RequestBody UsersDto usersDto){
        if (usersDto.getAge() > 150 || usersDto.getAge() < 0) {
            return (UsersDto) ResponseEntity.status(HttpStatus.CONFLICT);
        }
        return usersService.saveUser(usersDto);
    }

    /**
     * Delete a user by he's id
     */
    @DeleteMapping("/{id}")
    public Boolean deleteUser(@PathVariable Long id){
        return usersService.deleteUser(id);
    }

    /**
     * Get all reservations made by the user
     */
    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<ReservationDto>> getUserReservations(@PathVariable Long id) {
        UsersDto user = usersService.getUserById(id);

        if (user.getIdReservations().isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        List<ReservationDto> reservations = user.getIdReservations().stream()
                .map(idRes -> reservationService.getReservationById(String.valueOf(idRes)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservations);
    }

    /**
     * Create a reservation by the user
     */
    @PostMapping("/{id}/reservations")
    public ResponseEntity<ReservationDto> createReservation(
            @PathVariable Long id,
            @RequestBody ReservationDto reservationDto) {
        UsersDto user = usersService.getUserById(id);

        if (user.getIdReservations().size() >= 3 ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        ReservationDto created = reservationService.createReservation(reservationDto);
        user.getIdReservations().add(created.getId());
        usersService.saveUser(user);
        return ResponseEntity.ok(created);
    }

    /**
     * Delete a reservation made by the user
     */
    @DeleteMapping("/{id}/reservations/{idReservation}")
    public ResponseEntity<?> deleteReservation(
            @PathVariable Long id,
            @PathVariable Integer idReservation) {
        UsersDto user = usersService.getUserById(id);

        if (!user.getIdReservations().contains(idReservation)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        }

        reservationService.deleteReservation(String.valueOf(idReservation));
        user.getIdReservations().remove(idReservation);
        usersService.saveUser(user);
        return ResponseEntity.noContent().build(); // 204
    }
}
