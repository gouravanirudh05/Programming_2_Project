package com.operatoroverloaded.hotel.models;
import java.util.*;

public class Bill {
    private int billId;
    private float amount;
    private ArrayList<String> purchased;
    private ArrayList<Float> purchasedList;
    private ArrayList<Integer> quantity;
    private DateTime generatedOn, payedOn;

    // default constructor which initializes the values to garbage values
    public Bill(){
        billId = -1;
        amount = -1;
        this.amount = -1;
        this.purchased = new ArrayList<String>();
        this.quantity = new ArrayList<Integer>();
        this.purchasedList = new ArrayList<Float>();
        this.generatedOn = new DateTime(0, 0, 0, 0, 0, 0);
        this.payedOn = new DateTime(0, 0, 0, 0, 0, 0);
    }

    // parameterized constructor which should be used to create a new bill
    public Bill(int billId, ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, DateTime payedOn){
        this.billId = billId;
        this.amount = 0;
        for (int i = 0; i < purchasedList.size(); i++){
            this.amount += purchasedList.get(i) * quantity.get(i);
        }
        this.quantity = quantity;
        this.purchased = purchased;
        this.purchasedList = purchasedList;
        this.generatedOn = DateTime.getCurrentTime();
        this.payedOn = payedOn;
    }

    // parameterized constructor which is used to store the bill data in the database file (not to be used to create a new bill)
    public Bill(int billId, float amount, ArrayList<String> purchased, ArrayList<Float> purchasedList, 
                ArrayList<Integer> quantity, String genDate, String genTime, String payedDate, String payedTime){
        this.billId = billId;
        this.amount = amount;
        this.purchased = purchased;
        this.quantity = quantity;
        this.purchasedList = purchasedList;
        this.generatedOn = DateTime.fromString(genDate.replace('/', '-'), genTime);
        this.payedOn = DateTime.fromString(payedDate.replace('/', '-'), payedTime);
    }

    public int getBillId(){
        return billId;
    }

    public float getAmount(){
        return amount;
    }

    public ArrayList<String> getPurchased(){
        return purchased;
    }

    public ArrayList<Float> getPurchasedList(){
        return purchasedList;
    }

    public ArrayList<Integer> getQuantity(){
        return quantity;
    }

    public String getGeneratedOn(){
        return generatedOn.getDateString() + " " + generatedOn.getTimeString();
    }

    public String getPayedOn(){
        return payedOn.getDateString() + " " + payedOn.getTimeString();
    }

    public void setAmount(){
        float amt = 0;
        for(int i =  0; i < purchased.size(); i++){
            amt += purchasedList.get(i) * quantity.get(i);
        }   
        this.amount = amt;
    }

    public void setAmount(float amount){
        this.amount = amount;
    }

    public void setPurchased(ArrayList<String> purchased){
        this.purchased = purchased;
    }

    public void setPurchasedList(ArrayList<Float> purchasedList){
        this.purchasedList = purchasedList;
    }

    public void setQuantity(ArrayList<Integer> quantity){
        this.quantity = quantity;
    }
    
    public void setItems(ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity){
        if(purchased.size() != purchasedList.size() || purchased.size() != quantity.size()){
            return;
        }
        setPurchased(purchased);
        setPurchasedList(purchasedList);
        setQuantity(quantity);
        setAmount();
    }

    public void setPayedOn(DateTime payedOn){
        this.payedOn = payedOn;
    }

    @Override
    public String toString() {
        String str = "Bill ID: " + billId + "\nAmount: " + amount + "\nItems:\n";
        for(int i = 0; i < purchased.size(); i++){
            str += purchased.get(i) + " " + purchasedList.get(i) + " " + quantity.get(i) + "\n";
        }
        str += "Generated on: " + generatedOn.getDateString() + " " + generatedOn.getTimeString() + "\n";
        str += "Payed on: " + payedOn.getDateString() + " " + payedOn.getTimeString() + "\n";
        return str;
    }
}