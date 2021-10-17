package com.example.tennisreservation.customer;

import com.example.tennisreservation.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String phoneNumber;
    private String name;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Reservation> reservation;


    public Customer() {};

    /**
     * Constructor for customer class.
     *
     * @param name customers name
     * @param phoneNumber customers phone number
     */
    public Customer(String name, String phoneNumber) {
        if (! isCorrectName(name)) {
            throw new IllegalArgumentException(name + ": invalid format of name, expected in format Name Surname");
        }
        this.name = name;

        if (! isCorrectPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException(phoneNumber + ": invalid format of phone number, expected in format 123 456 789");
        }
        this.phoneNumber = phoneNumber.replaceAll("\\s","");

        this.reservation = new ArrayList<>();
    }

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }

    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            throw new NullPointerException("Null reservations cannot be added");
        }

        this.reservation.add(reservation);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.replaceAll("\\s","");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if name is in valid format.
     *
     * @param name name to be checked
     * @return true - name is in valid format
     *         false - otherwise
     */
    private boolean isCorrectName(String name) {
        if (name == null) {
            return false;
        }
        String regex = "\\p{L}+ \\p{L}+";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(name).matches();
    }

    /**
     * Checks if phone number is in valid format.
     *
     * @param number number to be checked
     * @return true - number is in valid format
     *         false - otherwise
     */
    private boolean isCorrectPhoneNumber(String number) {
        if (number == null) {
            return false;
        }
        String regex = "\\d{3} ?\\d{3} ?\\d{3} ?";
        Pattern pattern = Pattern.compile(regex);
        return  pattern.matcher(number).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, name, reservation);
    }

    @Override
    public String toString() {
        return name + ": " + phoneNumber;
    }
}
