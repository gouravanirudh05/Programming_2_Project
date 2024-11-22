package com.operatoroverloaded.hotel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.operatoroverloaded.hotel.models.Dish;
import com.operatoroverloaded.hotel.models.Dish.DishType;
import com.operatoroverloaded.hotel.stores.dishstore.InMemoryDishStore;

import java.util.List;

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
        List<Dish> dishes = dishStore.getDishes();
        return ResponseEntity.ok(dishes);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDish(@RequestBody JsonNode json) {
        int dishID = json.get("dishID").asInt();
        String name = json.get("name").asText();
        float price = (float) json.get("price").asDouble();
        DishType dishType = DishType.valueOf(json.get("dishType").asText().toUpperCase());
        int calories = json.get("calories").asInt();
        int preparationTime = json.get("preparationTime").asInt();
        boolean isAvailable = json.get("isAvailable").asBoolean();

        Dish newDish = new Dish(dishID, name, price, dishType, calories, preparationTime, isAvailable);
        dishStore.addDish(newDish);
        return ResponseEntity.ok().body("Dish added successfully");
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

        String name = json.get("name").asText(existingDish.getName());
        float price = (float) json.get("price").asDouble(existingDish.getPrice());
        DishType dishType = DishType.valueOf(json.get("dishType").asText().toUpperCase(existingDish.getDishType().toString()));
        int calories = json.get("calories").asInt(existingDish.getCalories());
        int preparationTime = json.get("preparationTime").asInt(existingDish.getPreparationTime());
        boolean isAvailable = json.get("isAvailable").asBoolean(existingDish.isAvailable());

        Dish updatedDish = new Dish(dishId, name, price, dishType, calories, preparationTime, isAvailable);
        dishStore.updateDish(dishId, updatedDish);
        return ResponseEntity.ok().body("Dish updated successfully");
    }
}

