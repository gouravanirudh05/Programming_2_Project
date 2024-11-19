package com.operatoroverloaded.hotel.stores.roomstore;

import java.util.List;

import com.operatoroverloaded.hotel.models.Room;


public abstract class RoomStore {
    public static RoomStore roomStore = null;
    public static RoomStore getInstance(){
        return roomStore;
    }
    public static void setInstance(RoomStore roomStore){
        RoomStore.roomStore = roomStore;
    }
    abstract void addRoom(Room room);
    abstract List<Room> getRooms();
    abstract Room deleteRoom(int roomNumber);
    // void saveAll(); // To save to .tmp files for the in-memory version
    abstract void saveToFile();
    abstract void updateRoom(int roomId, Room room);
    abstract Room findRoom(int roomId);
    abstract void loadFromFile();
}