package com.example.tennisreservation.customer;

import com.example.tennisreservation.reservation.Reservation;
import com.example.tennisreservation.reservation.ReservationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerRepository repository;

    CustomerController (CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customer")
    Customer getCourtReservations(@RequestParam String number) {
        return repository.findByPhoneNumber(number);
    }


}