package com.operatoroverloaded.hotel.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.operatoroverloaded.hotel.models.Dish;
import com.operatoroverloaded.hotel.models.Logon;
import com.operatoroverloaded.hotel.models.Room;
import com.operatoroverloaded.hotel.models.RoomType;
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
    private static BillStore billStore = BillStore.getInstance();

    private static DishStore dishStore = InMemoryDishStore.getInstance();
    @SuppressWarnings("unused")
    private static HotelCustomerStore hotelCustomerStore = HotelCustomerStore.getInstance();
    private static LogonStore logonStore = LogonStore.getInstance();
    @SuppressWarnings("unused")
    private static RestaurantCustomerStore restaurantCustomerStore = InMemoryRestaurantCustomerStore.getInstance();
    private static RoomStore roomStore = InMemoryRoomStore.getInstance();
    @SuppressWarnings("unused")
    private static RoomTypeStore roomTypeStore = InMemoryRoomTypeStore.getInstance();
    @SuppressWarnings("unused")
    private static StaffStore staffStore = InMemoryStaffStore.getInstance();
    @SuppressWarnings("unused")
    private static TableStore tableStore = InMemoryTableStore.getInstance();

    static boolean roomsAccess = false, restaurantAccess = false;
    Scanner scanner = new Scanner(System.in);

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
            throw new IllegalArgumentException("Incorrect user access type");

        System.out.println("LogIn Successful!!");
        System.out.println("\n\n" + "-".repeat(100) + "\n\n");
        handleInput();
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void handleInput() {

        while (true) {
            String message = """
                        \nHotel Management System
                        Enter a command:
                        1. Hotel Management
                        2. Restaurant Management
                        3. Staff Management
                        4. Exit
                    """;
            System.out.println(message);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    if (roomsAccess) {
                        hotelManagement();
                    } else {
                        System.out.println("Access Denied.");
                    }
                    break;
                case 2:
                    if (restaurantAccess) {
                        restaurantManagement();
                    } else {
                        System.out.println("Access Denied.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void hotelManagement() {
        while (true) {
            String options = """
                        \nHotel Management
                        1. Add Room to Database
                        2. View all Rooms
                        3. Book Room
                        4. Check Out Room
                        5. Search Customer by name/customerID/email/phone
                        6. View all 
                        7. View all Customers
                        8. Back
                    """;
            System.out.println(options);
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewRooms();
                    break;
                case 3:
                    bookRoom();
                    break;
                case 4:
                    checkOutRoom();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }

        }
    }

    private void addRoom() {
        System.out.println("Enter room ID: ");
        String roomID = scanner.nextLine().trim();

        System.out.println("Enter room capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Enter room type name (e.g., Single, Double, Suite): ");
        String roomTypeName = scanner.nextLine().trim();

        System.out.println("Enter room type tariff: ");
        float tariff = Float.parseFloat(scanner.nextLine().trim());

        System.out.println("Enter amenities (comma-separated): ");
        String[] amenitiesArray = scanner.nextLine().trim().split(",");
        ArrayList<String> amenities = new ArrayList<>(Arrays.asList(amenitiesArray));

        // Create RoomType instance
        RoomType roomType = new RoomType(roomID, roomTypeName, tariff, amenities);

        // Create Room instance
        Room room = new Room(roomID, capacity, roomType.getRoomTypeId(), roomTypeName, null); // Assuming housekeepingLast is set later

        // Store the room
        roomStore.addRoom(room);
        System.out.println("Room added successfully!");
    }

    private void viewRooms() {
        System.out.println("\nListing all rooms:");

        // Retrieve all rooms from the store
        List<Room> rooms = roomStore.getRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        for (Room room : rooms) {
            System.out.println(
                    "Room ID: " + room.getRoomId() +
                            ", Capacity: " + room.getCapacity() +
                            ", Room Type: " + RoomTypeStore.getInstance().findRoomType(room.getRoomTypeId()).getRoomTypeName() +
                            ", Tariff: " + RoomTypeStore.getInstance().findRoomType(room.getRoomTypeId()).getTariff() +
                            ", Amenities: " + String.join(", ", RoomTypeStore.getInstance().findRoomType(room.getRoomTypeId()).getAmenities()));
        }
    }

    private void bookRoom() {
        System.out.println("Enter customer ID: ");
        String customerId = scanner.nextLine().trim();

        System.out.println("Enter room ID to book: ");
        int roomID = Integer.parseInt(scanner.nextLine().trim());

        // Attempt to book the room
        boolean success = false;// roomStore.bookRoom(customerId, roomID);

        if (success) {
            System.out.println("Room booked successfully!");
        } else {
            System.out.println("Room booking failed. Room might already be booked or does not exist.");
        }
    }

    private void checkOutRoom() {
        // System.out.println("Enter room number to check out: ");
        // int roomNumber = scanner.nextInt();

        // Check if the room exists and is currently booked
        Room room = roomStore.findRoom(roomNumber);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        // // Check if the room is already booked or occupied
        // if (roomStore.isRoomBooked(roomNumber)) {
        //     // Perform checkout logic: free the room
        //     boolean success = roomStore.checkOutRoom(roomNumber);
        //     if (success) {
        //         System.out.println("Room " + roomNumber + " has been checked out successfully!");
        //         // Optionally, update housekeeping status or perform any other actions
        //         room.setHousekeepingLast(new DateTime()); // Example, update last housekeeping date
        //     } else {
        //         System.out.println("Unable to check out room. Please try again.");
        //     }
        // } else {
        //     System.out.println("Room " + roomNumber + " is not currently occupied.");
        // }
    }

    // ----------HOTEL CUSTOMER
    // MANAGMENT--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("unused")
    private void hotelCustomerManagement() {

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

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void restaurantManagement() {
        String options = """
                    \nRestaurant Management
                    1. Add Dish to Menu
                    2. Remove Dish from Menu
                    3. Generate Bill
                    4. Back
                """;
        System.out.println(options);
        int choice = Integer.parseInt(scanner.nextLine().trim());
        switch (choice) {
            case 1:
                addDishToMenu();
                break;
            case 2:
                removeDishFromMenu();
                break;
            case 3:
                generateBill();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void addDishToMenu() {
        System.out.println("Enter dish name: ");
        String name = scanner.nextLine();

        System.out.println("Enter dish price: ");
        double price = scanner.nextDouble();
        dishStore.addDish(name, (float) price);
        System.out.println("Dish added successfully.");
    }

    private void removeDishFromMenu() {
        System.out.println("Enter dish ID to remove: ");
        int dishId = Integer.parseInt(scanner.nextLine());

        Dish dish = dishStore.deleteDish(dishId);
        if (dish!=null) {
            System.out.println("Dish removed successfully.");
        } else {
            System.out.println("Dish not found.");
        }
    }

    private void generateBill() {
        // System.out.println("Enter customer ID: ");
        // String customerId = scanner.next();

        // Bill bill = billStore.generateBill(customerId);
        // if (bill != null) {
        //     System.out.println("Bill generated successfully:");
        //     System.out.println("Customer ID: " + bill.getCustomerId());
        //     System.out.println("Total Amount: " + bill.getTotalAmount());
        // } else {
        //     System.out.println("No bill found for the given customer ID.");
        // }
    }
}
