package com.example.tennisreservation.reservation;

import com.example.tennisreservation.court.Court;
import com.example.tennisreservation.customer.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reservation {


    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime startTime;
    private int minutesDuration;
    private GameType gameType;

    @ManyToOne
    private Court court;

    @JsonBackReference
    @ManyToOne
    private Customer customer;


    public Reservation() {
    }

    public Reservation(LocalDateTime startTime, int minutesDuration, Court court, GameType gameType) {
        this.startTime = startTime;
        this.minutesDuration = minutesDuration;
        this.gameType = gameType;
        this.court = court;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getMinutesDuration() {
        return minutesDuration;
    }

    public void setMinutesDuration(int minutesDuration) {
        this.minutesDuration = minutesDuration;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getEndTime() {
        return this.startTime.plusMinutes(minutesDuration);
    }

    @Override
    public String toString() {
        return "Reservation {" +
                "id=" + id +
                ", startTime=" + startTime +
                ", minutesDuration=" + minutesDuration +
                ", gameType=" + gameType +
                ", court=" + court +
                '}';
    }
}
