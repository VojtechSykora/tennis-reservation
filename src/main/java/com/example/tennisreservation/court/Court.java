package com.example.tennisreservation.court;

import javax.persistence.*;

@Entity
public class Court {

    // private static HashMap<Surface, Double> RATES;

    @Id
    @GeneratedValue
    private long id;
    private Surface surface;
    private double minuteRate;
    private int number;

    public double getMinuteRate() {
        return minuteRate;
    }

    public void setMinuteRate(double minuteRate) {
        this.minuteRate = minuteRate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Court() {};

    public Court(int number, Surface surface, double minuteRate) {
        this.surface = surface;
        this.minuteRate = minuteRate;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return "Court {" +
                "id=" + id +
                ", surface=" + surface +
                ", minuteRate=" + minuteRate +
                ", number=" + number +
                '}';
    }
}
