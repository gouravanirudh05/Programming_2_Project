
package com.operatoroverloaded.hotel.stores.restaurantcustomerstore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.RestaurantCustomer;

public abstract class RestaurantCustomerStore {
    static {
        System.loadLibrary("RestaurantCustomer");
    }
    public static RestaurantCustomerStore restaurantCustomerStore = null;

    public static RestaurantCustomerStore getInstance() {
        return restaurantCustomerStore;
    }

    public static void setInstance(RestaurantCustomerStore restaurantCustomerStore) {
        RestaurantCustomerStore.restaurantCustomerStore = restaurantCustomerStore;
    }
    public abstract void loadFromFile();
    public abstract void storeToFile();
    public abstract int addCustomer(RestaurantCustomer customer);
    public abstract void deleteCustomer(int id);
    public abstract ArrayList<RestaurantCustomer> getCustomers();
    public abstract RestaurantCustomer getCustomer(int id);
    public abstract int getCustomerId(RestaurantCustomer customer);
    public abstract void updateCustomer(int id, RestaurantCustomer customer);
}
