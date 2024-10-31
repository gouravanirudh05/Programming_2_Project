import java.util.ArrayList;

public class RestaurantCustomer extends Customer{
    //Attributes
    private ArrayList<Integer> dishes;
    private int tableId;
    private int serverId;

    //Methods
    //Constructor
    public RestaurantCustomer(){
        super();
        this.dishes = new ArrayList<Integer>();
        this.tableId = -1;
        this.serverId = -1;
    }

    public RestaurantCustomer(int id, String name, String email, int phone, String address, int tableid, int serverid, DateTime reservedfrom, DateTime reservedto){
        super(id, name, email, phone, address, reservedfrom, reservedto);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    public RestaurantCustomer(int id, String name, String email, int phone, String address, int tableid, int serverid, DateTime reservedfrom){
        super(id, name, email, phone, address, reservedfrom);
        this.tableId = tableid;
        this.serverId = serverid;
    }

    //setters
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
    public void setReservedFrom(DateTime d){
        this.reservedFrom = d;
    }
    public void setReservedTo(DateTime d){
        this.reservedTo = d;
    }

    //getters
    public ArrayList<Integer> getDishes(){
        return this.dishes;
    }
    public int getTableId(){
        return this.tableId;
    }
    public int getServerId(){
        return this.serverId;
    }
}
