package com.example.tennisreservation.reservation;

import com.example.tennisreservation.court.Court;
import com.example.tennisreservation.court.Surface;
import com.example.tennisreservation.customer.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class that represent a single reservation.
 *
 *
 */
@Entity
public class Reservation {


    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int minutesDuration;
    private GameType gameType;
    private double price;

    @ManyToOne
    private Court court;

    @JsonBackReference
    @ManyToOne
    private Customer customer;

    public Reservation() {
    }

    // Evidence of rates for different surface and game types
    private static final HashMap<Surface, Double> SURFACE_RATES;
    private static final HashMap<GameType, Double> GAME_RATES;

    static {
        SURFACE_RATES = new HashMap<>();
        SURFACE_RATES.put(Surface.CLAY, 5.0);
        SURFACE_RATES.put(Surface.ARTIFICIAL, 6.5);
        SURFACE_RATES.put(Surface.GRASS, 8.0);

        GAME_RATES = new HashMap<>();
        GAME_RATES.put(GameType.SINGLE, 1.0);
        GAME_RATES.put(GameType.DOUBLE, 1.5);
    }

    /**
     * Calculates price for reservation.
     *
     * @param type type of game
     * @param court reservations court
     * @param duration duration of reservation in minutes
     * @return price
     */
    public static double calculatePrice(GameType type, Court court, int duration) {
        return GAME_RATES.get(type) * SURFACE_RATES.get(court.getSurface()) * duration;
    }

    /**
     * Checks, if parameter duration is a valid duration for reservation.
     *
     * @param duration duration to be checked
     * @return true - if duration is valid
     *         false - otherwise
     */
    public static boolean isCorrectDuration(int duration) {
        return duration > 0 && duration % 30 == 0;
    }

    /**
     * Constructor for reservation
     *
     * @param startTime         reservations start time
     * @param minutesDuration   duration of reservation in minutes
     * @param court             reservations court
     * @param gameType          type of game
     * @param customer          customer
     */
    public Reservation(LocalDateTime startTime, int minutesDuration,
                       Court court, GameType gameType, Customer customer) {
        if (startTime == null) {
            throw new NullPointerException("Start time needs to be specified");
        }
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(minutesDuration);
        if (! isCorrectDuration(minutesDuration)) {
            throw new IllegalArgumentException("Reservation can be made for only for 30 minute intervals");
        }
        this.minutesDuration = minutesDuration;
        this.gameType = gameType;
        if (court == null) {
            throw new NullPointerException("Reservation needs to have assigned court");
        }
        this.court = court;
        if (customer == null) {
            throw new NullPointerException("Reservation needs to have assigned customer");
        }
        this.customer = customer;

        this.price = calculatePrice(gameType, court, minutesDuration);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static HashMap<Surface, Double> getSurfaceRates() {
        return SURFACE_RATES;
    }

    public static HashMap<GameType, Double> getGameRates() {
        return GAME_RATES;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        if (court == null) {
            throw new NullPointerException("Reservation needs to have assigned court");
        }
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
        if (startTime == null) {
            throw new NullPointerException("Start time needs to be specified");
        }
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(minutesDuration);
    }

    public int getMinutesDuration() {
        return minutesDuration;
    }

    public void setMinutesDuration(int minutesDuration) {
        if (! isCorrectDuration(minutesDuration)) {
            throw new IllegalArgumentException("Reservation can be made for only for 30 minute intervals");
        }
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
        if (customer == null) {
            throw new NullPointerException("Reservation needs to have assigned customer");
        }
        this.customer = customer;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (endTime == null) {
            throw new NullPointerException("End time needs to be specified");
        }
        this.endTime = endTime;
        this.startTime = endTime.minusMinutes(this.minutesDuration);

    }

    @Override
    public String toString() {
        return "Reservation:" +
                " from: " + startTime +
                " to: " + endTime +
                " gameType: " + gameType +
                " court: " + court;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return  minutesDuration == that.minutesDuration && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime) && gameType == that.gameType
                && Objects.equals(court, that.court) && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, minutesDuration, gameType, price, court, customer);
    }
}
