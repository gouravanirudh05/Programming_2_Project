package com.operatoroverloaded.hotel.controller;

import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.operatoroverloaded.hotel.models.Dish;
import com.operatoroverloaded.hotel.stores.dishstore.InMemoryDishStore;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    private final InMemoryDishStore dishStore;

    public DishController() {
        this.dishStore = InMemoryDishStore.getInstance();
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<?> getDish(@PathVariable int dishId) {
        Dish dish = dishStore.findDish(dishId);
        if (dish == null) {
            return ResponseEntity.status(404).body("Dish not found");
        }
        return ResponseEntity.ok(dish);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDishes() {
       ArrayList<Dish> dishes = dishStore.getDishes();
        return ResponseEntity.ok(dishes);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDish(@RequestBody JsonNode json) {
        try {
            int dishID = json.get("dishID").asInt();
            String name = json.get("name").asText();
            float price = (float) json.get("price").asDouble();
            String dishType = json.get("dishType").asText().toUpperCase();
            int calories = json.get("calories").asInt();
            int preparationTime = json.get("preparationTime").asInt();
            boolean isAvailable = json.get("isAvailable").asBoolean();

            Dish newDish = new Dish(dishStore.getDishes().size() + 1, name, price, dishType, calories, preparationTime, isAvailable);
            dishStore.addDish(newDish);

            return ResponseEntity.ok().body("Dish added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Failed to add dish: " + e.getMessage());
        }
    }

    @PostMapping("/remove/{dishId}")
    public ResponseEntity<?> removeDish(@PathVariable int dishId) {
        Dish removedDish = dishStore.deleteDish(dishId);
        if (removedDish == null) {
            return ResponseEntity.status(404).body("Dish not found");
        }
        return ResponseEntity.ok().body("Dish removed successfully");
    }

    @PostMapping("/update/{dishId}")
    public ResponseEntity<?> updateDish(@PathVariable int dishId, @RequestBody JsonNode json) {
        Dish existingDish = dishStore.findDish(dishId);
        if (existingDish == null) {
            return ResponseEntity.status(404).body("Dish not found");
        }

        try {
            String name = json.has("name") ? json.get("name").asText() : existingDish.getName();
            float price = json.has("price") ? (float) json.get("price").asDouble() : existingDish.getPrice();
            String dishType = json.has("dishType") ? json.get("dishType").asText().toUpperCase() : existingDish.getDishType();
            int calories = json.has("calories") ? json.get("calories").asInt() : existingDish.getCalories();
            int preparationTime = json.has("preparationTime") ? json.get("preparationTime").asInt() : existingDish.getPreparationTime();
            boolean isAvailable = json.has("isAvailable") ? json.get("isAvailable").asBoolean() : existingDish.isAvailable();

            Dish updatedDish = new Dish(dishId, name, price, dishType, calories, preparationTime, isAvailable);
            dishStore.updateDish(dishId, updatedDish);

            return ResponseEntity.ok().body("Dish updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Failed to update dish: " + e.getMessage());
        }
    }
}
