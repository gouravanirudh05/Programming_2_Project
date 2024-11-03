package com.operatoroverloaded.hotel.stores.dishstore;

import java.util.List;

import com.operatoroverloaded.hotel.models.Dish;

public interface DishStore {
    public static DishStore dishStore = null;
    public static DishStore getInstance(){
        return dishStore;
    }
    public static void setInstance(DishStore dishStore){
        dishStore = dishStore;
    }
    void addDish(Dish dish);
    List<Dish> getDishes();
    Dish deleteDish(int dishId);
    // void saveAll(); // To save to .tmp files for the in-memory version
    void saveToFile();
    void updateDish(int dishId, Dish dish);
    Dish findDish(int dishId);
    void loadFromFile();
}