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
import com.operatoroverloaded.hotel.models.RoomType;
import com.operatoroverloaded.hotel.stores.roomtypestore.RoomTypeStore;

@RestController
@RequestMapping("/api/roomtype")
public class RoomTypeController {
    private final RoomTypeStore roomTypeStore;

    public RoomTypeController() {
        this.roomTypeStore = RoomTypeStore.getInstance();
    }

    @GetMapping("/{roomTypeId}")
    public ResponseEntity<?> getRoomType(@PathVariable String roomTypeId) {
        RoomType roomType = roomTypeStore.findRoomType(roomTypeId);
        if (roomType == null) {
            return ResponseEntity.status(404).body("Room type not found");
        }
        return ResponseEntity.ok(roomType);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoomType(@RequestBody JsonNode json) {
            String roomTypeId = json.get("roomTypeId").asText();
            String roomTypeName = json.get("roomTypeName").asText();
            float tariff = (float) json.get("tariff").asDouble();
            ArrayList<String> amenities = new ArrayList<String>();
            for (JsonNode amenity : json.get("amenities")) {
                amenities.add(amenity.asText());
            }
            RoomType roomType = new RoomType(roomTypeId, roomTypeName, tariff, amenities);
            roomTypeStore.addRoomType(roomType);
            return ResponseEntity.ok().body("Room type added successfully");
    }

    @PostMapping("/remove/{roomTypeId}")
    public ResponseEntity<?> removeRoomType(@PathVariable String roomTypeId) {
        RoomType roomType = roomTypeStore.deleteRoomType(roomTypeId);
        if (roomType == null) {
            return ResponseEntity.status(404).body("Room type not found");
        }
        return ResponseEntity.ok().body("Room type removed successfully");
    }

    @PostMapping("/update/{roomTypeId}")
    public ResponseEntity<?> updateRoomType(@PathVariable String roomTypeId, @RequestBody JsonNode json) {
        RoomType existingRoomType = roomTypeStore.findRoomType(roomTypeId);
        if (existingRoomType == null) {
            return ResponseEntity.status(404).body("Room type not found");
        }

        String roomTypeName = json.get("roomTypeName").asText();
        float tariff = (float) json.get("tariff").asDouble();
        ArrayList<String> amenities = new ArrayList<String>();
        for (JsonNode amenity : json.get("amenities")) {
            amenities.add(amenity.asText());
        }
        RoomType updatedRoomType = new RoomType(roomTypeId, roomTypeName, tariff, amenities);
        roomTypeStore.updateRoomType(roomTypeId, updatedRoomType);
        return ResponseEntity.ok().body("Room type updated successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRoomTypes() {
        ArrayList<RoomType> roomTypes = roomTypeStore.getRoomTypes();
        return ResponseEntity.ok().body(roomTypes);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveToFile() {
        try {
            roomTypeStore.save();
            return ResponseEntity.ok().body("Room type data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save room type data: " + e.getMessage());
        }
    }

    @PostMapping("/load")
    public ResponseEntity<?> loadFromFile() {
        try {
            roomTypeStore.load();
            return ResponseEntity.ok().body("Room type data loaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load room type data: " + e.getMessage());
        }
    }
}