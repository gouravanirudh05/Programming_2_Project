package com.operatoroverloaded.hotel.stores.dishstore;

import java.util.List;

import com.operatoroverloaded.hotel.models.Dish;

public abstract class DishStore {
    public static DishStore dishStore = null;
    public static DishStore getInstance(){
        return dishStore;
    }
    public static void setInstance(DishStore dishStore){
        DishStore.dishStore = dishStore;
    }
    abstract void addDish(Dish dish);
    abstract List<Dish> getDishes();
    abstract Dish deleteDish(int dishId);
    // void saveAll(); // To save to .tmp files for the in-memory version
    abstract void saveToFile();
    abstract void updateDish(int dishId, Dish dish);
    abstract Dish findDish(int dishId);
    abstract void loadFromFile();
}