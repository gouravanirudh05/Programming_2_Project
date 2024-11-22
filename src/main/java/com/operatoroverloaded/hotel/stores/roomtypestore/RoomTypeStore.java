package com.operatoroverloaded.hotel.stores.roomtypestore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.RoomType;

public abstract class RoomTypeStore {
    public static RoomTypeStore roomTypeStore = null;
    public static RoomTypeStore getInstance(){
        return roomTypeStore;
    }
    public static void setInstance(RoomTypeStore roomTypeStore){
        RoomTypeStore.roomTypeStore = roomTypeStore;
    }
    abstract void addRoomType(RoomType roomType);
    abstract ArrayList<RoomType> getRoomTypes();
    abstract RoomType deleteRoomType(String roomTypeId);
    abstract void save();
    abstract void updateRoomType(String roomTypeId, RoomType roomType);
    abstract RoomType findRoomType(String roomTypeId);
    abstract void load();
}