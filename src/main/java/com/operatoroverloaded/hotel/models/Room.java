package com.operatoroverloaded.hotel.models.room;
import java.io.Serializable;
import com.operatoroverloaded.hotel.models.roomtype;
import com.operatoroverloaded.hotel.models.datetime;

public class Room implements Serializable{
    private int roomID;
    private int capacity;
    private RoomType roomType;
    private String roomTypeName;
    private DateTime housekeepingLast;

    public Room(int roomID, int capacity, RoomType roomType, String roomTypeName, DateTime housekeepingLast) {
        this.roomID = roomID;
        this.capacity = capacity;
        this.roomType = roomType;
        this.roomTypeName = roomTypeName;
        this.housekeepingLast = housekeepingLast;
    }
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    public int getCapacity(){
        return capacity;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    public RoomType getRoomType(){
        return roomType;
    }
    public void setRoomType(RoomType roomType){
        this.roomType = roomType;
    }
    public String getRoomTypeName(){
        return roomTypeName;
    }
}
