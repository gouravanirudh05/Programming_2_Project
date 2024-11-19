package com.operatoroverloaded.hotel.stores.tablestore;
import java.util.List;

import com.operatoroverloaded.hotel.models.Table;

public abstract class TableStore {
    public static TableStore tableStore = null;
    public static TableStore getInstance(){
        return tableStore;
    }
    public static void setInstance(TableStore tableStore){
        TableStore.tableStore = tableStore;
    }
    abstract void addTable(Table table);
    abstract List<Table> getTables();
    abstract Table deleteTable(int tableId);
    // void saveAll(); // To save to .tmp files for the in-memory version
    abstract void saveToFile();
    abstract void updateTable(int tableId, Table table);
    abstract Table findTable(int tableId);
    abstract void loadFromFile();
}