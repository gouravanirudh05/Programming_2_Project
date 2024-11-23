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

    // Abstract methods for basic operations
    public abstract void addDish(Dish dish);

    public abstract void addDish(String name, float price);

    public abstract List<Dish> getDishes();

    public abstract Dish deleteDish(int dishId);

    public abstract void updateDish(int dishId, Dish updatedDish);

    public abstract Dish findDish(int dishId);

    // Methods for saving/loading data (to be implemented in JNI)
    public abstract void saveToFile();

    public abstract void loadFromFile();
}
