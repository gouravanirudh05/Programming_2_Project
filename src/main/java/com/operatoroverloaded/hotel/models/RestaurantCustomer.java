import java.util.ArrayList;

public class RestaurantCustomer extends Customer{
    private ArrayList<Integer> dishes;
    private int tableId;
    private int serverId;


//-------------------------------------------------------------------Constructors--------------------------------------------------------------------------------------------------


    RestaurantCustomer(){
        super();
        this.dishes = new ArrayList<Integer>();
        this.tableId = -1;
        this.serverId = -1;
    }

    RestaurantCustomer(int id, String name, String email, String phone, String address, int tableid, int serverid, DateTime reservedfrom, DateTime reservedto){
        super(id, name, email, phone, address, reservedfrom, reservedto);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    RestaurantCustomer(String name, String email, String phone, String address, int tableid, int serverid, DateTime reservedfrom, DateTime reservedto){
        super(name, email, phone, address, reservedfrom, reservedto);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    
 //-------------------------------------------------------------------Setter Methods--------------------------------------------------------------------------------------------------


    void addDish(int dishid){ //expects dish id
        for (int id : this.dishes) if (id == dishid) return; //if dish already exists simply return
        this.dishes.add(dishid);
    }

    void removeDish(int dishid){
        this.dishes.remove(Integer.valueOf(dishid));
    }

    void setTableId(int id){
        this.tableId = id;
    }
    void setserverId(int id){
        this.serverId = id;
    }


//-------------------------------------------------------------------Getter Methods--------------------------------------------------------------------------------------------------


    ArrayList<Integer> getDishes(){
        return this.dishes;
    }
    int getTableId(){
        return this.tableId;
    }
    int getServerId(){
        return this.serverId;
    }

//-------------------------------------------------------------------Other constructors--------------------------------------------------------------------------------------------------


    RestaurantCustomer(int id, String name, String email, String phone, String address, int tableid, int serverid, DateTime reservedfrom){
        super(id, name, email, phone, address, reservedfrom);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    RestaurantCustomer(String name, String email, String phone, String address, int tableid, int serverid, DateTime reservedfrom){
        super(name, email, phone, address, reservedfrom);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    RestaurantCustomer(String name, String email, String phone, String address, int tableid, int serverid){
        super(name, email, phone, address);
        this.tableId = tableid;
        this.serverId = serverid;
    }

}
