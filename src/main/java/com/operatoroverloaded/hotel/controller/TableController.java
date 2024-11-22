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

    @GetMapping("/{tableId}")
    public ResponseEntity<?> getTable(@PathVariable int tableId) {
        Table table = tableStore.findTable(tableId);
        if (table == null) {
            return ResponseEntity.status(404).body("Table not found");
        }
        return ResponseEntity.ok(table);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTables() {
        List<Table> tables = tableStore.getTables();
        return ResponseEntity.ok(tables);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTable(@RequestBody JsonNode json) {
        int tableNumber = json.get("tableNumber").asInt();
        int seatingCapacity = json.get("seatingCapacity").asInt();

        Table newTable = new Table(tableNumber, seatingCapacity);
        tableStore.addTable(newTable);
        return ResponseEntity.ok().body("Table added successfully");
    }

    @PostMapping("/remove/{tableId}")
    public ResponseEntity<?> removeTable(@PathVariable int tableId) {
        Table removedTable = tableStore.deleteTable(tableId);
        if (removedTable == null) {
            return ResponseEntity.status(404).body("Table not found");
        }
        return ResponseEntity.ok().body("Table removed successfully");
    }

    @PostMapping("/update/{tableId}")
    public ResponseEntity<?> updateTable(@PathVariable int tableId, @RequestBody JsonNode json) {
        Table existingTable = tableStore.findTable(tableId);
        if (existingTable == null) {
            return ResponseEntity.status(404).body("Table not found");
        }

        int seatingCapacity = json.get("seatingCapacity").asInt(existingTable.getSeatingCapacity());
        boolean isReserved = json.get("isReserved").asBoolean(existingTable.isReserved());

        Table updatedTable = new Table(tableId, seatingCapacity);
        if (isReserved) {
            updatedTable.reserveTable();
        }
        tableStore.updateTable(tableId, updatedTable);
        return ResponseEntity.ok().body("Table updated successfully");
    }

    @PostMapping("/reserve/{tableId}")
    public ResponseEntity<?> reserveTable(@PathVariable int tableId) {
        Table table = tableStore.findTable(tableId);
        if (table == null) {
            return ResponseEntity.status(404).body("Table not found");
        }

        if (table.isReserved()) {
            return ResponseEntity.status(400).body("Table is already reserved.");
        }

        table.reserveTable();
        return ResponseEntity.ok().body("Table reserved successfully.");
    }

    @PostMapping("/unreserve/{tableId}")
    public ResponseEntity<?> unreserveTable(@PathVariable int tableId) {
        Table table = tableStore.findTable(tableId);
        if (table == null) {
            return ResponseEntity.status(404).body("Table not found");
        }

        if (!table.isReserved()) {
            return ResponseEntity.status(400).body("Table is not reserved.");
        }

        table.unreserveTable();
        return ResponseEntity.ok().body("Table unreserved successfully.");
    }
}
