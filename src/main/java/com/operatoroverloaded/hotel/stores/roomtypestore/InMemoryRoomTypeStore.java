package com.operatoroverloaded.hotel.stores.roomtypestore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.RoomType;

// import com.operatoroverloaded.hotel.models.RoomType;

public class InMemoryRoomTypeStore extends RoomTypeStore {
    private ArrayList<RoomType> roomTypes = new ArrayList<>();
    private static InMemoryRoomTypeStore instance = new InMemoryRoomTypeStore();

    static {
        System.loadLibrary("RoomTypeCPP");
    }

    public static InMemoryRoomTypeStore getInstance() {
        return instance;
    }
    
    public void addRoomType(RoomType roomType) {
        roomTypes.add(roomType);
    }

    public ArrayList<RoomType> getRoomTypes() {
        return new ArrayList<>(roomTypes);
    }
    
    public  RoomType deleteRoomType(String roomTypeId){
        RoomType roomType = null;
        for(RoomType r: roomTypes){
            if(r.getRoomTypeId().equals(roomTypeId)){
                roomType = r;
                roomTypes.remove(r);
                break;
            }
        }
        return roomType;
    }
    
    public  void updateRoomType(String roomTypeId, RoomType roomType){
        for(RoomType r: roomTypes){
            if(r.getRoomTypeId().equals(roomTypeId)){
                r.setRoomTypeName(roomType.getRoomTypeName());
                r.setTariff(roomType.getTariff());
                r.setAmenities(roomType.getAmenities());
                break;
            }
        }
    }
    
    public  RoomType findRoomType(String roomTypeId){
        for(RoomType r: roomTypes){
            System.err.println(roomTypeId+" "+r.getRoomTypeId());
            if(r.getRoomTypeId().equals(roomTypeId)){
                return r;
            }
        }
        return null;
    }
    
    public native void saveToFile();
    public native void loadFromFile();

    @Override
    public void save() {
        saveToFile();
    }

    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public String toString(){
        String result = "";
        for(RoomType type: roomTypes){
            result += type;
        }
        return result;
    }
}