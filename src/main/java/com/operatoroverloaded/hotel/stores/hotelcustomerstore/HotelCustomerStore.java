package com.operatoroverloaded.hotel.stores.hotelcustomerstore;
import java.util.List;

import com.operatoroverloaded.hotel.models.HotelCustomer;

public interface HotelCustomerStore {
    void loadFromFile();
    void storeToFile();
    int addCustomer(HotelCustomer customer);
    void deleteCustomer(int id);
    List<HotelCustomer> getCustomers();
    HotelCustomer getCustomer(int id);
    int getCustomerId(HotelCustomer customer);
    void updateCustomer(int id, HotelCustomer customer);
} 
