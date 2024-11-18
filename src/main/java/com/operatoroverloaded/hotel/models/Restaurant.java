package com.operatoroverloaded.hotel.models;

import com.operatoroverloaded.hotel.stores.dishstore.DishStore;
import com.operatoroverloaded.hotel.stores.tablestore.TableStore;
public class Restaurant {
    private DishStore dishStore = DishStore.getInstance();
    private TableStore tableStore = TableStore.getInstance();
}