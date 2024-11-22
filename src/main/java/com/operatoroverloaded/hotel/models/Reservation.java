package com.operatoroverloaded.hotel.models;
import java.time.LocalDateTime;

public abstract class Reservation {
    protected Room room;
    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;
    protected double bill;

    // Constructor
    public Reservation(Room room, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.room = room;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.bill = 0.0;
    }

    // Abstract methods
    public abstract void calculateBill();
    public abstract void displayReservationDetails();

    // Getters and Setters
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
}
