package com.operatoroverloaded.hotel.stores.room;

import com.hotelmanagement.models.Room;


public interface RoomStore {
    void addRoom(Room room);
    List<Room> getRooms();
    Room getRoomByNumber(int roomNumber);
    void deleteRoom(int roomNumber);
    void saveAll(); // To save to .tmp files for the in-memory version
}