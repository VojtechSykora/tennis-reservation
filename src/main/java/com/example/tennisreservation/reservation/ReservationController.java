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

    @GetMapping("/court/{courtId}/reservations")
    List<Reservation> getCourtReservations(@PathVariable int courtId) {
        return reservationRepository.findByCourtNumber(courtId);
    }

    @PostMapping("/reservation")
    double createReservation(@RequestBody ReservationForm form) {
        List<Reservation> reservations = reservationRepository.findByCourtNumber(form.getCourtNumber());
        Court court = courtRepository.findByNumber(form.getCourtNumber());
        Customer customer = customerRepository.findByPhoneNumber(form.getPhoneNumber());
        List<Reservation> customerReservations = customer.getReservation();

        Reservation newReservation = new Reservation(form.getStartTime(), form.getDuration(), court, form.getType());
        newReservation.setCustomer(customer);
        boolean overlaps = reservations.stream().anyMatch(reservation -> ReservationManager.reservationsOverlap(reservation, newReservation));

        if (overlaps) {
            return 0;
        }

        customerReservations.add(newReservation);
        customer.setReservation(customerReservations);

        customerRepository.save(customer);

        return ReservationManager.getPrice(newReservation);
    }

}
