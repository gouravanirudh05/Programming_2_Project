//Preliminary code
public class Table {
    private static int totalTables = 0; 
    private int tableNumber;
    private boolean isReserved;
    private int seatingCapacity;


    public Table(int tableNumber, int seatingCapacity) {
        this.tableNumber = tableNumber;
        this.seatingCapacity = seatingCapacity;
        this.isReserved = false; 
        totalTables++; 
    }


    public void reserveTable() {
        if (!isReserved) {
            isReserved = true;
            System.out.println("Table " + tableNumber + " has been reserved.");
        } else {
            System.out.println("Table " + tableNumber + " is already reserved.");
        }
    }


    public void unreserveTable() {
        if (isReserved) {
            isReserved = false;
            System.out.println("Table " + tableNumber + " reservation has been canceled.");
        } else {
            System.out.println("Table " + tableNumber + " is not reserved.");
        }
    }


    public boolean isReserved() {
        return isReserved;
    }

    
    public int getSeatingCapacity() {
        return seatingCapacity;
    }


    public static int getTotalTables() {
        return totalTables;
    }


    public void displayTableInfo() {
        System.out.println("Table Number: " + tableNumber);
        System.out.println("Seating Capacity: " + seatingCapacity);
        System.out.println("Reserved: " + (isReserved ? "Yes" : "No"));
    }
}
