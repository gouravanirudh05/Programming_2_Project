package com.operatoroverloaded.hotel.stores.roomstore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.operatoroverloaded.hotel.models.Room;

@Component
@Profile("in-memory")
public class InMemoryRoomStore implements RoomStore, Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Room> rooms = new ArrayList<>();

    @Override
    public void addRoom(Room room) {
        rooms.add(room);
    }
    @Override
    public List<Room> getRooms() {
        return rooms;
    }
    @Override
    public native Room deleteRoom(int roomId);
    @Override
    public native void updateRoom(int roomId, Room room);
    @Override
    public native Room findRoom(int roomId);
    @Override
    public native void saveToFile();
    @Override
    public native void loadFromFile();
}
