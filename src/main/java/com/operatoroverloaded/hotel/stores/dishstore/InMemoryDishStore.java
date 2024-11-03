package com.operatoroverloaded.hotel.stores.dishstore;

import java.util.ArrayList;
import java.util.List;

import com.operatoroverloaded.hotel.models.Dish;

public class InMemoryDishStore implements DishStore {
    private static final InMemoryDishStore instance = new InMemoryDishStore();

    public static InMemoryDishStore getInstance() {
        return instance;
    }
    private final List<Dish> dishes = new ArrayList<>();

    @Override
    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    @Override
    public List<Dish> getDishes() {
        return new ArrayList<>(dishes);
    }
    @Override
    public native Dish deleteDish(int dishId);
    @Override
    public native void updateDish(int dishId, Dish dish);
    @Override
    public native Dish findDish(int dishId);
    @Override
    public native void saveToFile();
    @Override    
    public native void loadFromFile();
}