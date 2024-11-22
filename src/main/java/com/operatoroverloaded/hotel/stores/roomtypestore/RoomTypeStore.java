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
    public abstract void addRoomType(RoomType roomType);
    public abstract ArrayList<RoomType> getRoomTypes();
    public abstract RoomType deleteRoomType(String roomTypeId);
    public abstract void save();
    public abstract void updateRoomType(String roomTypeId, RoomType roomType);
    public abstract RoomType findRoomType(String roomTypeId);
    public abstract void load();
}