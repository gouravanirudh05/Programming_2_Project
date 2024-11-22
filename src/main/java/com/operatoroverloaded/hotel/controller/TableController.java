package com.operatoroverloaded.hotel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.operatoroverloaded.hotel.models.Table;
import com.operatoroverloaded.hotel.stores.tablestore.InMemoryTableStore;

import java.util.List;

@RestController
@RequestMapping("/api/table")
public class TableController {

    private final InMemoryTableStore tableStore;

    public TableController() {
        this.tableStore = InMemoryTableStore.getInstance();
    }

    // Get details of a single table by ID
    @GetMapping("/{tableId}")
    public ResponseEntity<?> getTable(@PathVariable int tableId) {
        Table table = tableStore.findTable(tableId);
        if (table == null) {
            return ResponseEntity.status(404).body("Table not found");
        }
        return ResponseEntity.ok(table);
    }

    // Get a list of all tables
    @GetMapping("/all")
    public ResponseEntity<?> getAllTables() {
        List<Table> tables = tableStore.getTables();
        return ResponseEntity.ok(tables);
    }

    // Add a new table
    @PostMapping("/add")
    public ResponseEntity<?> addTable(@RequestBody JsonNode json) {
        int tableNumber = json.get("tableNumber").asInt();
        int seatingCapacity = json.get("seatingCapacity").asInt();

        // Create a new table and add to store
        Table newTable = new Table(tableNumber, seatingCapacity);
        tableStore.addTable(newTable);
        return ResponseEntity.ok().body("Table added successfully");
    }

    // Remove a table by ID
    @PostMapping("/remove/{tableId}")
    public ResponseEntity<?> removeTable(@PathVariable int tableId) {
        Table removedTable = tableStore.deleteTable(tableId);
        if (removedTable == null) {
            return ResponseEntity.status(404).body("Table not found");
        }
        return ResponseEntity.ok().body("Table removed successfully");
    }

    // Update the table details (seating capacity and reservation status)
    @PostMapping("/update/{tableId}")
    public ResponseEntity<?> updateTable(@PathVariable int tableId, @RequestBody JsonNode json) {
        Table existingTable = tableStore.findTable(tableId);
        if (existingTable == null) {
            return ResponseEntity.status(404).body("Table not found");
        }

        int seatingCapacity = json.get("seatingCapacity").asInt(existingTable.getSeatingCapacity());
        boolean isReserved = json.has("isReserved") ? json.get("isReserved").asBoolean() : existingTable.isReserved();

        Table updatedTable = new Table(tableId, seatingCapacity);
        if (isReserved) {
            updatedTable.reserveTable();
        }
        tableStore.updateTable(tableId, updatedTable);
        return ResponseEntity.ok().body("Table updated successfully");
    }

    // Reserve a table by ID
    @PostMapping("/reserve/{tableId}")
    public ResponseEntity<?> reserveTable(@PathVariable int tableId) {
        boolean reserved = tableStore.reserveTable(tableId);
        if (!reserved) {
            return ResponseEntity.status(400).body("Table is already reserved or does not exist.");
        }
        return ResponseEntity.ok().body("Table reserved successfully.");
    }

    // Unreserve a table by ID
    @PostMapping("/unreserve/{tableId}")
    public ResponseEntity<?> unreserveTable(@PathVariable int tableId) {
        boolean unreserved = tableStore.unreserveTable(tableId);
        if (!unreserved) {
            return ResponseEntity.status(400).body("Table is not reserved or does not exist.");
        }
        return ResponseEntity.ok().body("Table unreserved successfully.");
    }

    // Occupy a table by ID
    @PostMapping("/occupy/{tableId}")
    public ResponseEntity<?> occupyTable(@PathVariable int tableId) {
        boolean occupied = tableStore.occupyTable(tableId);
        if (!occupied) {
            return ResponseEntity.status(400).body("Table is already occupied or does not exist.");
        }
        return ResponseEntity.ok().body("Table occupied successfully.");
    }

    // Vacate a table by ID
    @PostMapping("/vacate/{tableId}")
    public ResponseEntity<?> vacateTable(@PathVariable int tableId) {
        boolean vacated = tableStore.vacateTable(tableId);
        if (!vacated) {
            return ResponseEntity.status(400).body("Table is already vacant or does not exist.");
        }
        return ResponseEntity.ok().body("Table vacated successfully.");
    }
}

