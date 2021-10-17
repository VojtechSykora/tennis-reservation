package com.example.tennisreservation.court;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourtController {

    private final CourtRepository repository;

    CourtController(CourtRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/courts")
    List<Court> all() {
        return repository.findAll();
    }
}
