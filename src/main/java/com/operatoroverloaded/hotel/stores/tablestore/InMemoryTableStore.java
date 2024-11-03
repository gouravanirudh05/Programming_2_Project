package com.operatoroverloaded.hotel.stores.tablestore;

import java.util.ArrayList;
import java.util.List;

import com.operatoroverloaded.hotel.models.Table;

public class InMemoryTableStore implements TableStore {
    private static final InMemoryTableStore instance = new InMemoryTableStore();

    public static InMemoryTableStore getInstance() {
        return instance;
    }
    private final List<Table> tables = new ArrayList<>();

    @Override
    public void addTable(Table table) {
        tables.add(table);
    }

    @Override
    public List<Table> getTables() {
        return new ArrayList<>(tables);
    }

    @Override
    public native Table deleteTable(int tableId);
    @Override
    public native void updateTable(int tableId, Table table);
    @Override
    public native Table findTable(int tableId);
    @Override
    public native void saveToFile();
    @Override    
    public native void loadFromFile();
}           