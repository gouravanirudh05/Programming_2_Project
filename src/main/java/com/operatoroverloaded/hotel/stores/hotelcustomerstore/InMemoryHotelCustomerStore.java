package com.operatoroverloaded.hotel.stores.hotelcustomerstore;
import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.HotelCustomer;

public class InMemoryHotelCustomerStore extends HotelCustomerStore {
    static {
        System.loadLibrary("HotelCustomer");
    }
    private static final InMemoryHotelCustomerStore instance = new InMemoryHotelCustomerStore();

    public static InMemoryHotelCustomerStore getInstance() {
        return instance;
    }
// -----------------------------------------------------------------Attributes-------------------------------------------------------------------------------------------------------------

    private ArrayList<HotelCustomer> customers;
    private static int id = 0;
    private static boolean objectExists = false; // i want only one database for each class, so this keeps a track of
                                                 // whether a databse already exists or no

// -----------------------------------------------------------------Constructor-------------------------------------------------------------------------------------------------------------

    public InMemoryHotelCustomerStore() { // constructor (also calls the loadFromFile function)
        if (InMemoryHotelCustomerStore.objectExists == true) {
            throw new Error("Hotel customer database already exists");
        }
        customers = new ArrayList<>();
        id = 0;
        objectExists = true;
        // loadFromFile();
    }

// -----------------------------------------------------------------Add Operations-------------------------------------------------------------------------------------------------------------

    public HotelCustomer addCustomer(HotelCustomer customer) { // returns the id assigned to the customer by the database
        for (HotelCustomer x : customers)
        if (customer.getPhone() == x.getPhone())
        return x; // assuming that every customer has a unique phone number
        customer.setCustomerId(id);
        id++;
        customers.add(customer);
        return customer;
    }

// -----------------------------------------------------------------Delete Operations-------------------------------------------------------------------------------------------------------------

    public void deleteCustomer(int id) {
        for (int i = 0; i < customers.size(); i++)
        if (customers.get(i).getCustomerId() == id)
        customers.remove(i);
    }

// -----------------------------------------------------------------Read Operations-------------------------------------------------------------------------------------------------------------

    public ArrayList<HotelCustomer> getCustomers() {
        return this.customers;
    }

    public int getCustomerId(HotelCustomer customer){
        for (int i =0; i<customers.size(); i++) if (customers.get(i).getPhone() == customer.getPhone()) return customers.get(i).getCustomerId();
        return -1;
    }

    public HotelCustomer getCustomer(int id){
        for (int i =0; i<customers.size(); i++) if (customers.get(i).getCustomerId() == id) return customers.get(i);
        return null;
    }

// -----------------------------------------------------------------Update Operations-------------------------------------------------------------------------------------------------------------

    public void updateCustomer(int id, HotelCustomer customer) {
        customer.setCustomerId(id);
        for (int i = 0; i < customers.size(); i++)
        if (customers.get(i).getCustomerId() == id)
        customers.set(i, customer);
    }

// -----------------------------------------------------------------Incomplete methods-------------------------------------------------------------------------------------------------------------

    public native void loadFromFile() ;
        // from the file load the customers and the id
        // modify the id and hotel customers

    public native void storeToFile() ;
        // store the id in first line and hotel customers afterwards
    
}
