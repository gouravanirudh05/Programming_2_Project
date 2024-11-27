package com.operatoroverloaded.hotel.models;

import com.operatoroverloaded.hotel.stores.billstore.BillStore;
import com.operatoroverloaded.hotel.stores.hotelcustomerstore.HotelCustomerStore;
import com.operatoroverloaded.hotel.stores.logonstore.LogonStore;
import com.operatoroverloaded.hotel.stores.restaurantcustomerstore.RestaurantCustomerStore;
import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;
import com.operatoroverloaded.hotel.stores.roomtypestore.RoomTypeStore;
// import com.operatoroverloaded.hotel.models.re

public class Hotel {
    private static final Hotel instance = new Hotel();

    public static Hotel getInstance() {
        return instance;
    }

    Hotel() {
        this.name = "Hotel";
        this.description = "Hotel description";
        this.location = "Hotel location";
        this.city = "Hotel city";
        this.state = "Hotel state";
        this.country = "Hotel country";
        this.rating = 5;
        this.phone = "Hotel phone";
        restaurant = new Restaurant();
    }

    private String name;
    private String description;
    private String location;
    private String city;
    private String state;
    private String country;
    private int rating;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public static RoomStore getRoomStore() {
        return roomStore;
    }

    public static void setRoomStore(RoomStore roomStore) {
        Hotel.roomStore = roomStore;
    }

    public static RoomTypeStore getRoomTypeStore() {
        return roomTypeStore;
    }
     public  String getAddress()
     {
        String address=location+","+city+","+state+','+country;
        return address;
     }
    public static void setRoomTypeStore(RoomTypeStore roomTypeStore) {
        Hotel.roomTypeStore = roomTypeStore;
    }

    public static BillStore getBillStore() {
        return billStore;
    }

    public static void setBillStore(BillStore billStore) {
        Hotel.billStore = billStore;
    }

    public static HotelCustomerStore getHotelCustomerStore() {
        return hotelCustomerStore;
    }

    public static void setHotelCustomerStore(HotelCustomerStore hotelCustomerStore) {
        Hotel.hotelCustomerStore = hotelCustomerStore;
    }

    public static RestaurantCustomerStore getRestaurantCustomerStore() {
        return restaurantCustomerStore;
    }

    public static void setRestaurantCustomerStore(RestaurantCustomerStore restaurantCustomerStore) {
        Hotel.restaurantCustomerStore = restaurantCustomerStore;
    }

    public static LogonStore getLogonStore() {
        return logonStore;
    }

    public static void setLogonStore(LogonStore logonStore) {
        Hotel.logonStore = logonStore;
    }

    private String phone;

    public Restaurant restaurant;
    public static RoomStore roomStore = RoomStore.getInstance();
    public static RoomTypeStore roomTypeStore = RoomTypeStore.getInstance();
    public static BillStore billStore = BillStore.getInstance();
    public static HotelCustomerStore hotelCustomerStore = HotelCustomerStore.getInstance();
    public static RestaurantCustomerStore restaurantCustomerStore = RestaurantCustomerStore.getInstance();
    public static LogonStore logonStore = LogonStore.getInstance();

    public Hotel(String name, String description, String location, String city, String state, String country,
            int rating, String phone) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.city = city;
        this.state = state;
        this.country = country;
        this.rating = rating;
        this.phone = phone;
        restaurant = new Restaurant();
    }

    public static void reConfigure() {
        roomStore = RoomStore.getInstance();
        roomTypeStore = RoomTypeStore.getInstance();
        billStore = BillStore.getInstance();
        hotelCustomerStore = HotelCustomerStore.getInstance();
        restaurantCustomerStore = RestaurantCustomerStore.getInstance();
        logonStore = LogonStore.getInstance();
    }
}
