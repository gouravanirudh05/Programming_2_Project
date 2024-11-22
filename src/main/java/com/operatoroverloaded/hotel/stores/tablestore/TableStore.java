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
    public abstract void addTable(Table table);
    public abstract List<Table> getTables();
    public abstract Table deleteTable(int tableId);
    // void saveAll(); // To save to .tmp files for the in-memory version
    public abstract void saveToFile();
    public abstract void updateTable(int tableId, Table table);
    public abstract Table findTable(int tableId);
    public abstract void loadFromFile();
}