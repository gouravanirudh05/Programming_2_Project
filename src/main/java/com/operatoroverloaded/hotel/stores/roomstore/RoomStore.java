package com.operatoroverloaded.hotel.stores.roomstore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.Room;


public  abstract class RoomStore {
    public static RoomStore roomStore = null;
    public static RoomStore getInstance(){
        return roomStore;
    }
    public static void setInstance(RoomStore roomStore){
        RoomStore.roomStore = roomStore;
    }
    public abstract void addRoom(Room room);
    public abstract ArrayList<Room> getRooms();
    public abstract Room deleteRoom(String roomNumber);
    public abstract void save();
    public abstract void updateRoom(String roomId, Room room);
    public abstract Room findRoom(String roomId);
    public abstract void load();
}