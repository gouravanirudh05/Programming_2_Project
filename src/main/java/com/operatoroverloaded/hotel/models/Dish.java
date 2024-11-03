//Basic structural code,changes can be made according to future requirements
public class Dish {
    public enum DishType {
        APPETIZER, MAIN_COURSE, DESSERT, BEVERAGE
    }

    private int dishID;
    private String name;
    private float price;
    private DishType dishType;
    private int calories;
    private int preparationTime;
    private boolean isAvailable;

    public Dish(int dishID, String name, float price, DishType dishType, int calories, int preparationTime, boolean isAvailable) {
        this.dishID = dishID;
        this.name = name;
        this.price = price;
        this.dishType = dishType;
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

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
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

