package com.operatoroverloaded.hotel.stores.billstore;

import java.util.ArrayList;
import com.operatoroverloaded.hotel.models.Bill;
import com.operatoroverloaded.hotel.models.DateTime;

public interface BillStore {
    public void addBill(ArrayList<String> a, ArrayList<Float> b, ArrayList<Integer> c, DateTime payed);
    public void removeBill(int billId);
    public void updateBill(int billId, ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, DateTime payedOn);
    public void save();
    public void load();
    public Bill getBill(int billId);
}
