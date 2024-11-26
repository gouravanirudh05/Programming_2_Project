package com.operatoroverloaded.hotel.stores.staffstore;

import java.util.ArrayList;
import com.operatoroverloaded.hotel.models.Staff;

public class InMemoryStaffStore extends StaffStore {
    static{
        System.loadLibrary("StaffStore");
    }
    private static final InMemoryStaffStore instance = new InMemoryStaffStore();

    public static InMemoryStaffStore getInstance() {
        return instance;
    }

    private ArrayList<Staff> staffData;

    public InMemoryStaffStore() {
        this.staffData = new ArrayList<>();
        // this.loadFromFile();
    }

    @Override
    public void addStaff(Staff staff) {
        staffData.add(staff);
    }

    @Override
    public void updateStaff(Staff staff) {
        for (int i = 0; i < staffData.size(); i++) {
            if (staffData.get(i).getStaffID() == staff.getStaffID()) {
                staffData.set(i, staff);
                return;
            }
        }
    }

    @Override
    public void removeStaff(int staffID) {
        for (int i = 0; i < staffData.size(); i++) {
            if (staffData.get(i).getStaffID() == staffID) {
                staffData.remove(i);
                return;
            }
        }
    }

    @Override
    public Staff getStaffById(int staffID) {
        for (Staff staff : staffData) {
            if (staff.getStaffID() == staffID) {
                return staff;
            }
        }
        return null; // Staff not found
    }

    @Override
    public ArrayList<Staff> getAllStaff() {
        return new ArrayList<>(staffData);
    }

    @Override
    public native void saveToFile();

    @Override
    public native void loadFromFile();
}
