package com.operatoroverloaded.hotel.stores.roomtypestore;

import com.operatoroverloaded.hotel.models.RoomType;
// import com.operatoroverloaded.hotel.stores.Store;

import java.util.List;

public interface RoomTypeStore {
    void addRoomType(RoomType roomType);
    List<RoomType> getRoomTypes();
    RoomType deleteRoomType(int roomTypeId);
    // void saveAll(); // To save to .tmp files for the in-memory version
    void saveToFile();
    void updateRoomType(int roomTypeId, RoomType roomType);
    RoomType findRoomType(int roomTypeId);
    void loadFromFile();
}