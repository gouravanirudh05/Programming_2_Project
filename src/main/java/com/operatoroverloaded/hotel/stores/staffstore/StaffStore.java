package com.operatoroverloaded.hotel.stores.staffstore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.Staff;

public abstract class StaffStore {
    public static StaffStore staffStore = null;
    public static StaffStore getInstance(){
        return staffStore;
    }
    public static void setInstance(StaffStore staffStore){
        StaffStore.staffStore = staffStore;
    }
    public abstract void addStaff(Staff staff);
    public abstract void updateStaff(Staff staff);
    public abstract void removeStaff(int staffID);
    public abstract Staff getStaffById(int staffID);
    public abstract ArrayList<Staff> getAllStaff();
    public abstract void saveToFile();
    public abstract void loadFromFile();
}
