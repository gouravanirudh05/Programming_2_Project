package com.operatoroverloaded.hotel.cli;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.operatoroverloaded.hotel.models.Logon;
import com.operatoroverloaded.hotel.stores.billstore.BillStore;
import com.operatoroverloaded.hotel.stores.dishstore.DishStore;
import com.operatoroverloaded.hotel.stores.dishstore.InMemoryDishStore;
import com.operatoroverloaded.hotel.stores.hotelcustomerstore.HotelCustomerStore;
import com.operatoroverloaded.hotel.stores.logonstore.LogonStore;
import com.operatoroverloaded.hotel.stores.restaurantcustomerstore.InMemoryRestaurantCustomerStore;
import com.operatoroverloaded.hotel.stores.restaurantcustomerstore.RestaurantCustomerStore;
import com.operatoroverloaded.hotel.stores.roomstore.InMemoryRoomStore;
import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;
import com.operatoroverloaded.hotel.stores.roomtypestore.InMemoryRoomTypeStore;
import com.operatoroverloaded.hotel.stores.roomtypestore.RoomTypeStore;
import com.operatoroverloaded.hotel.stores.staffstore.InMemoryStaffStore;
import com.operatoroverloaded.hotel.stores.staffstore.StaffStore;
import com.operatoroverloaded.hotel.stores.tablestore.InMemoryTableStore;
import com.operatoroverloaded.hotel.stores.tablestore.TableStore;

@Component
@Lazy
@Profile("cli") // Only run when the application is NOT a web application
public class ConsoleApplication implements CommandLineRunner {

    private static Scanner scanner = new Scanner(System.in);

    private static BillStore billStore = BillStore.getInstance();
    private static DishStore dishStore = InMemoryDishStore.getInstance();
    private static HotelCustomerStore hotelCustomerStore = HotelCustomerStore.getInstance();
    private static LogonStore logonStore = LogonStore.getInstance();
    private static RestaurantCustomerStore restaurantCustomerStore = InMemoryRestaurantCustomerStore.getInstance();
    private static RoomStore roomStore = InMemoryRoomStore.getInstance();
    private static RoomTypeStore roomTypeStore = InMemoryRoomTypeStore.getInstance();
    private static StaffStore staffStore = InMemoryStaffStore.getInstance();
    private static TableStore tableStore = InMemoryTableStore.getInstance();

    static boolean roomsAccess = false, restaurantAccess = false;

    @Override
    public void run(String... args) {
        System.out.println("************************** CONSOLE APP *********************************");
        System.out.println("\n\nLOGIN PAGE");
        Logon user = null;
        for (int i = 4; i >= 0; i--) {
            System.out.println("Please enter your login details to continue...");
            System.out.println("Email ID: ");
            String email = scanner.next();
            System.out.println("Password: ");
            String psw = scanner.next();
            user = logonStore.tryLogon(email, psw);
            if (user != null)
                break;
            else {
                if (i == 0) {
                    System.out.println("Login attempts exhausted.. Please restart the application.");
                    return;
                }
                System.out.println("Login failed (" + i + " attempts left).. \nPlease try again.");
            }
        }
        if (user.getAccess().equals("Admin")) {
            roomsAccess = true;
            restaurantAccess = true;
        } else if (user.getAccess().equals("Restaurant")) {
            roomsAccess = false;
            restaurantAccess = true;
        } else if (user.getAccess().equals("Room")) {
            roomsAccess = true;
            restaurantAccess = false;
        } else
            // throw Error("INCORRECT USER ACCESS TYPE");

        System.out.println("LogIn Successful!!");
        System.out.println("\n\n" + "-".repeat(100) + "\n\n");
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void handleInput() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nHotel Management System");
            System.out.println("Enter a command: ");
            System.out.println("1. Hotel Management");
            System.out.println("2. Restaurant Management");
            System.out.println("3. Staff Management");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    hotelManagement();
                    break;
                case 2:
                    restaurantManagement();
                    break;
                case 3:
                    // staffManagement();
                    break;
                case 4:
                    restaurantManagement();
                    break;
                case 5:
                    // staffManagement();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void hotelManagement() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nHotel Management");
        System.out.println("1. Add Room to Database");
        System.out.println("2. Book Room");
        System.out.println("3. Check Out Room");
        System.out.println("4. View all (current) Customers");
        System.out.println("5. Check Out Room");

        System.out.println("6. Back");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // createRoom();
                System.out.println("Creating Room...");
                break;
            case 2:
                // bookRoom();
                System.out.println("Booking Room...");
                break;
            case 3:
                // checkOutRoom();
                System.out.println("Checking Out Room...");
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice!");
        }

    }

    // ----------HOTEL CUSTOMER
    // MANAGMENT--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void hotelCustomerManagement() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nCustomer Management");
        System.out.println("1. Create Customer");
        System.out.println("4. Back");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // createCustomer();
                System.out.println("Creating Customer...");
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice!");

        }
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void restaurantCustomerManagement() {
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void restaurantManagement() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nRestaurant Management");
        System.out.println("1. Add Dish to Menu");
        System.out.println("2. Remove Dish from Menu");
        System.out.println("3. Generate Bill");
        System.out.println("4. Back");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // addDishToMenu();
                System.out.println("Adding Dish to Menu...");
                break;
            case 2:
                // removeDishFromMenu();
                System.out.println("Removing Dish from Menu...");
                break;
            case 3:
                // generateBill();
                System.out.println("Generating Bill...");
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// private void addRoom() {
// Scanner scanner = new Scanner(System.in);
// System.out.println("Enter room number: ");
// int roomNumber = scanner.nextInt();
// scanner.nextLine(); // Consume newline

// System.out.println("Enter room type: ");
// String roomType = scanner.nextLine();

// Room room = new Room(roomNumber, roomType);
// objectStore.addRoom(room); // Add to object store
// System.out.println("Room added successfully.");
// }

// private void viewRooms() {
// System.out.println("Listing all rooms:");
// for (Room room : objectStore.getRooms()) {
// System.out.println("Room Number: " + room.getRoomNumber() + ", Room Type: " +
// room.getRoomType());
// }
// }
