package com.operatoroverloaded.hotel.models;

public class Dish {
    private int dishID;
    private String name;
    private float price;
    private String dishType;
    private int calories;
    private int preparationTime;
    private boolean isAvailable;

    public Dish(int dishID, String name, float price, String dishType, int calories, int preparationTime, boolean isAvailable) {
        this.dishID = dishID;
        this.name = name;
        this.price = price;
        setDishType(dishType);
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

    public void setDishType(String dishType) {
        if (dishType.equalsIgnoreCase("APPETIZER") || 
            dishType.equalsIgnoreCase("MAIN_COURSE") || 
            dishType.equalsIgnoreCase("DESSERT") || 
            dishType.equalsIgnoreCase("BEVERAGE")) {
            this.dishType = dishType.toUpperCase();
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
        System.out.println("Price: rupees " + price);
        System.out.println("Type: " + dishType);
        System.out.println("Calories: " + calories + " kcal");
        System.out.println("Preparation Time: " + preparationTime + " mins");
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
    }

    // Example method using .equals() for comparisons
    public boolean isSameDish(Dish otherDish) {
        if (otherDish == null) {
            return false;
        }

        return this.dishID == otherDish.dishID && // Primitive type comparison
               this.name.equals(otherDish.name) && // String comparison
               Float.compare(this.price, otherDish.price) == 0 && // Float comparison
               this.dishType.equals(otherDish.dishType) && // String comparison
               this.calories == otherDish.calories && // Primitive type comparison
               this.preparationTime == otherDish.preparationTime && // Primitive type comparison
               this.isAvailable == otherDish.isAvailable; // Primitive type comparison
    }
}
