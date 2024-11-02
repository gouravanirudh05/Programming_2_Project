package com.operatoroverloaded.hotel.models;
import java.io.Serializable;

public class Room implements Serializable{
    private int roomID;
    private int capacity;
    private RoomType roomType;
    private String roomTypeName;
    private DateTime housekeepingLast;

    public Room(){
        
    }
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
    public int getId(){return getRoomID();}
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
        this.roomTypeName = roomType.getRoomTypeName();
    }
    public String getRoomTypeName(){
        return roomTypeName;
    }
    public DateTime getHousekeepingLast(){
        return housekeepingLast;
    }
    public void setHousekeepingLast(DateTime housekeepingLast){
        this.housekeepingLast = housekeepingLast;
    }
}
