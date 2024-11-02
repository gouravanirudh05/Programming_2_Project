package com.operatoroverloaded.hotel.stores.restaurantcustomerstore;
import java.util.ArrayList;
import java.util.List;

import com.operatoroverloaded.hotel.models.RestaurantCustomer;

public class InMemoryRestaurantCustomerStore implements RestaurantCustomerStore {

// -----------------------------------------------------------------Attributes-------------------------------------------------------------------------------------------------------------

    private List<RestaurantCustomer> restaurantCustomers;
    private static int id = 0;
    private static boolean objectExists = false; // i want only one database for each class, so this keeps a track of
                                                 // whether a databse already exists or no

// -----------------------------------------------------------------Constructor-------------------------------------------------------------------------------------------------------------

    InMemoryRestaurantCustomerStore() { // constructor (also calls the loadFromFile function)
        if (InMemoryRestaurantCustomerStore.objectExists == true) {
            throw new IllegalStateException("Restaurant customer database already exists");
        }
        restaurantCustomers = new ArrayList<>();
        id = 0;
        objectExists = true;
        loadFromFile();
    }

// -----------------------------------------------------------------Add Operations-------------------------------------------------------------------------------------------------------------

    public int addCustomer(RestaurantCustomer customer) { // returns the id assigned to the customer by the database
        for (RestaurantCustomer x : restaurantCustomers)
        if (customer.getPhone() == x.getPhone())
        return x.getCustomerId(); // assuming that every customer has a unique phone number
        customer.setCustomerId(id);
        id++;
        restaurantCustomers.add(customer);
        return id - 1;
    }

// -----------------------------------------------------------------Delete Operations-------------------------------------------------------------------------------------------------------------

    public void deleteCustomer(int id) {
        for (int i = 0; i < restaurantCustomers.size(); i++)
        if (restaurantCustomers.get(i).getCustomerId() == id)
        restaurantCustomers.remove(i);
    }

// -----------------------------------------------------------------Read Operations-------------------------------------------------------------------------------------------------------------

    public List<RestaurantCustomer> getCustomers() {
        return this.restaurantCustomers;
    }

    public int getCustomerId(RestaurantCustomer customer){
        for (int i =0; i<restaurantCustomers.size(); i++) if (restaurantCustomers.get(i).getPhone() == customer.getPhone()) return restaurantCustomers.get(i).getCustomerId();
        return -1;
    }

    public RestaurantCustomer getCustomer(int id){
        for (int i =0; i<restaurantCustomers.size(); i++) if (restaurantCustomers.get(i).getCustomerId() == id) return restaurantCustomers.get(i);
        return null;
    }

// -----------------------------------------------------------------Update Operations-------------------------------------------------------------------------------------------------------------

    public void updateCustomer(int id, RestaurantCustomer customer) {
        customer.setCustomerId(id);
        for (int i = 0; i < restaurantCustomers.size(); i++)
        if (restaurantCustomers.get(i).getCustomerId() == id)
        restaurantCustomers.set(i, customer);
    }

// -----------------------------------------------------------------Incomplete methods-------------------------------------------------------------------------------------------------------------

    public void loadFromFile() {
        // from the file load the hotelCustomers and the id
        // modify the id and hotel customers
    }

    public void storeToFile() {
        // store the id in first line and hotel customers afterwards
    }
}
