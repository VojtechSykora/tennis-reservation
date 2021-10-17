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


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

        @Bean
        CommandLineRunner initDatabase(CourtRepository repository, ReservationRepository repository2, CustomerRepository customerRepository) {

            var reservation = new Reservation();
            var court = new Court(10, Surface.ARTIFICIAL, 5);
            var customer = new Customer("Vojtech", "778040119");


            reservation.setCourt(court);
            reservation.setMinutesDuration(60);
            reservation.setGameType(GameType.SINGLE);
            reservation.setStartTime(LocalDateTime.now());
            reservation.setCustomer(customer);
            List<Reservation> reservationList = new ArrayList<>();
            reservationList.add(reservation);
            customer.setReservation(reservationList);






            return args -> {
                log.info("Preloading " + repository.save(court));
                log.info("Preloading " + customerRepository.save(customer));
                log.info("Preloading " + repository.save(new Court(2, Surface.CLAY, 5)));
                log.info("Preloading " + reservation);


            };
        }
    }


