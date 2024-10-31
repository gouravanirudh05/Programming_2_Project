package com.operatoroverloaded.hotel.stores.billstore;

import java.util.ArrayList;

public interface BillStore {
    public void addBill(ArrayList<String> a, ArrayList<Float> b, ArrayList<Integer> c, String gen, String pay);
    public void removeBill(int billId);
    public void updateBill(int billId, ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, String payedOn);
    public void save();
    public void load();
}
