package com.example.tennisreservation.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCourtId(long courtId);
    List<Reservation> findByCourtNumber(int courtNumber);

}
