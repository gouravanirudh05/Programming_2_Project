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
import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.Room;
import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final RoomStore roomStore;

    public RoomController() {
        this.roomStore = RoomStore.getInstance();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId) {
        Room room = roomStore.findRoom(roomId);
        if (room == null) {
            return ResponseEntity.status(404).body("Room not found");
        }
        return ResponseEntity.ok(room);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoom(@RequestBody JsonNode json) {
        String roomId = json.get("roomId").asText();
        int capacity = json.get("capacity").asInt();
        String roomTypeId = json.get("roomTypeId").asText();
        // String roomTypeName = json.get("roomTypeName").asText();
        DateTime housekeepingLast = DateTime.fromISOString(json.get("housekeepingLast").asText());
        Room room = new Room(roomId, capacity, roomTypeId, housekeepingLast);
        roomStore.addRoom(room);
        return ResponseEntity.ok().body("Room added successfully");
    }

    @PostMapping("/remove/{roomId}")
    public ResponseEntity<?> removeRoom(@PathVariable String roomId) {
        Room room = roomStore.deleteRoom(roomId);
        if (room == null) {
            return ResponseEntity.status(404).body("Room not found");
        }
        return ResponseEntity.ok().body("Room removed successfully");
    }

    @PostMapping("/update/{roomId}")
    public ResponseEntity<?> updateRoom(@PathVariable String roomId, @RequestBody JsonNode json) {
        Room existingRoom = roomStore.findRoom(roomId);
        if (existingRoom == null) {
            return ResponseEntity.status(404).body("Room not found");
        }

        String roomTypeId = json.get("roomTypeId").asText();
        int capacity = json.get("capacity").asInt();
        DateTime housekeepingLast = DateTime.fromISOString(json.get("housekeepingLast").asText());
        Room updatedRoom = new Room(roomId, capacity, roomTypeId, housekeepingLast);
        roomStore.updateRoom(roomId, updatedRoom);
        return ResponseEntity.ok().body("Room updated successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRooms() {
        ArrayList<Room> rooms = roomStore.getRooms();
        return ResponseEntity.ok().body(rooms);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveToFile() {
        try {
            roomStore.save();
            return ResponseEntity.ok().body("Room data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save room data: " + e.getMessage());
        }
    }

    @PostMapping("/load")
    public ResponseEntity<?> loadFromFile() {
        try {
            roomStore.load();
            return ResponseEntity.ok().body("Room data loaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load room data: " + e.getMessage());
        }
    }
}