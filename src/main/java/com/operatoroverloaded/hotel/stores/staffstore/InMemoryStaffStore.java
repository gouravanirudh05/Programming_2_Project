package com.operatoroverloaded.hotel.stores.staffstore;

import com.operatoroverloaded.hotel.models.Staff;
import java.util.ArrayList;
import java.util.List;

public class InMemoryStaffStore extends StaffStore {
    static{
        System.loadLibrary("StaffStore");
    }
    private static final InMemoryStaffStore instance = new InMemoryStaffStore();

    public static InMemoryStaffStore getInstance() {
        return instance;
    }

    private List<Staff> staffList;

    public InMemoryStaffStore() {
        this.staffList = new ArrayList<>();
        this.loadFromFile();
    }

    @Override
    public void addStaff(Staff staff) {
        staffList.add(staff);
    }

    @Override
    public void updateStaff(Staff staff) {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getStaffID() == staff.getStaffID()) {
                staffList.set(i, staff);
                return;
            }
        }
    }

    @Override
    public void removeStaff(int staffID) {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getStaffID() == staffID) {
                staffList.remove(i);
                return;
            }
        }
    }

    @Override
    public Staff getStaffById(int staffID) {
        for (Staff staff : staffList) {
            if (staff.getStaffID() == staffID) {
                return staff;
            }
        }
        return null; // Staff not found
    }

    @Override
    public List<Staff> getAllStaff() {
        return new ArrayList<>(staffList);
    }

    @Override
    public native void saveToFile();

    @Override
    public native void loadFromFile();
}
