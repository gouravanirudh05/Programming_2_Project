package com.operatoroverloaded.hotel.stores.billstore;

import java.util.ArrayList;
import com.operatoroverloaded.hotel.models.Bill;

public class InMemoryBillStore implements BillStore {
    private  ArrayList<Bill> billData;

    public InMemoryBillStore(){
        this.billData = new ArrayList<Bill>();
        this.load();
    }

    public ArrayList<Bill> getBillData(){
        return this.billData;
    }

    // Native method declaration
    public native void loadBill();
    public native void saveBill();

    // Load the native library
    static {
        System.loadLibrary("BillCPP");
    }

    public void save(){
        this.saveBill();
    }

    public void load(){
        this.loadBill();        
    }

    @Override
    public void addBill(ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, String generatedOn, String payedOn) {
        // Check if the sizes of the lists are not the same and return if they are not
        if(purchased.size() != purchasedList.size() || purchasedList.size() != quantity.size()){
            return;
        }
        
        Bill bill = new Bill(
            this.billData.size() == 0? 1: this.billData.get(this.billData.size()-1).getBillId()+1, 
            purchased, 
            purchasedList, 
            quantity, 
            generatedOn, 
            payedOn
        );
        this.billData.add(bill);
    }

    @Override
    public void removeBill(int billId) {
        for(Bill bill: billData){
            if(billId == bill.getBillId()){
                billData.remove(bill);
                return;
            }
        }
    }

    @Override
    public void updateBill(int billId, ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, String payedOn) {
        if(purchased.size() != purchasedList.size() || purchasedList.size() != quantity.size()){
            return;
        }
        for(Bill bill: billData){
            if(billId == bill.getBillId()){
                bill.setPayedOn(payedOn);
                bill.setPurchased(purchased);
                bill.setPurchasedList(purchasedList);
                bill.setQuantity(quantity);
                bill.setAmount();
            }
        }
    }
}
