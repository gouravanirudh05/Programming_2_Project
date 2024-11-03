package com.operatoroverloaded.hotel.stores.tablestore;

import com.operatoroverloaded.hotel.models.Table;

public interface TableStore {
    public static TableStore tableStore = null;
    public static TableStore getInstance(){
        return tableStore;
    }
    public static void setInstance(TableStore tableStore){
        tableStore = tableStore;
    }
    void addTable(Table table);
    List<Table> getTables();
    Table deleteTable(int tableId);
    // void saveAll(); // To save to .tmp files for the in-memory version
    void saveToFile();
    void updateTable(int tableId, Table table);
    Table findTable(int tableId);
    void loadFromFile();
}