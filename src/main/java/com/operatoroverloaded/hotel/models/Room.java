package com.operatoroverloaded.hotel.models;
import java.io.Serializable;


public class Room implements Serializable{
    private String roomId;
    private int capacity;
    private String roomTypeId;
    private DateTime housekeepingLast;

    public Room(String roomId, int capacity, String roomTypeId, String date, String time){
        this.roomId = roomId;
        this.capacity = capacity;
        this.roomTypeId = roomTypeId;
        this.housekeepingLast = DateTime.fromString(date.replace('/', '-'), time);
        
    }
    public Room(String roomId, int capacity, String roomType, DateTime housekeepingLast) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.roomTypeId = roomType;
        this.housekeepingLast = housekeepingLast;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public int getCapacity(){
        return capacity;
    }
    
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    
    public String getRoomTypeId(){
        return roomTypeId;
    }
    
    public void setRoomTypeId(String roomType){
        this.roomTypeId = roomType;
    }
    
    public DateTime getHousekeepingLast(){
        return housekeepingLast;
    }
    
    public void setHousekeepingLast(DateTime housekeepingLast){
        this.housekeepingLast = housekeepingLast;
    }
}
