package com.operatoroverloaded.hotel.stores.roomtypestore;

import java.util.ArrayList;
import java.util.List;

import com.operatoroverloaded.hotel.models.RoomType;

public class InMemoryRoomTypeStore implements RoomTypeStore {
    private static final InMemoryRoomTypeStore instance = new InMemoryRoomTypeStore();

    public static InMemoryRoomTypeStore getInstance() {
        return instance;
    }
    private final List<RoomType> roomTypes = new ArrayList<>();

    @Override
    public void addRoomType(RoomType roomType) {
        roomTypes.add(roomType);
    }

    @Override
    public List<RoomType> getRoomTypes() {
        return new ArrayList<>(roomTypes);
    }
    @Override
    public native RoomType deleteRoomType(int roomTypeId);
    @Override
    public native void updateRoomType(int roomTypeId, RoomType roomType);
    @Override
    public native RoomType findRoomType(int roomTypeId);
    @Override
    public native void saveToFile();
    @Override    
    public native void loadFromFile();
}