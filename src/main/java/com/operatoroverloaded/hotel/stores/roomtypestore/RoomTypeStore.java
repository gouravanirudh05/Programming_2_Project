package com.operatoroverloaded.hotel.stores.roomtypestore;

import com.operatoroverloaded.hotel.models.RoomType;
// import com.operatoroverloaded.hotel.stores.Store;

import java.util.List;

public abstract class RoomTypeStore {
    public static RoomTypeStore roomTypeStore = null;
    public static RoomTypeStore getInstance(){
        return roomTypeStore;
    }
    public static void setInstance(RoomTypeStore roomTypeStore){
        RoomTypeStore.roomTypeStore = roomTypeStore;
    }
    abstract void addRoomType(RoomType roomType);
    abstract List<RoomType> getRoomTypes();
    abstract RoomType deleteRoomType(int roomTypeId);
    // void saveAll(); // To save to .tmp files for the in-memory version
    abstract void saveToFile();
    abstract void updateRoomType(int roomTypeId, RoomType roomType);
    abstract RoomType findRoomType(int roomTypeId);
    abstract void loadFromFile();
}