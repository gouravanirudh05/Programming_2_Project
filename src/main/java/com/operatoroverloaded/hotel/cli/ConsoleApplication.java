package com.operatoroverloaded.hotel.cli;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.stereotype.Component;

import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;

@Component
@ConditionalOnNotWebApplication // Only run when the application is NOT a web application
public class ConsoleApplication implements CommandLineRunner {
    private Hotel hotel = Hotel.getInstance();
    @Override
    public void run(String... args) {
        System.out.println("************************** CONSOLE APP *********************************");
        handleInput();
    }
    private void handleInput() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Enter a command: ");
            String input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "add room":
                // Eg function. hotel.roomStore.addRoom(new Room(1, "roomType"));
                    break;
                case "view rooms":
                // Eg function. hotel.roomStore.getRooms();
                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("Unknown command, please try again.");
            }
        }
    }

    // private void addRoom() {
    //     Scanner scanner = new Scanner(System.in);
    //     System.out.println("Enter room number: ");
    //     int roomNumber = scanner.nextInt();
    //     scanner.nextLine();  // Consume newline

    //     System.out.println("Enter room type: ");
    //     String roomType = scanner.nextLine();

    //     Room room = new Room(roomNumber, roomType);
    //     objectStore.addRoom(room); // Add to object store
    //     System.out.println("Room added successfully.");
    // }

    // private void viewRooms() {
    //     System.out.println("Listing all rooms:");
    //     for (Room room : objectStore.getRooms()) {
    //         System.out.println("Room Number: " + room.getRoomNumber() + ", Room Type: " + room.getRoomType());
    //     }
    // }
}
