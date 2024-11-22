package com.operatoroverloaded.hotel.stores.dishstore;

import java.util.ArrayList;
import java.util.List;
import com.operatoroverloaded.hotel.models.Dish;

public class InMemoryDishStore extends DishStore {

    private static final InMemoryDishStore instance = new InMemoryDishStore();
    private final List<Dish> dishes = new ArrayList<>();

    private InMemoryDishStore() {}

    public static InMemoryDishStore getInstance() {
        return instance;
    }

    @Override
    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    @Override
    public List<Dish> getDishes() {
        return new ArrayList<>(dishes);
    }

    @Override
    public Dish deleteDish(int dishId) {
        for (Dish dish : dishes) {
            if (dish.getDishID() == dishId) {
                dishes.remove(dish);
                return dish;
            }
        }
        return null;
    }

    @Override
    public void updateDish(int dishId, Dish updatedDish) {
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getDishID() == dishId) {
                dishes.set(i, updatedDish);
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
        return null;
    }


    @Override
    public native void saveToFile();

    @Override
    public native void loadFromFile();
}
