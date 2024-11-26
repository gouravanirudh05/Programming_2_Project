package com.operatoroverloaded.hotel.models;

public class Table {
    private static int totalTables = 0; 
    private int tableNumber;
    private boolean isReserved;
    private boolean isOccupied; // New field to track if the table is occupied
    private int seatingCapacity;

    public Table(int tableNumber, int seatingCapacity) {
        this.tableNumber = tableNumber;
        this.seatingCapacity = seatingCapacity;
        this.isReserved = false;
        this.isOccupied = false; // Initialize as vacant
        totalTables++;
    }

    // Reserve a table
    public void reserveTable() {
        if (!isReserved) {
            isReserved = true;
            System.out.println("Table " + tableNumber + " has been reserved.");
        } else {
            System.out.println("Table " + tableNumber + " is already reserved.");
        }
    }

    // Unreserve a table
    public void unreserveTable() {
        if (isReserved) {
            isReserved = false;
            System.out.println("Table " + tableNumber + " reservation has been canceled.");
        } else {
            System.out.println("Table " + tableNumber + " is not reserved.");
        }
    }

    // Mark the table as occupied
    public void occupyTable() {
        if (!isOccupied) {
            isOccupied = true;
            System.out.println("Table " + tableNumber + " is now occupied.");
        } else {
            System.out.println("Table " + tableNumber + " is already occupied.");
        }
    }

    // Mark the table as vacant
    public void vacateTable() {
        if (isOccupied) {
            isOccupied = false;
            System.out.println("Table " + tableNumber + " is now vacant.");
        } else {
            System.out.println("Table " + tableNumber + " is already vacant.");
        }
    }

    // Check if the table is reserved
    public boolean isReserved() {
        return isReserved;
    }

    // Check if the table is occupied
    public boolean isOccupied() {
        return isOccupied;
    }

    // Get the seating capacity
    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    // Get the total number of tables
    public static int getTotalTables() {
        return totalTables;
    }

    // Get the table number
    public int getTableNumber() {
        return tableNumber;
    }

    // Display information about the table
    public void displayTableInfo() {
        System.out.println(this.toString());
    }

    // Override toString method
    @Override
    public String toString() {
        return "Table{" +
                "tableNumber=" + tableNumber +
                ", seatingCapacity=" + seatingCapacity +
                ", isReserved=" + (isReserved ? "Yes" : "No") +
                ", isOccupied=" + (isOccupied ? "Yes" : "No") +
                '}';
    }
}
