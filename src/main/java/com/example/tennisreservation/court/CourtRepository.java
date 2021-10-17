package com.example.tennisreservation.court;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourtRepository extends JpaRepository<Court, Long> {

    Court findByNumber(int number);

}