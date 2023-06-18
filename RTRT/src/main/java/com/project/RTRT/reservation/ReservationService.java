package com.project.RTRT.reservation;

import com.project.RTRT.reservationTime.ReservationTime;
import com.project.RTRT.reservationTime.ReservationTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    @Value("${maxCapacity}")
    private int maxCapacity;

    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    public List<Reservation> getAllReservation() {
        return this.reservationRepository.findAll();
    }

    public List<Reservation> getActiveReservation() {
        LocalDate currentDate = LocalDate.now();
        List<Reservation> futureReservation = this.reservationRepository.findAllByReservationDateGreaterThanEqualAndStatus(
                currentDate, 0);
        List<Reservation> itemsToRemove = new ArrayList<>();
        for (Reservation reservation : futureReservation) {
            if (reservation.getReservationDate().isEqual(LocalDate.now())) {
                if (reservation.getStartTime().isBefore(LocalTime.now())) {
                    itemsToRemove.add(reservation);
                }
            }
        }
        futureReservation.removeAll(itemsToRemove);
        return futureReservation;
    }

    public List<Reservation> getByDateAndTime(LocalDate date, LocalTime time) {
        return this.reservationRepository.findAllByReservationDateAndStartTimeAndStatus(date, time, 0);
    }

    public List<Reservation> getBetweenTwoDates(LocalDate date1, LocalDate date2) {
        return this.reservationRepository.findAllByReservationDateBetweenAndStatus(date1, date2, 0);
    }


    public ResponseEntity<?> addReservation(Reservation reservation) {

        // everywhere
        if (checkIfMonday(reservation)) {
            return new ResponseEntity<>("We are closed", HttpStatus.BAD_REQUEST);
        }

        //starts from 14, last reservation at 22
        if (!startTimeAllowed(reservation)) {
            return new ResponseEntity<>("We are closed at this time", HttpStatus.BAD_REQUEST);
        }

        adjustTimeOfReservation(reservation);


        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        if (reservation.getReservationDate().isBefore(currentDate)) {
            return new ResponseEntity<>("you can not make a reservation in the past", HttpStatus.BAD_REQUEST);
        } else if (reservation.getReservationDate().isEqual(currentDate)) {
            if (reservation.getStartTime().isBefore(currentTime)) {
                return new ResponseEntity<>("you can not make a reservation in the past", HttpStatus.BAD_REQUEST);
            } else {

                Reservation saved = this.reservationRepository.save(reservation);
                if (!addReservationTime(reservation)) {
                    return new ResponseEntity<String>("max capacity ", HttpStatus.BAD_REQUEST);
                }

                return new ResponseEntity<>(saved, HttpStatus.CREATED);
            }
        } else {
            if (!addReservationTime(reservation)) {
                return new ResponseEntity<String>("max capacity from here ", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(this.reservationRepository.save(reservation), HttpStatus.CREATED);

        }
    }

    private void adjustTimeOfReservation(Reservation reservation) {
        if (reservation.getStartTime().getMinute()<30){
            reservation.setStartTime(LocalTime.of(reservation.getStartTime().getHour(),0));
        }
        if (reservation.getStartTime().getMinute()>=30){
            reservation.setStartTime(LocalTime.of(reservation.getStartTime().getHour(),30));
        }


    }


    public ResponseEntity<?> updateReservation(Long id, Reservation reservation) {

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        if (reservationRepository.findById(id).isPresent()) {
            Optional<Reservation> optionalReservation = this.reservationRepository.findById(id);
            Reservation existingReservation = optionalReservation.get();
            if (existingReservation.getReservationDate().isBefore(currentDate)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (existingReservation.getReservationDate().isEqual(currentDate)) {
                if (existingReservation.getStartTime().isBefore(currentTime)) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    if (reservation.getReservationDate().isBefore(currentDate)) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }

                    if (reservation.getReservationDate().isEqual(currentDate)) {
                        if (reservation.getStartTime().isBefore(currentTime)) {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                        // delete the old number of person(existingReservation object) from the current capacity of this startTime
                        // than add the new number of person(from reservation object) to the current capacity
                        Optional<ReservationTime> reservationTime = reservationTimeRepository.findByDateAndTime(existingReservation.getReservationDate(), existingReservation.getStartTime());

                        reservationTime.ifPresent(time ->{
                            time.setCurrentCapacity(time.getCurrentCapacity() - existingReservation.getNumberOfPerson());
                            reservationTimeRepository.save(time);
                        });

                        reservation.setReservationId(id);
                        return addReservation(reservation);
                    } else {
                        Optional<ReservationTime> reservationTime = reservationTimeRepository.findByDateAndTime(existingReservation.getReservationDate(), existingReservation.getStartTime());
                        reservationTime.ifPresent(time ->{
                            time.setCurrentCapacity(time.getCurrentCapacity() - existingReservation.getNumberOfPerson());
                            reservationTimeRepository.save(time);

                        });
//                        addReservationTime(reservation);
                        reservation.setReservationId(id);
                        return addReservation(reservation);
                    }
                }
            } else {
                if (reservation.getReservationDate().isBefore(currentDate)) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else if (reservation.getReservationDate().isEqual(currentDate)) {
                    if (reservation.getStartTime().isBefore(currentTime)) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    } else {
                        Optional<ReservationTime> reservationTime = reservationTimeRepository.findByDateAndTime(existingReservation.getReservationDate(), existingReservation.getStartTime());
                        reservationTime.ifPresent(time ->{
                         time.setCurrentCapacity(time.getCurrentCapacity() - existingReservation.getNumberOfPerson());
                         reservationTimeRepository.save(time);
                        });

//                        addReservationTime(reservation);
                        reservation.setReservationId(id);
                        return addReservation(reservation);
                    }
                } else {
                    Optional<ReservationTime> reservationTime = reservationTimeRepository.findByDateAndTime(existingReservation
                            .getReservationDate(), existingReservation.getStartTime());

                    reservationTime.ifPresent(time -> {
                                time.setCurrentCapacity(reservationTime.get().getCurrentCapacity() - existingReservation.getNumberOfPerson());
                                reservationTimeRepository.save(time);
                            }
                    );
                    reservation.setReservationId(id);
                    return addReservation(reservation);
                }
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    public ResponseEntity<?> cancelReservation(Long id) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        if (reservationRepository.findById(id).isPresent()) {
            Optional<Reservation> optionalReservation = this.reservationRepository.findById(id);
            Reservation reservation = optionalReservation.get();
            if (reservation.getReservationDate().isBefore(currentDate)) {
                return new ResponseEntity<>("you can not make a reservation in the past", HttpStatus.BAD_REQUEST);
            } else if (reservation.getReservationDate().isEqual(currentDate)) {
                if (reservation.getStartTime().isBefore(currentTime)) {
                    return new ResponseEntity<>("you can not make a reservation in the past", HttpStatus.BAD_REQUEST);
                } else {
                    reservation.setStatus(1);
                    reservation.setReservationId(id);
                    Optional<ReservationTime> reservationTime = reservationTimeRepository.findByDateAndTime(reservation.getReservationDate(), reservation.getStartTime());
                    reservationTime.ifPresent(time -> {
                        reservationTime.get().setCurrentCapacity(reservationTime.get().getCurrentCapacity() - reservation.getNumberOfPerson());
                        reservationTimeRepository.save(time);
                    });

                    return new ResponseEntity<>(this.reservationRepository.save(reservation), HttpStatus.OK);
                }
            } else {
                reservation.setStatus(1);
                reservation.setReservationId(id);
                Optional<ReservationTime> reservationTime = reservationTimeRepository.findByDateAndTime(reservation.getReservationDate(), reservation.getStartTime());
                reservationTime.ifPresent(time -> {
                    reservationTime.get().setCurrentCapacity(reservationTime.get().getCurrentCapacity() - reservation.getNumberOfPerson());
                    reservationTimeRepository.save(reservationTime.get());
                });

                return new ResponseEntity<>(this.reservationRepository.save(reservation), HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private boolean startTimeAllowed(Reservation reservation) {
        return !reservation.getStartTime().isBefore(LocalTime.of(14, 0)) &&
                !reservation.getStartTime().isAfter(LocalTime.of(22, 0));
    }

    private boolean checkIfMonday(Reservation reservation) {
        LocalDate date = reservation.getReservationDate();
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return true;
        }
        return false;
    }

    private boolean addReservationTime(Reservation reservation) {
        LocalDate date = reservation.getReservationDate();
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return false;
        }
        Optional<ReservationTime> reservationTime = reservationTimeRepository.findByDateAndTime(reservation.getReservationDate(), reservation.getStartTime());
        boolean added;
        if (reservationTime.isEmpty()) {

            ReservationTime reservationTime1 = new ReservationTime();
            reservationTime1.setDate(reservation.getReservationDate());
            reservationTime1.setTime(reservation.getStartTime());
            reservationTime1.setCurrentCapacity(reservation.getNumberOfPerson());
            reservationTimeRepository.save(reservationTime1);
            System.out.println("new save for this startTime and date");
            added = true;

        } else {
            if (reservationTime.get().getCurrentCapacity() + reservation.getNumberOfPerson() > maxCapacity) {
                System.out.println("mehr als 70 nicht erlaubt!"); //check if the new reservation.
                added = false;
            } else {
                reservationTime.get().setCurrentCapacity(reservationTime.get().getCurrentCapacity() + reservation.getNumberOfPerson());
                ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime.get());
                System.out.println("getCurrentCapacity:" + savedReservationTime.getCurrentCapacity());
                added = true;
            }
        }
        return added;
    }


}
