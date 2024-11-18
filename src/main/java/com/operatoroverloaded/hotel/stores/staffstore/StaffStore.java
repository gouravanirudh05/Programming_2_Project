package com.operatoroverloaded.hotel.stores.staffstore;

import com.operatoroverloaded.hotel.models.Staff;
import java.util.List;

public interface StaffStore {
    public static StaffStore staffStore = null;
    public static StaffStore getInstance(){
        return staffStore;
    }
    public static void setInstance(StaffStore staffStore){
        staffStore = staffStore;
    }
    void addStaff(Staff staff);
    void updateStaff(Staff staff);
    void removeStaff(int staffID);
    Staff getStaffById(int staffID);
    List<Staff> getAllStaff();
    void saveToFile();
    void loadFromFile();
}
