package com.operatoroverloaded.hotel.stores.hotelcustomerstore;

import java.util.List;

import com.operatoroverloaded.hotel.models.HotelCustomer;

public abstract class HotelCustomerStore {
    public static HotelCustomerStore hotelCustomerStore = null;

    public static HotelCustomerStore getInstance() {
        return hotelCustomerStore;
    }

    public static void setInstance(HotelCustomerStore hotelCustomerStore) {
        HotelCustomerStore.hotelCustomerStore = hotelCustomerStore;
    }

    abstract void loadFromFile();

    abstract void storeToFile();

    abstract int addCustomer(HotelCustomer customer);

    abstract void deleteCustomer(int id);

    abstract List<HotelCustomer> getCustomers();

    abstract HotelCustomer getCustomer(int id);

    abstract int getCustomerId(HotelCustomer customer);

    abstract void updateCustomer(int id, HotelCustomer customer);
}
