package com.operatoroverloaded.hotel.stores.dishstore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.Dish;

public class InMemoryDishStore extends DishStore {
    static {
        System.loadLibrary("DishCPP");
    }
    private static final InMemoryDishStore instance = new InMemoryDishStore();
    private ArrayList<Dish> dishes = new ArrayList<>();

    private InMemoryDishStore() {}

    public static InMemoryDishStore getInstance() {
        return instance;
    }

    @Override
    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    @Override
    public void addDish(String name, float price) {
        // Default dishType to MAIN_COURSE, calories and preparationTime to 0, and availability to true
        try {
            dishes.add(new Dish(dishes.size(), name, price, "MAIN_COURSE", 0, 0, true));
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to add dish: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Dish> getDishes() {
        return new ArrayList<>(dishes); // Return a copy of the list to prevent external modifications
    }

    @Override
    public Dish deleteDish(int dishId) {
        for (Dish dish : dishes) {
            if (dish.getDishID() == dishId) {
                dishes.remove(dish);
                return dish;
            }
        }
        return null; // Return null if no matching dish is found
    }

    @Override
    public void updateDish(int dishId, Dish updatedDish) {
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getDishID() == dishId) {
                try {
                    // Validate updatedDish's dishType before updating
                    updatedDish.setDishType(updatedDish.getDishType());
                    dishes.set(i, updatedDish);
                } catch (IllegalArgumentException e) {
                    System.err.println("Failed to update dish: " + e.getMessage());
                }
                return;
            }
        }
    }

    @Override
    public Dish findDish(int dishId) {
        for (Dish dish : dishes) {
            if (dish.getDishID() == dishId) {
                return dish;
            }
        }
        return null; // Return null if no matching dish is found
    }

    @Override
    public native void saveToFile();

    @Override
    public native void loadFromFile();
}
