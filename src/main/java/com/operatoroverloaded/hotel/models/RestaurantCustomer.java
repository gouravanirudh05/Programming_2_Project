package com.operatoroverloaded.hotel.models;
import java.util.ArrayList;

import com.operatoroverloaded.hotel.stores.billstore.BillStore;
import com.operatoroverloaded.hotel.stores.dishstore.DishStore;

public class RestaurantCustomer extends Customer{
    private ArrayList<Integer> dishes;
    private int tableId;
    private int serverId;


//-------------------------------------------------------------------Constructors--------------------------------------------------------------------------------------------------


    public RestaurantCustomer(){
        super();
        this.dishes = new ArrayList<Integer>();
        this.tableId = 0;
        this.serverId = 0;
    }

    public RestaurantCustomer(int id, String name, String email, String phone, String address, int tableid, int serverid, DateTime reservedfrom, DateTime reservedto){
        super(id, name, email, phone, address, reservedfrom, reservedto);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    public RestaurantCustomer(String name, String email, String phone, String address, int tableid, int serverid, DateTime reservedfrom, DateTime reservedto){
        super(name, email, phone, address, reservedfrom, reservedto);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    
 //-------------------------------------------------------------------Setter Methods--------------------------------------------------------------------------------------------------


    public void addDish(int dishid){ //expects dish id
        for (int id : this.dishes) if (id == dishid) return; //if dish already exists simply return
        this.dishes.add(dishid);
    }

    public void removeDish(int dishid){
        this.dishes.remove(Integer.valueOf(dishid));
    }

    public void setTableId(int id){
        this.tableId = id;
    }
    public void setServerId(int id){
        this.serverId = id;
    }


//-------------------------------------------------------------------Getter Methods--------------------------------------------------------------------------------------------------


    public ArrayList<Integer> getDishes(){
        return this.dishes;
    }
    public int getTableId(){
        return this.tableId;
    }
    public int getServerId(){
        return this.serverId;
    }

//-------------------------------------------------------------------Other constructors--------------------------------------------------------------------------------------------------


    public RestaurantCustomer(int id, String name, String email, String phone, String address, int tableid, int serverid, DateTime reservedfrom){
        super(id, name, email, phone, address, reservedfrom);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    public RestaurantCustomer(String name, String email, String phone, String address, int tableid, int serverid, DateTime reservedfrom){
        super(name, email, phone, address, reservedfrom);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    public RestaurantCustomer(String name, String email, String phone, String address, int tableid, int serverid){
        super(name, email, phone, address);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    public RestaurantCustomer(String name, String email, String phone, String address, double bill_amt, double bill_payed, double bill_left, ArrayList<Integer> bills, DateTime reservedFrom, DateTime reservedTo, ArrayList<Integer> dishes, int tableId, int serverId){
        super(name, email, phone, address, bill_amt, bill_payed, bill_left, bills, reservedFrom, reservedTo);
        this.dishes = dishes;
        this.tableId = tableId;
        this.serverId = serverId;
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
        DishStore store2 = DishStore.getInstance();
        store2.loadFromFile();
        if (dishes.size() > 0) {
            str = str + "\nDishes:\n";
            for (Integer i: dishes){
                Dish r = store2.findDish(i);
                str = str + r.toString();
            }
        }
        str = str + "\nTable id: " + tableId;
        str = str + "\nServer id: " + serverId;
        return str + '\n';
    }

    public String viewOrder(){
        String str = "";
        DishStore store2 = DishStore.getInstance();
        str = str + "\nDishes:\n";
        for (Integer i: dishes){
            Dish r = store2.findDish(i);
            str = str + r.toString();
        }
        str = str + "\nTable id: " + tableId;
        str = str + "\nServer id: " + serverId;
        return str + '\n';
    }

}
