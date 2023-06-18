package com.project.RTRT.reservation;

import com.project.RTRT.Customer.CustomerRepository;
import com.project.RTRT.Customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping("findAll")
    // get all reservation
    public List<Reservation> getAllReservation() {
        return reservationService.getAllReservation();
    }


    @GetMapping("findActive")
    // get all active reservation
    public List<Reservation> getActiveReservation() {
        return reservationService.getActiveReservation();
    }


    @GetMapping("findByDateAndTime")
    public List<Reservation> getByDateAndTime(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                              @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time) {
        return reservationService.getByDateAndTime(date, time);
    }

    @GetMapping("findBetweenTwoDates")
    public List<Reservation> getBetweenTwoDates(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return reservationService.getBetweenTwoDates(date1, date2);
    }

    @PostMapping("add")
    // Add a new reservation
    public ResponseEntity<?> addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @PutMapping("update/{id}")
    // the customer can update his reservation
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.updateReservation(id, reservation);
    }


    @PutMapping("cancel/{id}")
    // the customer can cancel his reservation but the reservation should be stored in the databases
    // so we just change the status
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id);
    }

}
