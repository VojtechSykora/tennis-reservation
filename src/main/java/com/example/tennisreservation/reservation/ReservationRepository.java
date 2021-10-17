package com.example.tennisreservation.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCourtId(long courtId);
    List<Reservation> findByCourtNumber(int courtNumber);
    Reservation findFirstByCourtNumberAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(int courtNumber, LocalDateTime time1, LocalDateTime time2);
    Reservation findFirstByCourtNumberAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(int courtNumber, LocalDateTime startTime, LocalDateTime endTime);

}
