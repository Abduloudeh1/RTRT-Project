package com.project.RTRT.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
