package com.example.tennisreservation.court;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourtRepository extends JpaRepository<Court, Long> {
    /**
     * Finds court by unique court number.
     * Court number != Court id
     *
     * @param number court number
     * @return court
     */
    Court findByNumber(int number);

}