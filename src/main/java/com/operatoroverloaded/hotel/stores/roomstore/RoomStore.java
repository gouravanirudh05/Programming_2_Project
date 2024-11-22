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
    abstract void addRoom(Room room);
    abstract ArrayList<Room> getRooms();
    abstract Room deleteRoom(String roomNumber);
    abstract void save();
    abstract void updateRoom(String roomId, Room room);
    abstract Room findRoom(String roomId);
    abstract void load();
}