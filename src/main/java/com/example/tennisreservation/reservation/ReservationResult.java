package com.example.tennisreservation.reservation;

public class ReservationResult {

    private Double price;
    private String message;

    ReservationResult(Double price) {
        this.message = "Reservation successfully created";
        this.price = price;
    }

    ReservationResult(String message) {
        this.message = message;
        this.price = null;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
