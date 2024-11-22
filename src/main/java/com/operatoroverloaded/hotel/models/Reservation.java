package com.operatoroverloaded.hotel.models;
import com.operatoroverloaded.hotel.models.Room;
import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.Bill;

import java.util.*;
public abstract class Reservation {
    protected int reservationId; // Unique identifier for the reservation
    protected Room room;
    protected DateTime startDateTime;
    protected DateTime endDateTime;
    protected double totalAmount; // Updated to align with Bill's amount type
    protected String guestName; // Name of the guest
    protected Bill bill; // Associated Bill object

    // Default constructor
    public Reservation() {
        this.reservationId = -1;
        this.room = null;
        this.startDateTime = new DateTime(0, 0, 0, 0, 0, 0);
        this.endDateTime = new DateTime(0, 0, 0, 0, 0, 0);
        this.totalAmount = 0.0;
        this.guestName = "";
        this.bill = null;
    }

    // Parameterized constructor
    public Reservation(int reservationId, Room room, String guestName, DateTime startDateTime, DateTime endDateTime, Bill bill) {
        this.reservationId = reservationId;
        this.room = room;
        this.guestName = guestName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.totalAmount = bill != null ? bill.getAmount() : 0.0;
        this.bill = bill;
    }
    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
        if (bill != null) {
            this.totalAmount = bill.getAmount();
        }
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", room=" + room +
                ", startDateTime=" + startDateTime.getDateString() + " " + startDateTime.getTimeString() +
                ", endDateTime=" + endDateTime.getDateString() + " " + endDateTime.getTimeString() +
                ", totalAmount=" + totalAmount +
                ", guestName='" + guestName + '\'' +
                ", bill=" + (bill != null ? bill.toString() : "No Bill") +
                '}';
    }
}
