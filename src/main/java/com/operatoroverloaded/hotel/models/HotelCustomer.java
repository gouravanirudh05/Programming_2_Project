package com.operatoroverloaded.hotel.models;
import java.util.ArrayList;

import com.operatoroverloaded.hotel.stores.billstore.BillStore;
import com.operatoroverloaded.hotel.stores.reservationstore.*;;

public class HotelCustomer extends Customer{

    private ArrayList<Integer> reservations; //I will just store the id's here

//-------------------------------------------------------------------Constructors--------------------------------------------------------------------------------------------------


    public HotelCustomer(){
        super();
        this.reservations = new ArrayList<Integer>();
    }
    public HotelCustomer(int id, String name, String email, String phone, String address, DateTime reservedfrom, DateTime reservedto){
        super(id, name, email, phone, address, reservedfrom, reservedto);
        this.reservations = new ArrayList<Integer>();
    }

    public HotelCustomer(String name, String email, String phone, String address, DateTime reservedfrom, DateTime reservedto){
        super(name, email, phone, address, reservedfrom, reservedto);
        this.reservations = new ArrayList<Integer>();
    }



//-------------------------------------------------------------------Setter Methods--------------------------------------------------------------------------------------------------


    public void addReservation(int reservationId){ //expects dish id
        for (int id : this.reservations) if (reservationId == id) return; //if billid already exists simply return
        this.reservations.add(reservationId);
    }

    public void removeReservation(int id){
        this.reservations.remove(Integer.valueOf(id));
    }


//-------------------------------------------------------------------Getter Methods--------------------------------------------------------------------------------------------------
    

    public ArrayList<Integer> getReservations(){
        return this.reservations;
    }

//-------------------------------------------------------------------Other Constructors--------------------------------------------------------------------------------------------------


    public HotelCustomer(int id, String name, String email, String phone, String address, DateTime reservedfrom){
        super(id, name, email, phone, address, reservedfrom);
        this.reservations = new ArrayList<Integer>();
    }

    public HotelCustomer(String name, String email, String phone, String address, DateTime reservedfrom){
        super(name, email, phone, address, reservedfrom);
        this.reservations = new ArrayList<Integer>();
    }

    public HotelCustomer(String name, String email, String phone, String address){
        super(name, email, phone, address);
        this.reservations = new ArrayList<Integer>();
    }

    public HotelCustomer(String name, String email, String phone, String address, double bill_amt, double bill_payed, double bill_left, ArrayList<Integer> bills, DateTime reservedFrom, DateTime reservedTo, ArrayList<Integer> reservations){
        super(name, email, phone, address, bill_amt, bill_payed, bill_left, bills, reservedFrom, reservedTo);
        this.reservations = reservations;
    }
    @Override
    public String toString(){
        String str = "";
        str = str + "Name: " + this.getName() + "\nEmail: " + this.getEmail() + "\nPhone number: " + this.getPhone() + "\nAddress: " + this.getAddress() + "\nTotal Bill amount: " + this.getBillAmount() + "\nBill payed: " + this.getBillPayed() + "\nBill left: " + this.getBillLeft();
        ArrayList<Integer> bills = this.getBills();
        BillStore store = BillStore.getInstance();
        store.load();
        if (bills.size() > 0) {
            str = str + "\nBills:\n";
            for (Integer i: bills){
                Bill b = store.getBill(i);
                str = str + b.toString();
            }
        }
        str = str + "\nReserved from: " + reservedFrom.toString();
        if (reservedTo.timeDifference(new DateTime(0,0,0,0,0,0)) != 0) str = str + "\nReserved to: " + reservedTo.toString();
        ReservationStore store2 = ReservationStore.getInstance();
        store2.load();
        if (reservations.size() > 0) {
            str = str + "\nReservations:\n";
            for (Integer i: reservations){
                Reservation r = store2.getReservation(i);
                str = str + r.toString();
            }
        }
        return str + '\n';
    }

}
