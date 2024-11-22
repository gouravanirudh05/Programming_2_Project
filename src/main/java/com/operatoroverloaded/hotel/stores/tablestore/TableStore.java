package com.operatoroverloaded.hotel.stores.tablestore;

import java.util.List;
import com.operatoroverloaded.hotel.models.Table;

public abstract class TableStore {

    // Abstract methods for basic operations
    public abstract void addTable(Table table);

    public abstract List<Table> getTables();

    public abstract Table deleteTable(int tableId);

    public abstract void updateTable(int tableId, Table updatedTable);

    public abstract Table findTable(int tableId);

    // Methods for saving/loading data (to be implemented in JNI)
    public abstract void saveToFile();

    public abstract void loadFromFile();
}
