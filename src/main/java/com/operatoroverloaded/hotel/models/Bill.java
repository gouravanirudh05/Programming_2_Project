package com.operatoroverloaded.hotel.models;
import java.util.ArrayList;

public class Bill {
    private int billId;
    private float amount;
    private ArrayList<String> purchased;
    private ArrayList<Float> purchasedList;
    private ArrayList<Integer> quantity;
    private String generatedOn, payedOn;

    public Bill(){
        billId = -1;
        amount = -1;
        this.amount = -1;
        this.purchased = new ArrayList<String>();
        this.quantity = new ArrayList<Integer>();
        this.purchasedList = new ArrayList<Float>();
        this.generatedOn = "";
        this.payedOn = "";
    }

    public Bill(int billId, ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, String generatedOn, String payedOn){
        this.billId = billId;
        this.amount = 0;
        for (int i = 0; i < purchasedList.size(); i++){
            this.amount += purchasedList.get(i) * quantity.get(i);
        }
        this.quantity = quantity;
        this.purchased = purchased;
        this.purchasedList = purchasedList;
        this.generatedOn = generatedOn;
        this.payedOn = payedOn;
    }

    public Bill(int billId, float amount, ArrayList<String> purchased, ArrayList<Float> purchasedList, ArrayList<Integer> quantity, String generatedOn, String payedOn){
        this.billId = billId;
        this.amount = amount;
        this.purchased = purchased;
        this.quantity = quantity;
        this.purchasedList = purchasedList;
        this.generatedOn = generatedOn;
        this.payedOn = payedOn;
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
        return generatedOn;
    }

    public String getPayedOn(){
        return payedOn;
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

    public void setPayedOn(String payedOn){
        this.payedOn = payedOn == ""? this.payedOn: payedOn;
    }
}