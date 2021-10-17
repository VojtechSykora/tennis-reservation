package com.example.tennisreservation;

import com.example.tennisreservation.court.Court;
import com.example.tennisreservation.court.CourtRepository;
import com.example.tennisreservation.court.Surface;
import com.example.tennisreservation.customer.Customer;
import com.example.tennisreservation.customer.CustomerRepository;
import com.example.tennisreservation.reservation.GameType;
import com.example.tennisreservation.reservation.Reservation;
import com.example.tennisreservation.reservation.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for seeding database with some basic testing data
 *
 */
@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

        @Bean
        CommandLineRunner initDatabase(CourtRepository courtRepository, CustomerRepository customerRepository) {
            var customer = new Customer("Vojtech Sykora", "778040119");
            var customer2 = new Customer("Jan Novak", "746258751");
            var court1 = new Court(1, Surface.ARTIFICIAL);
            courtRepository.save(court1);
            var court2 = new Court(2, Surface.ARTIFICIAL);
            courtRepository.save(court2);
            var court3 = new Court(3, Surface.CLAY);
            courtRepository.save(court3);
            var court4 = new Court(4, Surface.CLAY);
            courtRepository.save(court4);
            var court5 = new Court(5, Surface.GRASS);
            courtRepository.save(court5);

            var reservation1 = new Reservation(LocalDateTime.now(), 60, court1, GameType.DOUBLE, customer);
            customer.addReservation(reservation1);

            customerRepository.save(customer);
            customerRepository.save(customer2);

            return args -> {
                log.info("Database seeded successfully");
            };
        }
    }


