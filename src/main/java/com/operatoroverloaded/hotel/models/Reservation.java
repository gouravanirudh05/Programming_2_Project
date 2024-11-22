package com.operatoroverloaded.hotel.models;
import java.util.*;

public class Reservation {
    private int reservationId; // Unique identifier for the reservation
    private int roomNumber;   // Room number instead of Room object
    private DateTime startDateTime; // Start date and time of reservation
    private DateTime endDateTime;   // End date and time of reservation
    private double totalAmount;     // Total amount based on Bill
    private String guestName;       // Name of the guest
    private int billId;             // Bill ID instead of Bill object

    // Default constructor
    public Reservation() {
        this.reservationId = -1;
        this.roomNumber = 0;
        this.startDateTime = new DateTime(0, 0, 0, 0, 0, 0);
        this.endDateTime = new DateTime(0, 0, 0, 0, 0, 0);
        this.totalAmount = 0.0;
        this.guestName = "";
        this.billId = -1;
    }

    // Parameterized constructor
    public Reservation(int reservationId, int roomNumber, String guestName, DateTime startDateTime, DateTime endDateTime, int billId) {
        this.reservationId = reservationId;
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.billId = billId;
        this.totalAmount = 0.0; // This can be updated later when the bill information is available
    }

    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", roomNumber=" + roomNumber +
                ", startDateTime=" + startDateTime.getDateString() + " " + startDateTime.getTimeString() +
                ", endDateTime=" + endDateTime.getDateString() + " " + endDateTime.getTimeString() +
                ", totalAmount=" + totalAmount +
                ", guestName='" + guestName + '\'' +
                ", billId=" + (billId != -1 ? billId : "No Bill") +
                '}';
    }
}
