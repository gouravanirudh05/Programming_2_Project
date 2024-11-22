package com.operatoroverloaded.hotel.stores.hotelcustomerstore;
import java.util.ArrayList;
import java.util.List;

import com.operatoroverloaded.hotel.models.HotelCustomer;

public class InMemoryHotelCustomerStore extends HotelCustomerStore {
    private static final InMemoryHotelCustomerStore instance = new InMemoryHotelCustomerStore();

    public static InMemoryHotelCustomerStore getInstance() {
        return instance;
    }
// -----------------------------------------------------------------Attributes-------------------------------------------------------------------------------------------------------------

    private List<HotelCustomer> hotelCustomers;
    private static int id = 0;
    private static boolean objectExists = false; // i want only one database for each class, so this keeps a track of
                                                 // whether a databse already exists or no

// -----------------------------------------------------------------Constructor-------------------------------------------------------------------------------------------------------------

    public InMemoryHotelCustomerStore() { // constructor (also calls the loadFromFile function)
        if (InMemoryHotelCustomerStore.objectExists == true) {
            throw new Error("Hotel customer database already exists");
        }
        hotelCustomers = new ArrayList<>();
        id = 0;
        objectExists = true;
        loadFromFile();
    }

// -----------------------------------------------------------------Add Operations-------------------------------------------------------------------------------------------------------------

    public int addCustomer(HotelCustomer customer) { // returns the id assigned to the customer by the database
        for (HotelCustomer x : hotelCustomers)
        if (customer.getPhone() == x.getPhone())
        return x.getCustomerId(); // assuming that every customer has a unique phone number
        customer.setCustomerId(id);
        id++;
        hotelCustomers.add(customer);
        return id - 1;
    }

// -----------------------------------------------------------------Delete Operations-------------------------------------------------------------------------------------------------------------

    public void deleteCustomer(int id) {
        for (int i = 0; i < hotelCustomers.size(); i++)
        if (hotelCustomers.get(i).getCustomerId() == id)
        hotelCustomers.remove(i);
    }

// -----------------------------------------------------------------Read Operations-------------------------------------------------------------------------------------------------------------

    public List<HotelCustomer> getCustomers() {
        return this.hotelCustomers;
    }

    public int getCustomerId(HotelCustomer customer){
        for (int i =0; i<hotelCustomers.size(); i++) if (hotelCustomers.get(i).getPhone() == customer.getPhone()) return hotelCustomers.get(i).getCustomerId();
        return -1;
    }

    public HotelCustomer getCustomer(int id){
        for (int i =0; i<hotelCustomers.size(); i++) if (hotelCustomers.get(i).getCustomerId() == id) return hotelCustomers.get(i);
        return null;
    }

// -----------------------------------------------------------------Update Operations-------------------------------------------------------------------------------------------------------------

    public void updateCustomer(int id, HotelCustomer customer) {
        customer.setCustomerId(id);
        for (int i = 0; i < hotelCustomers.size(); i++)
        if (hotelCustomers.get(i).getCustomerId() == id)
        hotelCustomers.set(i, customer);
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
