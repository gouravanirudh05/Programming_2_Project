package com.operatoroverloaded.hotel.controller;

// Importing required libraries and classes
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

@RestController // This class is a REST controller
@RequestMapping("/api/staff")   // Base mapping for all endpoints in this controller
public class StaffController {

    private final StaffStore staffStore;        // Singleton store for managing staff data

    // Constructor initializing the staff store instance
    public StaffController() {
        this.staffStore = StaffStore.getInstance();
    }

    // Endpoint to fetch details of a staff member by their ID
    @GetMapping("/{staffID}")
    public ResponseEntity<?> getStaff(@PathVariable int staffID) {
        Staff staff = staffStore.getStaffById(staffID);
        if (staff == null) {
            // Return 404 if staff member is not found
            return ResponseEntity.status(404).body("Staff not found");
        }
        // Return staff details
        return ResponseEntity.ok(staff);
    }

    // Endpoint to add a new staff member
    @PostMapping("/add")
    public ResponseEntity<?> addStaff(@RequestBody JsonNode json) {
        // Extracting staff details from the JSON payload
        int staffID = json.get("staffID").asInt();
        staffID = staffStore.getAllStaff().size() + 1;
        String name = json.get("name").asText();
        float salary = (float) json.get("salary").asDouble();
        int phone = json.get("phone").asInt();
        String address = json.get("address").asText();
        String role = json.get("role").asText();
        DateTime workingFrom = DateTime.fromISOString(json.get("workingFrom").asText());
        DateTime retiredOn = json.has("retiredOn") ? DateTime.fromISOString(json.get("retiredOn").asText()) : null;
        String assignedTo = json.get("assignedTo").asText();

        // Creating and adding the new staff object
        Staff staff = new Staff(staffID, name, salary, phone, address, role, workingFrom, retiredOn, assignedTo);
        staffStore.addStaff(staff);
        return ResponseEntity.ok().body("Staff added successfully");
    }

    // Endpoint to update an existing staff member's details
    @PutMapping("/update/{staffID}")
    public ResponseEntity<?> updateStaff(@PathVariable int staffID, @RequestBody JsonNode json) {
        Staff existingStaff = staffStore.getStaffById(staffID);
        if (existingStaff == null) {
            // Return 404 if staff member is not found
            return ResponseEntity.status(404).body("Staff not found");
        }

        // Update fields if present in the JSON payload
        if (json.has("name")) existingStaff.setName(json.get("name").asText());
        if (json.has("salary")) existingStaff.setSalary((float) json.get("salary").asDouble());
        if (json.has("phone")) existingStaff.setPhone(json.get("phone").asInt());
        if (json.has("address")) existingStaff.setAddress(json.get("address").asText());
        if (json.has("role")) existingStaff.setRole(json.get("role").asText());
        if (json.has("workingFrom")) existingStaff.setWorkingFrom(DateTime.fromISOString(json.get("workingFrom").asText()));
        if (json.has("retiredOn")) existingStaff.setRetiredOn(DateTime.fromISOString(json.get("retiredOn").asText()));
        if (json.has("assignedTo")) existingStaff.setAssignedTo(json.get("assignedTo").asText());

        // Save updated staff details to the store
        staffStore.updateStaff(existingStaff);
        return ResponseEntity.ok().body("Staff updated successfully");
    }

    // Endpoint to remove a staff member by ID
    @PostMapping("/remove/{staffID}")
    public ResponseEntity<?> removeStaff(@PathVariable int staffID) {
        Staff staff = staffStore.getStaffById(staffID);
        if (staff == null) {
            // Return 404 if staff member is not found
            return ResponseEntity.status(404).body("Staff not found");
        }
        // Remove staff member from the store
        staffStore.removeStaff(staffID);
        return ResponseEntity.ok().body("Staff removed successfully");
    }

    // Endpoint to fetch details of all staff members
    @GetMapping("/list")
    public ResponseEntity<?> getAllStaff() {
       ArrayList<Staff> staffList = staffStore.getAllStaff();
        return ResponseEntity.ok().body(staffList);
    }

    // Endpoint to save staff data to a file
    @PostMapping("/save")
    public ResponseEntity<?> saveToFile() {
        try {
            // Save staff data to a file
            staffStore.saveToFile();
            return ResponseEntity.ok().body("Staff data saved successfully");
        } catch (Exception e) {
            // Return 500 if an error occurs while saving data
            return ResponseEntity.status(500).body("Failed to save staff data: " + e.getMessage());
        }
    }

    // Endpoint to load staff data from a file
    @PostMapping("/load")
    public ResponseEntity<?> loadFromFile() {
        try {
            // Load staff data from a file
            staffStore.loadFromFile();
            return ResponseEntity.ok().body("Staff data loaded successfully");
        } catch (Exception e) {
            // Return 500 if an error occurs while loading data
            return ResponseEntity.status(500).body("Failed to load staff data: " + e.getMessage());
        }
    }
}
