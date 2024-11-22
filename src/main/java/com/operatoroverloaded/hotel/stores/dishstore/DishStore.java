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
    public abstract void addDish(Dish dish);
    public abstract List<Dish> getDishes();
    public abstract Dish deleteDish(int dishId);
    // void saveAll(); // To save to .tmp files for the in-memory version
    public abstract void saveToFile();
    public abstract void updateDish(int dishId, Dish dish);
    public abstract Dish findDish(int dishId);
    public abstract void loadFromFile();
    public abstract void addDish(String name, float price);
}