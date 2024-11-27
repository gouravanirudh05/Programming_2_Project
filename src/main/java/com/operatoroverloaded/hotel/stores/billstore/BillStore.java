package com.operatoroverloaded.hotel.stores.billstore;

import java.util.ArrayList;

import com.operatoroverloaded.hotel.models.Bill;
import com.operatoroverloaded.hotel.models.DateTime;

public abstract class BillStore {
    public static BillStore billStore = null;
    public static BillStore getInstance(){
        return billStore;
    }
    public static void setInstance(BillStore billStore){
        BillStore.billStore = billStore;
    }
    public abstract void addBill(ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, DateTime payedOn, boolean payed, String customerID);
    public abstract void removeBill(int billId);
    public abstract void updateBill(int billId, ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, DateTime payedOn,boolean payed, String customerID);
    public abstract void save();
    public abstract void load();
    public abstract Bill getBill(int billId);
    public abstract ArrayList<Bill> getBills();
}