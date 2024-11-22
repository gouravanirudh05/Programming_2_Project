package com.operatoroverloaded.hotel.models;
import java.util.ArrayList;

public class RestaurantCustomer extends Customer{
    private ArrayList<Integer> dishes;
    private int tableId;
    private int serverId;


//-------------------------------------------------------------------Constructors--------------------------------------------------------------------------------------------------


    public RestaurantCustomer(){
        super();
        this.dishes = new ArrayList<Integer>();
        this.tableId = -1;
        this.serverId = -1;
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
    public void setserverId(int id){
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

}
