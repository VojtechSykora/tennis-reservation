package com.example.tennisreservation.reservation;

import java.time.LocalDateTime;

/**
 * Class representing data format for incoming POST requests to endpoint /reservation
 *
 */
public class ReservationForm {
    private int courtNumber;
    private GameType type;
    private String phoneNumber;
    private String customerName;
    private LocalDateTime startTime;
    private int duration;

    public ReservationForm() {
    }

    public ReservationForm(int courtNumber, GameType type, String phoneNumber, String customerName, LocalDateTime startTime, int duration) {
        this.courtNumber = courtNumber;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(int courtNumber) {
        this.courtNumber = courtNumber;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
