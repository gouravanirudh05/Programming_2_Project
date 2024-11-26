package com.operatoroverloaded.hotel.controller;

import java.util.ArrayList;
import java.util.HashMap;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.Reservation;
import com.operatoroverloaded.hotel.models.Room;
import com.operatoroverloaded.hotel.stores.reservationstore.ReservationStore;
import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ReservationStore reservationStore;
    private final RoomStore roomStore;

    public DashboardController() {
        this.reservationStore = ReservationStore.getInstance();
        this.roomStore = RoomStore.getInstance();
    }

    // Endpoint to fetch all dynamic metrics
    @GetMapping
    public ResponseEntity<?> getDashboardMetrics() {
        HashMap<String, Object> metrics = new HashMap<>();

        // Fetch dynamic data
        ArrayList<Room> allRooms = roomStore.getRooms();
        ArrayList<Reservation> allReservations = reservationStore.getAllReservations();
        DateTime today = DateTime.getCurrentTime();

        int totalRooms = allRooms.size();
        int availableRooms = calculateAvailableRooms(allRooms, allReservations, today);
        int activeReservations = calculateActiveReservations(allReservations, today);
        double todaysRevenue = calculateTodaysRevenue(allReservations, today);
        double occupancyRate = calculateOccupancyRate(allRooms, allReservations, today);
        double averageDailyRate = calculateAverageDailyRate(allReservations);

        // Metrics
        metrics.put("totalRooms", totalRooms);
        metrics.put("availableRooms", availableRooms);
        metrics.put("totalReservations", allReservations.size());
        metrics.put("activeReservations", activeReservations);
        metrics.put("todaysRevenue", todaysRevenue);
        metrics.put("occupancyRate", occupancyRate);
        metrics.put("averageDailyRate", averageDailyRate);

        return ResponseEntity.ok().body(metrics);
    }

    // Helper method: Calculate available rooms dynamically
    private int calculateAvailableRooms(ArrayList<Room> rooms, ArrayList<Reservation> reservations, DateTime today) {
        int occupiedRooms = 0;
        for (Reservation reservation : reservations) {
            if (isReservationActive(reservation, today)) {
                occupiedRooms++;
            }
        }
        return rooms.size() - occupiedRooms;
    }

    // Helper method: Calculate active reservations dynamically
    private int calculateActiveReservations(ArrayList<Reservation> reservations, DateTime today) {
        int activeCount = 0;
        for (Reservation reservation : reservations) {
            if (isReservationActive(reservation, today)) {
                activeCount++;
            }
        }
        return activeCount;
    }

    // Helper method: Check if a reservation is active on a given day
    private boolean isReservationActive(Reservation reservation, DateTime today) {
        return today.compareTo(reservation.getStartDateTime()) >= 0 &&
                today.compareTo(reservation.getEndDateTime()) <= 0;
    }

    // Helper method: Calculate today's revenue dynamically
    private double calculateTodaysRevenue(ArrayList<Reservation> reservations, DateTime today) {
        double revenue = 0.0;
        for (Reservation reservation : reservations) {
            if (isReservationActive(reservation, today)) {
                revenue += reservation.getTotalAmount();
            }
        }
        return revenue;
    }

    // Helper method: Calculate occupancy rate dynamically
    private double calculateOccupancyRate(ArrayList<Room> rooms, ArrayList<Reservation> reservations, DateTime today) {
        int occupiedRooms = calculateActiveReservations(reservations, today);
        return (double) occupiedRooms / rooms.size() * 100.0;
    }

    // Helper method: Calculate average daily rate dynamically
    private double calculateAverageDailyRate(ArrayList<Reservation> reservations) {
        double totalRevenue = 0.0;
        int totalDays = 0;

        for (Reservation reservation : reservations) {
            int days = reservation.getStartDateTime().dateDifference(reservation.getEndDateTime());
            totalRevenue += reservation.getTotalAmount();
            totalDays += days;
        }

        return totalDays > 0 ? totalRevenue / totalDays : 0.0;
    }
}
