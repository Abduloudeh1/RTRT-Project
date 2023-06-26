package com.project.RTRT.reservation;


import com.project.RTRT.user.model.AppUser;
import com.project.RTRT.user.repository.UserRepository;
import com.project.RTRT.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "reservation")
public class ReservationController {

   @Autowired
   UserRepository userRepository;

   @Autowired
   ReservationService reservationService;

   @Autowired
   UserService userService;

   @Autowired
   ReservationRepository reservationRepository;

   @GetMapping("findAll")
   // get all reservation
   public List<Reservation> getAllReservation() {
      return reservationService.getAllReservation();
   }

//TODO:
//   @GetMapping("findActive")
//   // get all active reservation
//   public List<Reservation> getActiveReservation() {
//      return reservationService.getActiveReservation(loggedInUser.getUserId());
//   }

   @GetMapping("findUserReservations")
   // get all active reservation for the logged in user
   public List<Reservation> findReservationsForUser(HttpServletRequest req) {

      AppUser loggedInUser = userService.getMyInfo(req);
      return reservationService.getActiveReservation(loggedInUser.getUserId());
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

   @PostMapping("admin/add")
   // Add a new reservation
   public ResponseEntity<?> adminAddReservation(@RequestBody Reservation reservation,
                                                @RequestParam(name = "firstName", required = false) String firstName,
                                                @RequestParam(name = "lastName", required = false) String lastName,
                                                @RequestParam(name = "telephoneNumber", required = false) String telephoneNumber
   ) {

      AppUser guest = new AppUser();
      guest.setFirstName(firstName);
      guest.setLastName(lastName);
      guest.setTelephoneNumber(telephoneNumber);
      guest.setRegistered(false);
      Random random = new Random();
      String email = firstName + "." + lastName + random.nextInt(20000) + "@customer.com";
      guest.setEmail(email);
      guest.setPassword("passssssssss");
      AppUser saved = userRepository.saveAndFlush(guest);
      reservation.setAppUser(saved);
      return reservationService.addReservation(reservation);
   }


   @PutMapping("update/{id}")
   // the customer can update his reservation
   public ResponseEntity<?> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
      return reservationService.updateReservation(id, reservation);
   }

   @GetMapping("/{id}")
   // get reservation by id
   public ResponseEntity<?> getReservationPerId(@PathVariable Long id) {
      System.out.println(id);
      return new ResponseEntity<>(reservationRepository.findById(id).get(), HttpStatus.OK);
   }

   @PutMapping("cancel/{id}")
   // the customer can cancel his reservation but the reservation should be stored in the databases
   // so we just change the statusÏ
   public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
      return reservationService.cancelReservation(id);
   }

}
