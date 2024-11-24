package com.operatoroverloaded.hotel.models;

// Basic structural code, changes can be made according to future requirements
public class Dish {

    private int dishID;
    private String name;
    private float price;
    private String dishType; // Changed from enum to String
    private int calories;
    private int preparationTime;
    private boolean isAvailable;

    public Dish(int dishID, String name, float price, String dishType, int calories, int preparationTime, boolean isAvailable) {
        this.dishID = dishID;
        this.name = name;
        this.price = price;
        setDishType(dishType); // Validate and set dishType here
        this.calories = calories;
        this.preparationTime = preparationTime;
        this.isAvailable = isAvailable;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDishType() {
        return dishType;
    }

    // Validate and set the dishType
    public void setDishType(String dishType) {
        if (dishType.equalsIgnoreCase("APPETIZER") || 
            dishType.equalsIgnoreCase("MAIN_COURSE") || 
            dishType.equalsIgnoreCase("DESSERT") || 
            dishType.equalsIgnoreCase("BEVERAGE")) {
            this.dishType = dishType.toUpperCase(); // Normalize to uppercase
        } else {
            throw new IllegalArgumentException("Invalid dish type. Must be one of: APPETIZER, MAIN_COURSE, DESSERT, BEVERAGE.");
        }
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void display() {
        System.out.println("Dish ID: " + dishID);
        System.out.println("Name: " + name);
        System.out.println("Price: rupees" + price);
        System.out.println("Type: " + dishType);
        System.out.println("Calories: " + calories + " kcal");
        System.out.println("Preparation Time: " + preparationTime + " mins");
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
    }
}
