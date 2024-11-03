//        !!!More changes need to be done!!!

package com.operatoroverloaded.hotel.stores.tablestore;

import java.util.ArrayList;
import java.util.List;

import com.operatoroverloaded.hotel.models.Table;

public class InMemoryTableStore implements TableStore {
    private static final InMemoryTableStore instance = new InMemoryTableStore();
    private final List<Table> tables = new ArrayList<>();

    private InMemoryTableStore() {}

    public static InMemoryTableStore getInstance() {
        return instance;
    }

    @Override
    public void addTable(Table table) {
        tables.add(table);
    }

    @Override
    public List<Table> getTables() {
        return new ArrayList<>(tables);
    }

    @Override
    public Table deleteTable(int tableId) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableId) {
                tables.remove(table);
                return table;
            }
        }
        return null; // Return null if not found
    }

    @Override
    public void updateTable(int tableId, Table updatedTable) {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getTableNumber() == tableId) {
                tables.set(i, updatedTable);
                return;
            }
        }
    }

    @Override
    public Table findTable(int tableId) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableId) {
                return table;
            }
        }
        return null; // Return null if not found
    }

    // Native methods
    @Override
    public native void saveToFile();

    @Override    
    public native void loadFromFile();
}
