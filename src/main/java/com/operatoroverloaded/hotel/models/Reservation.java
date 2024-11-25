package com.operatoroverloaded.hotel.models;
import java.util.ArrayList;
import java.util.List;

import com.operatoroverloaded.hotel.stores.billstore.InMemoryBillStore;

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

    // New method to attempt creating a reservation
    public static Reservation createReservationIfNoOverlap(
            List<Reservation> reservations, int reservationId, String roomID, 
            String guestName, DateTime startDateTime, DateTime endDateTime, int billId) {

        // Check for overlaps with existing reservations
        for (Reservation reservation : reservations) {
            if (reservation.getRoomID().equals(roomID) && 
                (startDateTime.compareTo(reservation.getEndDateTime())<0) &&
                (endDateTime.compareTo(reservation.getStartDateTime()))>0) {
                return null; // Overlap found, return null
            }
        }

        // No overlaps, create and return the new reservation
        return new Reservation(reservationId, roomID, guestName, startDateTime, endDateTime, billId);
    }
    public boolean updateReservation(DateTime newStartDateTime, DateTime newEndDateTime, float dailyRate) {
    InMemoryBillStore billStore = InMemoryBillStore.getInstance();

    // Fetch the associated Bill object using billId
    Bill bill = billStore.getBill(this.billId);
    if (bill == null) {
        System.err.println("Bill not found for the given billId: " + this.billId);
        return false;
    }

    // Validate the new date-time range
    if (newStartDateTime.compareTo(newEndDateTime) >= 0) {
        System.err.println("Invalid date range: Start time must be before end time.");
        return false;
    }

    // Update reservation dates
    this.startDateTime = newStartDateTime;
    this.endDateTime = newEndDateTime;

    // Calculate the number of days for the stay
    int days = newStartDateTime.dateDifference(newEndDateTime);

    // Calculate the new room charges
    float totalRoomCharge = days * dailyRate;

    // Prepare updated bill details
    ArrayList<String> purchased = new ArrayList<>();
    ArrayList<Float> purchasedList = new ArrayList<>();
    ArrayList<Integer> quantity = new ArrayList<>();

    // Add room charges to the bill
    purchased.add("Room Charge");
    purchasedList.add(dailyRate);
    quantity.add(days);

    // Update the bill details
    bill.setItems(purchased, purchasedList, quantity);
    bill.setAmount(totalRoomCharge);
    bill.setPayedOn(DateTime.getCurrentTime());

    // Update the total amount in the reservation
    this.totalAmount = totalRoomCharge;

    // Print confirmation and return
    System.out.println("Reservation and associated bill updated successfully.");
    return true;
}
}
