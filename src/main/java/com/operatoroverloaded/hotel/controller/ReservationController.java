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
import com.operatoroverloaded.hotel.models.Reservation;
import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.Room;
import com.operatoroverloaded.hotel.models.Bill;
import com.operatoroverloaded.hotel.stores.reservationstore.ReservationStore;

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
        int roomId = json.get("roomId").asInt();
        Room room = new Room(roomId); // Assuming Room constructor takes roomId, modify as needed
        String guestName = json.get("guestName").asText();
        DateTime startDateTime = DateTime.fromISOString(json.get("startDateTime").asText());
        DateTime endDateTime = DateTime.fromISOString(json.get("endDateTime").asText());
        Bill bill = new Bill(json.get("billId").asInt()); // Assuming Bill constructor takes billId, modify as needed

        Reservation reservation = new Reservation(
            reservationStore.getAllReservations().size() + 1, // Generate new reservation ID
            room,
            guestName,
            startDateTime,
            endDateTime,
            bill
        );
        reservationStore.addReservation(reservation);
        return ResponseEntity.ok().body("Reservation added successfully");
    }

    @PostMapping("/remove/{reservationId}")
    public ResponseEntity<?> removeReservation(@PathVariable int reservationId) {
        reservationStore.removeReservation(reservationId);
        return ResponseEntity.ok().body("Reservation removed successfully");
    }

    @PostMapping("/update/{reservationId}")
    public ResponseEntity<?> updateReservation(@PathVariable int reservationId, @RequestBody JsonNode json) {
        int roomId = json.get("roomId").asInt();
        Room room = new Room(roomId); // Assuming Room constructor takes roomId, modify as needed
        String guestName = json.get("guestName").asText();
        DateTime startDateTime = DateTime.fromISOString(json.get("startDateTime").asText());
        DateTime endDateTime = DateTime.fromISOString(json.get("endDateTime").asText());
        Bill bill = new Bill(json.get("billId").asInt()); // Assuming Bill constructor takes billId, modify as needed

        Reservation updatedReservation = new Reservation(
            reservationId,
            room,
            guestName,
            startDateTime,
            endDateTime,
            bill
        );
        reservationStore.updateReservation(reservationId, updatedReservation);
        return ResponseEntity.ok().body("Reservation updated successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReservations() {
        ArrayList<Reservation> reservations = reservationStore.getAllReservations();
        return ResponseEntity.ok(reservations);
    }
}
