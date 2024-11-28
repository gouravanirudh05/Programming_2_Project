package com.operatoroverloaded.hotel.stores.reservationstore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.Reservation;

public abstract class ReservationStore {
    public static ReservationStore reservationStore = null;

    // Singleton Pattern
    public static ReservationStore getInstance() {
        return reservationStore;
    }

    // Method to retrieve the singleton instance
    public static void setInstance(ReservationStore reservationStore) {
        ReservationStore.reservationStore = reservationStore;
    }

    // Abstract methods for managing reservations (to be implemented by subclasses)
    // Adds a reservation to the store
    public abstract void addReservation(Reservation reservation);

    // Removes a reservation by its ID
    public abstract void removeReservation(int reservationId);

    // Updates a reservation
    public abstract void updateReservation(int reservationId, Reservation updatedReservation);

    // Retrieves a reservation by its ID
    public abstract Reservation getReservation(int reservationId);

    // Retrieves all reservations
    public abstract ArrayList<Reservation> getAllReservations();

    // Saves the reservation data to persistent storage
    public abstract void save();

    // Loads the reservation data from persistent storage
    public abstract void load();

    // Abstract method for creating a reservation only if no time overlaps exist
    public abstract Reservation createReservationIfNoOverlap(
            int reservationId, String roomID, String customerID,
            DateTime startDateTime, DateTime endDateTime, int billId);

    // Method to add a reservation to the store only if no overlaps are detected
    public void addReservationIfNoOverlap(Reservation reservation) {
        // Check if a valid reservation can be created without time conflicts
        Reservation existing = createReservationIfNoOverlap(
                reservation.getReservationId(),
                reservation.getRoomID(),
                reservation.getcustomerID(),
                reservation.getStartDateTime(),
                reservation.getEndDateTime(),
                reservation.getBillId());
        // If no conflicts, add the reservation; otherwise, print a conflict message
        if (existing != null) {
            addReservation(existing);
        } else {
            System.out.println("Cannot add reservation: Time conflict detected.");
        }
    }
}
