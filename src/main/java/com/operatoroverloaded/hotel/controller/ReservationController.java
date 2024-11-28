package com.operatoroverloaded.hotel.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.operatoroverloaded.hotel.models.Bill;
import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.Reservation;
import com.operatoroverloaded.hotel.models.Room;
import com.operatoroverloaded.hotel.stores.billstore.BillStore;
import com.operatoroverloaded.hotel.stores.reservationstore.ReservationStore;
import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;
import com.operatoroverloaded.hotel.stores.roomtypestore.RoomTypeStore;
@RestController// Marks this class as a REST API controller
@RequestMapping("/api/reservation")// Maps all endpoints in this controller under the `/api/reservation` path
public class ReservationController {

    private final ReservationStore reservationStore;// Singleton instance for managing reservations
     // Constructor to initialize the ReservationStore instance
    public ReservationController() {
        this.reservationStore = ReservationStore.getInstance();
    }

    // GET method to retrieve a reservation by ID
    @GetMapping("/{reservationId}")
    public ResponseEntity<?> getReservation(@PathVariable int reservationId) {
        Reservation reservation = reservationStore.getReservation(reservationId);
        // If the reservation is not found, return a 404 response
        if (reservation == null) {
            return ResponseEntity.status(404).body("Reservation not found");
        }
        return ResponseEntity.ok(reservation);// Return the found reservation
    }
    // POST method to add a new reservation
    @PostMapping("/add")
    public ResponseEntity<?> addReservation(@RequestBody JsonNode json) {
        // Extracting data from the JSON payload
        String roomId = json.get("roomId").asText();
        System.err.println(roomId);
        String customerID = json.get("customerID").asText();
        DateTime startDateTime = DateTime.fromISOString(json.get("startDateTime").asText());
        DateTime endDateTime = DateTime.fromISOString(json.get("endDateTime").asText());
        int billId = 0;
        // Preparing bill data
        ArrayList<String> purchased = new ArrayList<>();
        purchased.add("Room Charge");
        ArrayList<Float> purchasedList = new ArrayList<>();
        purchasedList.add(RoomTypeStore.getInstance().findRoomType(RoomStore.getInstance().findRoom(roomId).getRoomTypeId()).getTariff());
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add((int) endDateTime.dateDifference(startDateTime)/(60*60*24));
        // Check for overlapping reservations
        for (Reservation reservation : reservationStore.getAllReservations()) {
            if (reservation.getRoomID().equals(roomId) && 
                (startDateTime.compareTo(reservation.getEndDateTime())<0) &&
                (endDateTime.compareTo(reservation.getStartDateTime()))>0) {
                return ResponseEntity.status(409).body("Time conflict detected. Reservation could not be created."); // Overlap found, return null
            }
        }
        // Create and store a new bill
        Bill bill = BillStore.getInstance().addBill(purchased, purchasedList, quantity, endDateTime, false, roomId);
        if (bill == null) {
            return ResponseEntity.status(409).body("Time conflict detected. Reservation could not be created.");
        }

        //Create a reservation only if no overlap exists
        Reservation newReservation = reservationStore.createReservationIfNoOverlap(
            reservationStore.getAllReservations().size() + 1, // Generate new reservation ID
            roomId,
            customerID,
            startDateTime,
            endDateTime,
            bill.getBillId()
        );
        // Return the response based on reservation creation status
        if (newReservation == null) {
            return ResponseEntity.status(409).body("Time conflict detected. Reservation could not be created.");
        }

        return ResponseEntity.ok().body("Reservation added successfully: " + newReservation);
    }

    // POST method to remove a reservation by ID
    @PostMapping("/remove/{reservationId}")
    public ResponseEntity<?> removeReservation(@PathVariable int reservationId) {
        reservationStore.removeReservation(reservationId);
        return ResponseEntity.ok().body("Reservation removed successfully");
    }

     // POST method to update a reservation
    @PostMapping("/update/{reservationId}")
    public ResponseEntity<?> updateReservation(@PathVariable int reservationId, @RequestBody JsonNode json) {
        // Extracting updated data from the JSON payload
        String roomId = json.get("roomId").asText();
        String customerID = json.get("customerID").asText();
        DateTime startDateTime = DateTime.fromISOString(json.get("startDateTime").asText());
        DateTime endDateTime = DateTime.fromISOString(json.get("endDateTime").asText());
        int billId = json.get("billId").asInt();

        Reservation updatedReservation = new Reservation(
            reservationId,
            roomId, // Room id directly as string
            customerID,
            startDateTime,
            endDateTime,
            billId
        );
        // Update the reservation in the store
        reservationStore.updateReservation(reservationId, updatedReservation);
        return ResponseEntity.ok().body("Reservation updated successfully");
    }
    // GET method to fetch all reservations
    @GetMapping("/all")
    public ResponseEntity<?> getAllReservations() {
        ArrayList<Reservation> reservations = reservationStore.getAllReservations();
        return ResponseEntity.ok(reservations);
    }
    // POST method to find available rooms based on time and room type
    @PostMapping("/available")
    public ResponseEntity<?> getAvailableRooms(@RequestBody JsonNode json) {
        // Extracting data from the JSON payload
        DateTime startDateTime = DateTime.fromISOString(json.get("startDateTime").asText());
        DateTime endDateTime = DateTime.fromISOString(json.get("endDateTime").asText());
        String roomType = json.get("roomType").asText();
        ArrayList<Reservation> reservations = reservationStore.getAllReservations();
        ArrayList<Room> availableRooms = new ArrayList<>();
        // Check each room for availability
        for (Room room : RoomStore.getInstance().getRooms()) {
            if (room.getRoomTypeId().equals(RoomTypeStore.getInstance().findRoomType(roomType).getRoomTypeId())) {
                boolean flag = true;
                for (Reservation reservation : reservations) {
                    if (reservation.getRoomID().equals(room.getRoomId()) && 
                        (startDateTime.compareTo(reservation.getEndDateTime())<0) &&
                        (endDateTime.compareTo(reservation.getStartDateTime()))>0) {
                        flag = false;// Room is not available
                        break;
                    }
                }
                if (flag) {
                    availableRooms.add(room);// Add available room to the list
                }
            }
        }
        return ResponseEntity.ok().body(availableRooms);// Return the list of available rooms
    }
}
