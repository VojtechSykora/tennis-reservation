package com.example.tennisreservation.reservation;

import com.example.tennisreservation.court.Court;
import com.example.tennisreservation.court.CourtRepository;
import com.example.tennisreservation.customer.Customer;
import com.example.tennisreservation.customer.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final CourtRepository courtRepository;
    private final CustomerRepository customerRepository;

    ReservationController(ReservationRepository repository, CourtRepository courtRepository, CustomerRepository customerRepository) {
        this.reservationRepository = repository;
        this.courtRepository = courtRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Endpoint for getting all reservations for court identified by URL path paramater
     *
     * @param courtNumber URL path variable
     * @return list of reservations for specified court
     */
    @GetMapping("/courts/{courtNumber}/reservations")
    List<Reservation> getCourtReservations(@PathVariable int courtNumber) {
        return reservationRepository.findByCourtNumber(courtNumber);
    }

    /**
     * Endpoint for creating a new reservation.
     *
     *
     * @param form
     * @return On successfull creation - reservations price
     *         if something went wrong - error message and null price
     */
    @PostMapping("/reservation")
    ReservationResult createReservation(@RequestBody ReservationForm form) {

        Court court = courtRepository.findByNumber(form.getCourtNumber());
        if (court == null) {
            return new ReservationResult("Court number " + form.getCourtNumber() + " does not exist");
        }

        Customer customer = customerRepository.findByPhoneNumber(form.getPhoneNumber().replaceAll("\\s", ""));
        if (customer == null) {
            try {
                customer = new Customer(form.getCustomerName(), form.getPhoneNumber());
            } catch (IllegalArgumentException e) {
                return new ReservationResult(e.getMessage());
            }

        }

        Reservation newReservation;
        try {
            newReservation = new Reservation(form.getStartTime(), form.getDuration(), court, form.getType(), customer);
        } catch (Exception e) {
            return new ReservationResult(e.getMessage());
        }

        if (reservationOverlap(newReservation)) {
            return new ReservationResult("Court " + court.getNumber() +
                    " is already reserved.");
        }

        customer.addReservation(newReservation);
        customerRepository.save(customer);

        return new ReservationResult(newReservation.getPrice());
    }

    /**
     * Checks whether new reservation interferes with another existing one.
     *
     * @param newReservation reservation to be checked
     * @return true - reservation interferes with another one (and should not be placed)
     *         false - reservation does not overlap with another one (and can be placed)
     */
    private boolean reservationOverlap(Reservation newReservation) {
        Reservation startsBefore = reservationRepository
                .findFirstByCourtNumberAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        newReservation.getCourt().getNumber(),
                        newReservation.getStartTime(),
                        newReservation.getStartTime()
                );

        Reservation startsAfter = reservationRepository
                .findFirstByCourtNumberAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        newReservation.getCourt().getNumber(),
                        newReservation.getEndTime(),
                        newReservation.getEndTime()
                );

        Reservation startsBetween = reservationRepository
                .findFirstByCourtNumberAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
                        newReservation.getCourt().getNumber(),
                        newReservation.getStartTime(),
                        newReservation.getEndTime()
                );

        // other reservations are in progress in new reservation timespan
        if (startsBetween != null) {
            return true;
        }

        // new reservation is for the exact same time as another existing
        if (startsAfter != null && startsBefore != null) {
            return true;
        }

        // new reservation end time overlaps with existing reservation
        if (startsAfter != null && ! startsAfter.getStartTime().equals(newReservation.getEndTime())) {
            return true;
        }

        // new reservation start time overlaps with existing reservation
        if (startsBefore != null && ! startsBefore.getEndTime().equals(newReservation.getStartTime())) {
            return true;
        }

        return false;




    }




}
