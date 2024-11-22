package com.operatoroverloaded.hotel.stores.reservationstore;

import com.operatoroverloaded.hotel.models.Reservation;

import java.util.ArrayList;

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
}
