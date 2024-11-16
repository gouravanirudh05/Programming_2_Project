package com.operatoroverloaded.hotel.stores.staffstore;

import com.operatoroverloaded.hotel.models.Staff;
import java.util.List;

public interface StaffStore {
    void addStaff(Staff staff);
    void updateStaff(Staff staff);
    void removeStaff(int staffID);
    Staff getStaffById(int staffID);
    List<Staff> getAllStaff();
}
