package com.project.RTRT.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

   List<Reservation> findAllByStatus(int status);

   List<Reservation> findByAppUserUserIdAndReservationDateGreaterThanEqualAndStartTimeGreaterThanEqualOrderByReservationDateAscStartTimeAsc(Integer id, LocalDate date, LocalTime startTime);

   List<Reservation> findByAppUserUserIdAndReservationDateGreaterThanEqualAndStartTimeGreaterThanEqualAndReservationDateIsNotAndStartTimeIsNotOrderByReservationDateAscStartTimeAsc(Integer id, LocalDate date, LocalTime startTime, LocalDate localDate,LocalTime startTimes);

   List<Reservation> findAllByAppUserUserIdAndReservationDateGreaterThanEqualAndStatusOrderByReservationDateAscStartTimeAsc(Integer userId, LocalDate date, int status);

   List<Reservation> findAllByReservationDateAndStartTimeAndStatus(LocalDate date, LocalTime startTime, int status);

   List<Reservation> findAllByReservationDateBetweenAndStatus(LocalDate date1, LocalDate date2, int status);
}
