package com.operatoroverloaded.hotel.stores.staffstore;

import com.operatoroverloaded.hotel.models.Staff;
import java.util.List;

public abstract class StaffStore {
    public static StaffStore staffStore = null;
    public static StaffStore getInstance(){
        return staffStore;
    }
    public static void setInstance(StaffStore staffStore){
        StaffStore.staffStore = staffStore;
    }
    abstract void addStaff(Staff staff);
    abstract void updateStaff(Staff staff);
    abstract void removeStaff(int staffID);
    abstract Staff getStaffById(int staffID);
    abstract List<Staff> getAllStaff();
    abstract void saveToFile();
    abstract void loadFromFile();
}
