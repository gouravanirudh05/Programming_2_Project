package com.operatoroverloaded.hotel.stores.tablestore;

import java.util.ArrayList;
import java.util.List;

import com.operatoroverloaded.hotel.models.Table;

public class InMemoryTableStore extends TableStore {
    static {
        System.loadLibrary("TableCPP");
    }
    private static final InMemoryTableStore instance = new InMemoryTableStore();
    private ArrayList<Table> tables = new ArrayList<>();

    private InMemoryTableStore() {}

    public static InMemoryTableStore getInstance() {
        return instance;
    }

    @Override
    public void addTable(Table table) {
        tables.add(table);
        System.out.println("Table added successfully: Table Number " + table.getTableNumber());
    }

    @Override
    public ArrayList<Table> getTables() {
        return new ArrayList<>(tables); // Return a copy to prevent external modifications
    }

    @Override
    public Table deleteTable(int tableId) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableId) {
                tables.remove(table);
                System.out.println("Table deleted successfully: Table Number " + tableId);
                return table;
            }
        }
        System.out.println("Table not found: Table Number " + tableId);
        return null; // Return null if the table is not found
    }

    @Override
    public void updateTable(int tableId, Table updatedTable) {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getTableNumber() == tableId) {
                tables.set(i, updatedTable);
                System.out.println("Table updated successfully: Table Number " + tableId);
                return;
            }
        }
        System.out.println("Table not found for update: Table Number " + tableId);
    }

    @Override
    public Table findTable(int tableId) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableId) {
                return table;
            }
        }
        System.out.println("Table not found: Table Number " + tableId);
        return null; // Return null if not found
    }

    // Reserve a table
    public boolean reserveTable(int tableId) {
        Table table = findTable(tableId);
        if (table != null && !table.isReserved()) {
            table.reserveTable();
            return true;
        }
        System.out.println("Table reservation failed: Table Number " + tableId);
        return false;
    }

    // Unreserve a table
    public boolean unreserveTable(int tableId) {
        Table table = findTable(tableId);
        if (table != null && table.isReserved()) {
            table.unreserveTable();
            return true;
        }
        System.out.println("Table unreservation failed: Table Number " + tableId);
        return false;
    }

    // Occupy a table
    public boolean occupyTable(int tableId) {
        Table table = findTable(tableId);
        if (table != null && !table.isOccupied()) {
            table.occupyTable();
            return true;
        }
        System.out.println("Table occupation failed: Table Number " + tableId);
        return false;
    }

    // Vacate a table
    public boolean vacateTable(int tableId) {
        Table table = findTable(tableId);
        if (table != null && table.isOccupied()) {
            table.vacateTable();
            return true;
        }
        System.out.println("Table vacating failed: Table Number " + tableId);
        return false;
    }

    @Override
    public native void saveToFile();

    @Override    
    public native void loadFromFile();
}
