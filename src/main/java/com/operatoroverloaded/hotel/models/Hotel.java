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
    Hotel(){
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
    private String phone;

    public Restaurant restaurant;
    public static RoomStore roomStore = RoomStore.getInstance();
    public static RoomTypeStore roomTypeStore = RoomTypeStore.getInstance();
    public static BillStore billStore = BillStore.getInstance();
    public static HotelCustomerStore hotelCustomerStore = HotelCustomerStore.getInstance();
    public static RestaurantCustomerStore restaurantCustomerStore = RestaurantCustomerStore.getInstance();
    public static LogonStore logonStore = LogonStore.getInstance();

    public Hotel(String name, String description, String location, String city, String state, String country, int rating, String phone) {
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
    public static void reConfigure(){
        roomStore = RoomStore.getInstance();
        roomTypeStore = RoomTypeStore.getInstance();
        billStore = BillStore.getInstance();
        hotelCustomerStore = HotelCustomerStore.getInstance();
        restaurantCustomerStore = RestaurantCustomerStore.getInstance();
        logonStore = LogonStore.getInstance();
    }
}