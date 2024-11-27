package com.operatoroverloaded.hotel.stores.billstore;

import java.util.ArrayList;
import com.operatoroverloaded.hotel.models.Bill;
import com.operatoroverloaded.hotel.models.DateTime;

public class InMemoryBillStore extends BillStore {
    static {
        System.loadLibrary("BillCPP");
    }
    private static final InMemoryBillStore instance = new InMemoryBillStore();

    // Getter for the instance
    // @Override
    public static InMemoryBillStore getInstance(){
        return instance;
    }
    private ArrayList<Bill> billData;

    // Constructor which also loads the data from the native library
    public InMemoryBillStore(){
        this.billData = new ArrayList<Bill>();
        this.load();
        System.err.println("Bill store loaded");
    }

    // Getter for the billData
    public ArrayList<Bill> getBillData(){
        return this.billData;
    }

    // Getter for the billData
    @Override
    public Bill getBill(int billId) {
        for(Bill bill: this.billData){
            if(billId == bill.getBillId()){
                return bill;
            }
        }        
        return null;
    }

    // // Native method declaration
    private native void loadBill();
    private native void saveBill();

    @Override
    public void save(){
        this.saveBill();
    }

    @Override
    public void load(){
        this.loadBill();        
    }

    // add a new bill to the billData
    @Override
    public Bill addBill(ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, DateTime payedOn, boolean payed, String customerID) {
        // Check if the sizes of the lists are not the same and return if they are not
        if(purchased.size() != purchasedList.size() || purchasedList.size() != quantity.size()){
            return null;
        }
        // int billId,ArrayList<String> purchased, ArrayList<Float> purchasedList, 
        // ArrayList<Integer> quantity, DateTime payedOn, String customerID
        Bill bill = new Bill(
            this.billData.size() == 0? 1: this.billData.get(this.billData.size()-1).getBillId()+1, 
            purchased, 
            purchasedList, 
            quantity, 
            payedOn,
            payed,
            customerID
        );
        this.billData.add(bill);
        return bill;
    }
    
    // remove a bill from the billData based on the billId
    @Override
    public void removeBill(int billId) {
        for(Bill bill: billData){
            if(billId == bill.getBillId()){
                billData.remove(bill);
                return;
            }
        }
    }

    // update a bill in the billData based on the billId
    @Override
    public void updateBill(int billId, ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, DateTime payedOn,boolean payed, String customerID) {
        if(purchased.size() != purchasedList.size() || purchasedList.size() != quantity.size()){
            return;
        }
        for(Bill bill: billData){
            if(billId == bill.getBillId()){
                bill.setPayedOn(payedOn);
                bill.setItems(purchased, purchasedList, quantity);
                bill.setPayed(payed);
                bill.setCustomerID(customerID);
            }
        }
    }

    @Override
    public ArrayList<Bill> getBills() {
        return new ArrayList<>(billData);
    }
}
