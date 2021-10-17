package com.example.tennisreservation.court;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class for representing single court. Court can be identified by id (for database purposes) and
 * number (for reservation purposes).
 *
 */
@Entity
public class Court {

    @Id
    @GeneratedValue
    private long id;
    private Surface surface;
    @Column(unique = true)
    private int number;

    public Court() {};

    public Court(int number, Surface surface) {
        this.surface = surface;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return "Court number " + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Court court = (Court) o;
        return number == court.number && surface == court.surface;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surface, number);
    }
}
