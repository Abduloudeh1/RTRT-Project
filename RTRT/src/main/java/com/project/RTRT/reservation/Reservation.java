package com.project.RTRT.reservation;

import com.project.RTRT.Customer.Customer;
import com.project.RTRT.tables.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(updatable = false,unique = true)
    private Long reservationId;


    @Column(nullable = false)
    private int numberOfPersons;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private LocalTime reservationTime;


    public Reservation() {
    }

    public Reservation(Long reservationId, int numberOfPersons,
                       LocalDate reservationDate, LocalTime reservationTime) {
        this.reservationId = reservationId;
        this.numberOfPersons = numberOfPersons;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }
}
