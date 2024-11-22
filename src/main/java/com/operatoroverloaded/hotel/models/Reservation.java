package com.operatoroverloaded.hotel.models;
import java.util.*;

public class Reservation {
    private int reservationId; 
    private String roomID;   
    private DateTime startDateTime; 
    private DateTime endDateTime;   
    private double totalAmount;     
    private String guestName;       
    private int billId;             

    
    public Reservation() {
        this.reservationId = -1;
        this.roomID = "";
        this.startDateTime = new DateTime(0, 0, 0, 0, 0, 0);
        this.endDateTime = new DateTime(0, 0, 0, 0, 0, 0);
        this.totalAmount = 0.0;
        this.guestName = "";
        this.billId = -1;
    }

    
    public Reservation(int reservationId, String roomID, String guestName, DateTime startDateTime, DateTime endDateTime, int billId) {
        this.reservationId = reservationId;
        this.roomID = roomID;
        this.guestName = guestName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.billId = billId;
        this.totalAmount = 0.0; 
    }

    
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
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
                ", roomID='" + roomID + '\'' +
                ", startDateTime=" + startDateTime.getDateString() + " " + startDateTime.getTimeString() +
                ", endDateTime=" + endDateTime.getDateString() + " " + endDateTime.getTimeString() +
                ", totalAmount=" + totalAmount +
                ", guestName='" + guestName + '\'' +
                ", billId=" + (billId != -1 ? billId : "No Bill") +
                '}';
    }
}
