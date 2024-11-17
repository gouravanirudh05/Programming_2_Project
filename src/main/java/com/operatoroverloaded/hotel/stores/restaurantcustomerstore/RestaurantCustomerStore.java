
package com.operatoroverloaded.hotel.stores.restaurantcustomerstore;

import java.util.List;

import com.operatoroverloaded.hotel.models.RestaurantCustomer;

public interface RestaurantCustomerStore {
    public static RestaurantCustomerStore restaurantCustomerStore = null;

    public static RestaurantCustomerStore getInstance() {
        return restaurantCustomerStore;
    }

    public static void setInstance(RestaurantCustomerStore restaurantCustomerStore) {
        restaurantCustomerStore = restaurantCustomerStore;
    }

    void loadFromFile();

    void storeToFile();

    int addCustomer(RestaurantCustomer customer);

    void deleteCustomer(int id);

    List<RestaurantCustomer> getCustomers();

    RestaurantCustomer getCustomer(int id);

    int getCustomerId(RestaurantCustomer customer);

    void updateCustomer(int id, RestaurantCustomer customer);
}