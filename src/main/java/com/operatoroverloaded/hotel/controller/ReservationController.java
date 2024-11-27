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
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationStore reservationStore;

    public ReservationController() {
        this.reservationStore = ReservationStore.getInstance();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<?> getReservation(@PathVariable int reservationId) {
        Reservation reservation = reservationStore.getReservation(reservationId);
        if (reservation == null) {
            return ResponseEntity.status(404).body("Reservation not found");
        }
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReservation(@RequestBody JsonNode json) {
        String roomId = json.get("roomId").asText();
        String customerID = json.get("customerID").asText();
        DateTime startDateTime = DateTime.fromISOString(json.get("startDateTime").asText());
        DateTime endDateTime = DateTime.fromISOString(json.get("endDateTime").asText());
        int billId = 0;
        ArrayList<String> purchased = new ArrayList<>();
        purchased.add("Room Charge");
        ArrayList<Float> purchasedList = new ArrayList<>();
        purchasedList.add(RoomTypeStore.getInstance().findRoomType(RoomStore.getInstance().findRoom(roomId).getRoomTypeId()).getTariff());
        ArrayList<Integer> quantity = new ArrayList<>();
        purchasedList.add(1f);

        for (Reservation reservation : reservationStore.getAllReservations()) {
            if (reservation.getRoomID().equals(roomId) && 
                (startDateTime.compareTo(reservation.getEndDateTime())<0) &&
                (endDateTime.compareTo(reservation.getStartDateTime()))>0) {
                return ResponseEntity.status(409).body("Time conflict detected. Reservation could not be created."); // Overlap found, return null
            }
        }

        Bill bill = BillStore.getInstance().addBill(purchased, purchasedList, quantity, endDateTime, false, roomId);
        

        // Attempt to create a reservation only if no overlap exists
        Reservation newReservation = reservationStore.createReservationIfNoOverlap(
            reservationStore.getAllReservations().size() + 1, // Generate new reservation ID
            roomId,
            customerID,
            startDateTime,
            endDateTime,
            bill.getBillId()
        );

        if (newReservation == null) {
            return ResponseEntity.status(409).body("Time conflict detected. Reservation could not be created.");
        }

        return ResponseEntity.ok().body("Reservation added successfully: " + newReservation);
    }

    @PostMapping("/remove/{reservationId}")
    public ResponseEntity<?> removeReservation(@PathVariable int reservationId) {
        reservationStore.removeReservation(reservationId);
        return ResponseEntity.ok().body("Reservation removed successfully");
    }

    @PostMapping("/update/{reservationId}")
    public ResponseEntity<?> updateReservation(@PathVariable int reservationId, @RequestBody JsonNode json) {
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
        reservationStore.updateReservation(reservationId, updatedReservation);
        return ResponseEntity.ok().body("Reservation updated successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReservations() {
        ArrayList<Reservation> reservations = reservationStore.getAllReservations();
        return ResponseEntity.ok(reservations);
    }
    @PostMapping("/available")
    public ResponseEntity<?> getAvailableRooms(@RequestBody JsonNode json) {
        DateTime startDateTime = DateTime.fromISOString(json.get("startDateTime").asText());
        DateTime endDateTime = DateTime.fromISOString(json.get("endDateTime").asText());
        String roomType = json.get("roomType").asText();
        ArrayList<Reservation> reservations = reservationStore.getAllReservations();
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room : RoomStore.getInstance().getRooms()) {
            if (room.getRoomTypeId().equals(RoomTypeStore.getInstance().findRoomType(roomType).getRoomTypeId())) {
                boolean flag = true;
                for (Reservation reservation : reservations) {
                    if (reservation.getRoomID().equals(room.getRoomId()) && 
                        (startDateTime.compareTo(reservation.getEndDateTime())<0) &&
                        (endDateTime.compareTo(reservation.getStartDateTime()))>0) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    availableRooms.add(room);
                }
            }
        }
        return ResponseEntity.ok().body(availableRooms);
    }
}
