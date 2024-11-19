
package com.operatoroverloaded.hotel.stores.restaurantcustomerstore;

import java.util.List;

import com.operatoroverloaded.hotel.models.RestaurantCustomer;

public abstract class RestaurantCustomerStore {
    public static RestaurantCustomerStore restaurantCustomerStore = null;
    public static RestaurantCustomerStore getInstance(){
        return restaurantCustomerStore;
    }
    public static void setInstance(RestaurantCustomerStore restaurantCustomerStore){
        RestaurantCustomerStore.restaurantCustomerStore = restaurantCustomerStore;
    }
    abstract void loadFromFile();
    abstract void storeToFile();
    abstract int addCustomer(RestaurantCustomer customer);
    abstract void deleteCustomer(int id);
    abstract List<RestaurantCustomer> getCustomers();
    abstract RestaurantCustomer getCustomer(int id);
    abstract int getCustomerId(RestaurantCustomer customer);
    abstract void updateCustomer(int id, RestaurantCustomer customer);
}
