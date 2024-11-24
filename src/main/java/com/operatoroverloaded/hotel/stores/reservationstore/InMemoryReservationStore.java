package com.operatoroverloaded.hotel.stores.reservationstore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.Reservation;

public class InMemoryReservationStore extends ReservationStore {
    private static final InMemoryReservationStore instance = new InMemoryReservationStore();
    private ArrayList<Reservation> reservationData;

    // Load the native library
    static {
        System.loadLibrary("ReservationCPP"); // Ensure the compiled C++ library is available in the system path
    }

    // Singleton: Get the single instance of InMemoryReservationStore
    public static InMemoryReservationStore getInstance() {
        return instance;
    }

    // Constructor that initializes the reservation data and loads existing data
    private InMemoryReservationStore() {
        this.reservationData = new ArrayList<>();
        // this.load();
    }

    // Getter for the reservation data
    public ArrayList<Reservation> getReservationData() {
        return this.reservationData;
    }

    // Get a reservation by its ID
    @Override
    public Reservation getReservation(int reservationId) {
        for (Reservation reservation : reservationData) {
            if (reservationId == reservation.getReservationId()) {
                return reservation;
            }
        }
        return null;
    }

    // Save reservations to persistent storage
    @Override
    public void save() {
        saveReservationsNative();
    }

    // Load reservations from persistent storage
    @Override
    public void load() {
        loadReservationsNative();
    }

    // Add a new reservation to the data
    @Override
    public void addReservation(Reservation reservation) {
        if (reservation != null) {
            this.reservationData.add(reservation);
        }
    }

    // Remove a reservation by its ID
    @Override
    public void removeReservation(int reservationId) {
        for (Reservation reservation : reservationData) {
            if (reservation.getReservationId() == reservationId) {
                reservationData.remove(reservation);
                return;
            }
        }
    }

    // Update an existing reservation
    @Override
    public void updateReservation(int reservationId, Reservation updatedReservation) {
        for (int i = 0; i < reservationData.size(); i++) {
            if (reservationData.get(i).getReservationId() == reservationId) {
                reservationData.set(i, updatedReservation);
                return;
            }
        }
    }

    // Get all reservations as a list
    @Override
    public ArrayList<Reservation> getAllReservations() {
        return new ArrayList<>(this.reservationData);
    }

    // Native methods for saving and loading reservations
    private native void saveReservationsNative();

    private native void loadReservationsNative();

    // Implementation of the createReservationIfNoOverlap method
    @Override
    public Reservation createReservationIfNoOverlap(
            int reservationId, String roomID, String guestName,
            DateTime startDateTime, DateTime endDateTime, int billId
    ) {
        // Check for overlaps with existing reservations
        for (Reservation reservation : reservationData) {
            if (reservation.getRoomID().equals(roomID) &&
                    (startDateTime.compareTo(reservation.getEndDateTime())<0) &&
                    (endDateTime.compareTo(reservation.getStartDateTime()))>0) {
                return null; // Overlap detected
            }
        }
        // No overlaps, create a new reservation and add it to the data
        Reservation newReservation = new Reservation(reservationId, roomID, guestName, startDateTime, endDateTime, billId);
        addReservation(newReservation);
        return newReservation;
    }
}
