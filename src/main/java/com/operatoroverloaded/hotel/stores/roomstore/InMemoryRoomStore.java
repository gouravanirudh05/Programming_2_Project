package com.operatoroverloaded.hotel.stores.roomstore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.Room;

// import org.springframework.context.annotation.Profile;
// import org.springframework.stereotype.Component;
// import com.operatoroverloaded.hotel.models.Room;

// @Component
// @Profile("in-memory")
public class InMemoryRoomStore extends RoomStore {
    private  ArrayList<Room> rooms = new ArrayList<>();
    private static final long serialVersionUId = 1L;
    private static final InMemoryRoomStore instance = new InMemoryRoomStore();

    public static InMemoryRoomStore getInstance() {
        return instance;
    }

    static {
        System.loadLibrary("RoomCPP");
    }

    @Override
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    @Override
    public Room deleteRoom(String roomId){
        Room room = null;
        for(Room r: rooms){
            if(r.getRoomId().equals(roomId)){
                room = r;
                rooms.remove(r);
                break;
            }
        }
        return room;
    }

    @Override
    public void updateRoom(String roomId, Room room){
        for(Room r: rooms){
            if(r.getRoomId().equals(roomId)){
                r.setCapacity(room.getCapacity());
                r.setHousekeepingLast(room.getHousekeepingLast());
                r.setRoomTypeId(room.getRoomTypeId());
                break;
            }
        }
    }

    @Override
    public Room findRoom(String roomId){
        for(Room r: rooms){
            if(r.getRoomId().equals(roomId)){
                return r;
            }
        }
        return null;
    }

    public void save(){
        saveToFile();
    }

    public void load(){
        loadFromFile();
    }
    
    public native void saveToFile();
    public native void loadFromFile();
}
