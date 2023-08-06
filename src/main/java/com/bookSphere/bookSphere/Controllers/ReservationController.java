package com.bookSphere.bookSphere.Controllers;
import com.bookSphere.bookSphere.dto.ReservationDTO;
import com.bookSphere.bookSphere.models.Client;
import com.bookSphere.bookSphere.models.Reservation;
import com.bookSphere.bookSphere.repositories.ClientRepository;
import com.bookSphere.bookSphere.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ReservationController {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ClientRepository clientRepository;

    @PostMapping("/reservation/create")
    ResponseEntity<Object>  createReservation(Authentication authentication, @RequestBody ReservationDTO reservationDTO) {
        Client client=clientRepository.findByEmail(authentication.getName());

        if (reservationDTO.getDate() == null) {
            return new ResponseEntity<>("Please enter date", HttpStatus.FORBIDDEN);
        }

        if (reservationDTO.getEventRoom() == null) {
            return new ResponseEntity<>("Please enter a room", HttpStatus.FORBIDDEN);
        }

        if (reservationDTO.getDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>("Reservation date must be after the current date", HttpStatus.FORBIDDEN);
        }

        if (reservationDTO.getDate().isBefore(LocalDate.now().plusDays(30))) {
            return new ResponseEntity<>("Reservation date must be at least 30 days in advance", HttpStatus.FORBIDDEN);
        }

        List<Reservation> reservations = reservationRepository.findAll();


        if (reservations.stream().anyMatch(reservation -> reservation.getEventRoom().equals(reservationDTO.getEventRoom()) && reservation.getDate().isEqual(reservationDTO.getDate()))) {
            return new ResponseEntity<>("The selected room is already reserved for the chosen date", HttpStatus.FORBIDDEN);
        }

        Reservation reservation = new Reservation(reservationDTO.getDate(),reservationDTO.getEventRoom(),false);
        reservationRepository.save(reservation);
        client.addReservation(reservation);
         clientRepository.save(client);
        return new ResponseEntity<>("Reservation created successfully",HttpStatus.CREATED);

    }

    @GetMapping("reservation")
    Set<ReservationDTO> getReservationsDTO(){
        Set<ReservationDTO> reservationDTOS=reservationRepository.findAll().stream().map(reservation -> new ReservationDTO(reservation)).collect(Collectors.toSet());
        return reservationDTOS;
    }

    @PutMapping("/reservation/delete")
    ResponseEntity<Object> deleteReservation(@RequestParam Long id){
        Reservation reservation=reservationRepository.findById(id).orElse(null);
        reservation.setDeleted(true);
        reservationRepository.save(reservation);
        return new ResponseEntity<>("Reservation successfully deleted", HttpStatus.OK);
    }
}
