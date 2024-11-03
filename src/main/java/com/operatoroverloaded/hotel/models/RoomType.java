package com.operatoroverloaded.hotel.models;

import java.util.List;
public class RoomType {
    private int roomTypeID;
    private String roomTypeName;
    private float tariff;
    private List<String> amenities;
    // boolean TO_BE_IMPLEMENTED = true;


    public RoomType(int roomTypeID, String roomTypeName, float tariff, List<String> amenities) {
        this.roomTypeID = roomTypeID;
        this.roomTypeName = roomTypeName;
        this.tariff = tariff;
        this.amenities = amenities;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public float getTariff() {
        return tariff;
    }

    public void setTariff(float tariff) {
        this.tariff = tariff;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }
}