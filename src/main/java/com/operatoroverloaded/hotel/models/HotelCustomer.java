package com.operatoroverloaded.hotel.models;
import java.util.ArrayList;

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

}
