package com.project.RTRT.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "reservation")
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("findall")
    public List<Reservation> getReservation() {
        return this.reservationRepository.findAll();
    }


    @PostMapping("add")
    // Add a new reservation
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        return new ResponseEntity<>(reservationRepository.save(reservation), HttpStatus.CREATED);
    }


}
