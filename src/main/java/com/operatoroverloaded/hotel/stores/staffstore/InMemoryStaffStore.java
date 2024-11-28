package com.operatoroverloaded.hotel.stores.staffstore;

import java.util.ArrayList;
import com.operatoroverloaded.hotel.models.Staff;

public class InMemoryStaffStore extends StaffStore {
    // Load the native library
    static{
        System.loadLibrary("StaffStore");
    }
    // Singleton instance
    private static final InMemoryStaffStore instance = new InMemoryStaffStore();

    // Singleton instance getter
    public static InMemoryStaffStore getInstance() {
        return instance;
    }

    // Data store
    private ArrayList<Staff> staffData;

    // constructor
    public InMemoryStaffStore() {
        this.staffData = new ArrayList<>();
        // this.loadFromFile();
    }

    // method to add staff
    @Override
    public void addStaff(Staff staff) {
        staffData.add(staff);
    }

    // method to update staff
    @Override
    public void updateStaff(Staff staff) {
        for (int i = 0; i < staffData.size(); i++) {
            if (staffData.get(i).getStaffID() == staff.getStaffID()) {
                staffData.set(i, staff);
                return;
            }
        }
    }

    // method to remove staff
    @Override
    public void removeStaff(int staffID) {
        for (int i = 0; i < staffData.size(); i++) {
            if (staffData.get(i).getStaffID() == staffID) {
                staffData.remove(i);
                return;
            }
        }
    }

    // method to get staff by ID
    @Override
    public Staff getStaffById(int staffID) {
        for (Staff staff : staffData) {
            if (staff.getStaffID() == staffID) {
                return staff;
            }
        }
        return null; // Staff not found
    }

    // method to get all staff
    @Override
    public ArrayList<Staff> getAllStaff() {
        return new ArrayList<>(staffData);
    }

    // method to save to file
    @Override
    public native void saveToFile();

    // method to load from file
    @Override
    public native void loadFromFile();
}
