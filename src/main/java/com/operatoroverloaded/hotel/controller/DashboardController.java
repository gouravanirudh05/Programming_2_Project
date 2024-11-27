package com.operatoroverloaded.hotel.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operatoroverloaded.hotel.models.Bill;
import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.Hotel;
import com.operatoroverloaded.hotel.models.Reservation;
import com.operatoroverloaded.hotel.models.Room;
import com.operatoroverloaded.hotel.stores.billstore.BillStore;
import com.operatoroverloaded.hotel.stores.reservationstore.ReservationStore;
import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;
import com.operatoroverloaded.hotel.stores.staffstore.StaffStore;
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ReservationStore reservationStore;
    private final RoomStore roomStore;
    private final StaffStore staffStore;
    // private final RestaurantOrderStore restaurantOrderStore;
    private final Hotel hotel;

    public DashboardController() {
        this.reservationStore = ReservationStore.getInstance();
        this.roomStore = RoomStore.getInstance();
        this.staffStore = StaffStore.getInstance();
        // this.restaurantOrderStore = RestaurantOrderStore.getInstance();
        this.hotel = Hotel.getInstance(); // Assuming Hotel class has a singleton pattern
    }

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
        // int staffOnLeave = staffStore.getStaffOnLeaveCount(today);
        // int[] restaurantOrders = restaurantOrderStore.getOrderCounts(today); // [pending, served]
        int todayCheckIns = calculateTodayCheckIns(allReservations, today);
        int totalStaff = staffStore.getAllStaff().size();
        //double lastWeekOccupancyRate = calculateOccupancyRateForDate(allRooms, allReservations, DateTime.getCurrentTime().plusDays(-7));

        // Fetch hotel name and address
        metrics.put("hotelName", hotel.getName());
        metrics.put("hotelAddress", hotel.getAddress());
    
        metrics.put("totalStaff", totalStaff);
        // Metrics
        metrics.put("totalRooms", totalRooms);
        metrics.put("availableRooms", availableRooms);
        metrics.put("totalReservations", allReservations.size());
        metrics.put("activeReservations", activeReservations);
        metrics.put("todaysRevenue", todaysRevenue);
        metrics.put("occupancyRate", occupancyRate);
        metrics.put("averageDailyRate", averageDailyRate);
        // metrics.put("staffOnLeave", staffOnLeave);
        // metrics.put("restaurantOrders", restaurantOrders); // [pending, served]
        metrics.put("todayCheckIns", todayCheckIns);

        return ResponseEntity.ok().body(metrics);
    }

    private int calculateAvailableRooms(ArrayList<Room> rooms, ArrayList<Reservation> reservations, DateTime today) {
        int occupiedRooms = 0;
        for (Reservation reservation : reservations) {
            if (isReservationActive(reservation, today)) {
                occupiedRooms++;
            }
        }
        return rooms.size() - occupiedRooms;
    }

    private int calculateActiveReservations(ArrayList<Reservation> reservations, DateTime today) {
        int activeCount = 0;
        for (Reservation reservation : reservations) {
            if (isReservationActive(reservation, today)) {
                activeCount++;
            }
        }
        return activeCount;
    }

    private boolean isReservationActive(Reservation reservation, DateTime today) {
        return today.compareTo(reservation.getStartDateTime()) >= 0 &&
                today.compareTo(reservation.getEndDateTime()) <= 0;
    }

    private double calculateTodaysRevenue(ArrayList<Reservation> reservations, DateTime today) {
        ArrayList<Bill> bills = BillStore.getInstance().getBills();
        double revenue = 0.0, totalAmount = 0.0;
        for (Bill bill : bills) {
            System.out.println(bill.getPayedOnDT().dateDifference(today)+bill.getAmount());
            if (Math.abs(bill.getPayedOnDT().dateDifference(today)) <= 1) {
                totalAmount += bill.getAmount();
            }
        }
        return revenue;
    }

    private double calculateOccupancyRate(ArrayList<Room> rooms, ArrayList<Reservation> reservations, DateTime today) {
        int occupiedRooms = calculateActiveReservations(reservations, today);
        return (double) occupiedRooms / rooms.size() * 100.0;
    }

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

    private int calculateTodayCheckIns(ArrayList<Reservation> reservations, DateTime today) {
        int checkIns = 0;
        for (Reservation reservation : reservations) {
            if (today.compareTo(reservation.getStartDateTime()) == 0) {
                checkIns++;
            }
        }
        return checkIns;
    }
    private double calculateOccupancyRateForDate(ArrayList<Room> rooms, ArrayList<Reservation> reservations, DateTime date) {
        int occupiedRooms = 0;
        for (Reservation reservation : reservations) {
            if (isReservationActive(reservation, date)) {
                occupiedRooms++;
            }
        }
        return (double) occupiedRooms / rooms.size() * 100.0;
    }
    
}  
