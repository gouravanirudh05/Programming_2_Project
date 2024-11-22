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
    public abstract void addRoom(Room room);
    public abstract List<Room> getRooms();
    public abstract Room deleteRoom(int roomNumber);
    // void saveAll(); // To save to .tmp files for the in-memory version
    public abstract void saveToFile();
    public abstract void updateRoom(int roomId, Room room);
    public abstract Room findRoom(int roomId);
    public abstract void loadFromFile();
}