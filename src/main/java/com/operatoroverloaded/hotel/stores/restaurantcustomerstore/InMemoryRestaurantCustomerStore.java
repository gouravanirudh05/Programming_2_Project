    package com.operatoroverloaded.hotel.stores.restaurantcustomerstore;
import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.RestaurantCustomer;

public class InMemoryRestaurantCustomerStore extends RestaurantCustomerStore {
    private static final InMemoryRestaurantCustomerStore instance = new InMemoryRestaurantCustomerStore();

    public static InMemoryRestaurantCustomerStore getInstance() {
        return instance;
    }
// -----------------------------------------------------------------Attributes-------------------------------------------------------------------------------------------------------------

    private ArrayList<RestaurantCustomer> restaurantCustomers;
    private static boolean objectExists = false; // i want only one database for each class, so this keeps a track of
                                                 // whether a databse already exists or no

// -----------------------------------------------------------------Constructor-------------------------------------------------------------------------------------------------------------

    public InMemoryRestaurantCustomerStore() { // constructor (also calls the loadFromFile function)
        if (InMemoryRestaurantCustomerStore.objectExists == true) {
            throw new IllegalStateException("Restaurant customer database already exists");
        }
        restaurantCustomers = new ArrayList<>();
        objectExists = true;
        // loadFromFile();
    }

// -----------------------------------------------------------------Add Operations-------------------------------------------------------------------------------------------------------------

    public void addCustomer(RestaurantCustomer customer) { // returns the id assigned to the customer by the database
        restaurantCustomers.add(customer);
    }

// -----------------------------------------------------------------Delete Operations-------------------------------------------------------------------------------------------------------------

    public void deleteCustomer(int id) {
        for (int i = 0; i < restaurantCustomers.size(); i++)
        if (restaurantCustomers.get(i).getCustomerId() == id){
            restaurantCustomers.remove(i);
            return;
        }
    }

// -----------------------------------------------------------------Read Operations-------------------------------------------------------------------------------------------------------------

    public ArrayList<RestaurantCustomer> getCustomers() {
        return this.restaurantCustomers;
    }

    public int getCustomerId(RestaurantCustomer customer){
        for (int i =0; i<restaurantCustomers.size(); i++) if (restaurantCustomers.get(i).getPhone() == customer.getPhone()) return restaurantCustomers.get(i).getCustomerId();
        return 0;
    }

    public RestaurantCustomer getCustomer(int id){
        for (int i =0; i<restaurantCustomers.size(); i++) if (restaurantCustomers.get(i).getCustomerId() == id) return restaurantCustomers.get(i);
        return null;
    }

// -----------------------------------------------------------------Update Operations-------------------------------------------------------------------------------------------------------------

    public void updateCustomer(int id, RestaurantCustomer customer) {
        for (int i = 0; i < restaurantCustomers.size(); i++)
            if (restaurantCustomers.get(i).getCustomerId() == id){
            restaurantCustomers.set(i, customer);
            break;
        }
    }

    public String viewOrders(){
        String str = "";
        for (RestaurantCustomer rc : restaurantCustomers){
            str = str + "Customer id: " + rc.getCustomerId() + '\n';
            str = str + rc.viewOrder();
        }
        return str;
    }

// -----------------------------------------------------------------Incomplete methods-------------------------------------------------------------------------------------------------------------

    public native void loadFromFile() ;

    public native void storeToFile() ;
}
