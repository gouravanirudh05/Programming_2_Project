package com.operatoroverloaded.hotel.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.Staff;
import com.operatoroverloaded.hotel.stores.staffstore.StaffStore;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffStore staffStore;

    public StaffController() {
        this.staffStore = StaffStore.getInstance();
    }

    @GetMapping("/{staffID}")
    public ResponseEntity<?> getStaff(@PathVariable int staffID) {
        Staff staff = staffStore.getStaffById(staffID);
        if (staff == null) {
            return ResponseEntity.status(404).body("Staff not found");
        }
        return ResponseEntity.ok(staff);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStaff(@RequestBody JsonNode json) {
        int staffID = json.get("staffID").asInt();
        String name = json.get("name").asText();
        float salary = (float) json.get("salary").asDouble();
        int phone = json.get("phone").asInt();
        String address = json.get("address").asText();
        String role = json.get("role").asText();
        DateTime workingFrom = DateTime.fromISOString(json.get("workingFrom").asText());
        DateTime retiredOn = json.has("retiredOn") ? DateTime.fromISOString(json.get("retiredOn").asText()) : null;
        String assignedTo = json.get("assignedTo").asText();

        Staff staff = new Staff(staffID, name, salary, phone, address, role, workingFrom, retiredOn, assignedTo);
        staffStore.addStaff(staff);
        return ResponseEntity.ok().body("Staff added successfully");
    }

    @PutMapping("/update/{staffID}")
    public ResponseEntity<?> updateStaff(@PathVariable int staffID, @RequestBody JsonNode json) {
        Staff existingStaff = staffStore.getStaffById(staffID);
        if (existingStaff == null) {
            return ResponseEntity.status(404).body("Staff not found");
        }

        if (json.has("name")) existingStaff.setName(json.get("name").asText());
        if (json.has("salary")) existingStaff.setSalary((float) json.get("salary").asDouble());
        if (json.has("phone")) existingStaff.setPhone(json.get("phone").asInt());
        if (json.has("address")) existingStaff.setAddress(json.get("address").asText());
        if (json.has("role")) existingStaff.setRole(json.get("role").asText());
        if (json.has("workingFrom")) existingStaff.setWorkingFrom(DateTime.fromISOString(json.get("workingFrom").asText()));
        if (json.has("retiredOn")) existingStaff.setRetiredOn(DateTime.fromISOString(json.get("retiredOn").asText()));
        if (json.has("assignedTo")) existingStaff.setAssignedTo(json.get("assignedTo").asText());

        staffStore.updateStaff(existingStaff);
        return ResponseEntity.ok().body("Staff updated successfully");
    }

    @PostMapping("/remove/{staffID}")
    public ResponseEntity<?> removeStaff(@PathVariable int staffID) {
        Staff staff = staffStore.getStaffById(staffID);
        if (staff == null) {
            return ResponseEntity.status(404).body("Staff not found");
        }
        staffStore.removeStaff(staffID);
        return ResponseEntity.ok().body("Staff removed successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllStaff() {
       ArrayList<Staff> staffList = staffStore.getAllStaff();
        return ResponseEntity.ok().body(staffList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveToFile() {
        try {
            staffStore.saveToFile();
            return ResponseEntity.ok().body("Staff data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save staff data: " + e.getMessage());
        }
    }

    @PostMapping("/load")
    public ResponseEntity<?> loadFromFile() {
        try {
            staffStore.loadFromFile();
            return ResponseEntity.ok().body("Staff data loaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load staff data: " + e.getMessage());
        }
    }
}
