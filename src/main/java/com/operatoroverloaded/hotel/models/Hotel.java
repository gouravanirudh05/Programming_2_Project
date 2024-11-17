package com.operatoroverloaded.hotel.models;

import java.util.ArrayList;
import java.util.List;
import com.operatoroverloaded.hotel.stores.billstore.*;
import com.operatoroverloaded.hotel.stores.hotelcustomerstore.*;
import com.operatoroverloaded.hotel.stores.restaurantcustomerstore.*;
import com.operatoroverloaded.hotel.stores.logonstore.*;
import com.operatoroverloaded.hotel.stores.roomstore.*;
import com.operatoroverloaded.hotel.stores.roomtypestore.*;
// import com.operatoroverloaded.hotel.models.re

public class Hotel {
    private static final Hotel instance = new Hotel();

    public static Hotel getInstance() {
        return instance;
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
    public static RoomType roomTypeStore = RoomTypeStore.getInstance();
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