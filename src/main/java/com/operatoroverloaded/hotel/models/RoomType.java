package com.operatoroverloaded.hotel.models;

import java.util.ArrayList;

public class RoomType {
    private String roomTypeId;
    private String roomTypeName;
    private float tariff;
    private ArrayList<String> amenities;

    public RoomType(String roomTypeId, String roomTypeName, float tariff, ArrayList<String> amenities) {
        this.roomTypeId = roomTypeId;
        this.roomTypeName = roomTypeName;
        this.tariff = tariff;
        this.amenities = amenities;
    }

    public RoomType(String roomTypeId, String roomTypeName, float tariff) {
        this.roomTypeId = roomTypeId;
        this.roomTypeName = roomTypeName;
        this.tariff = tariff;
        this.amenities = new ArrayList<>();
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public float getTariff() {
        return tariff;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public void setTariff(float tariff) {
        this.tariff = tariff;
    }

    public void setAmenities(ArrayList<String> amenities) {
        this.amenities = amenities;
    }

    public void addAmenity(String amenity) {
        amenities.add(amenity);
    }

    @Override
    public String toString(){
        String result = "";
        result += roomTypeId + ' ' + roomTypeName + " " + tariff;
        for(String amenity: amenities){
            result += " " + amenity;
        }
        return result + "\n";
    }
}