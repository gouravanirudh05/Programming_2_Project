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

    public static void setInstance(ReservationStore reservationStore) {
        ReservationStore.reservationStore = reservationStore;
    }

    // Abstract methods for managing reservations
    public abstract void addReservation(Reservation reservation);

    public abstract void removeReservation(int reservationId);

    public abstract void updateReservation(int reservationId, Reservation updatedReservation);

    public abstract Reservation getReservation(int reservationId);

    public abstract ArrayList<Reservation> getAllReservations();

    public abstract void save();

    public abstract void load();

    // New abstract method to create a reservation if no overlap exists
    public abstract Reservation createReservationIfNoOverlap(
            int reservationId, String roomID, String guestName,
            DateTime startDateTime, DateTime endDateTime, int billId
    );

    // Implementation of `addReservation` to ensure no overlaps
    public void addReservationIfNoOverlap(Reservation reservation) {
        Reservation existing = createReservationIfNoOverlap(
                reservation.getReservationId(),
                reservation.getRoomID(),
                reservation.getGuestName(),
                reservation.getStartDateTime(),
                reservation.getEndDateTime(),
                reservation.getBillId()
        );
        if (existing != null) {
            addReservation(existing);
        } else {
            System.out.println("Cannot add reservation: Time conflict detected.");
        }
    }
}
