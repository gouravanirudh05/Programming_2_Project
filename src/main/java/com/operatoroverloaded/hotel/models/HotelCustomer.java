import java.util.ArrayList;

public class HotelCustomer extends Customer{
    //Attributes
    private ArrayList<Integer> reservations;
    //methods
    //Constructor
    public HotelCustomer(){
        super();
        this.reservations = new ArrayList<Integer>();
    }
    public HotelCustomer(int id, String name, String email, String phone, String address, DateTime reservedfrom, DateTime reservedto){
        super(id, name, email, phone, address, reservedfrom, reservedto);
        this.reservations = new ArrayList<Integer>();
    }

    public HotelCustomer(int id, String name, String email, String phone, String address, DateTime reservedfrom){
        super(id, name, email, phone, address, reservedfrom);
        this.reservations = new ArrayList<Integer>();
    }

    //setters
    public void addReservation(int reservationId){ //expects dish id
        for (int id : this.reservations) if (reservationId == id) return; //if billid already exists simply return
        this.reservations.add(reservationId);
    }

    public void removeReservation(int id){
        this.reservations.remove(Integer.valueOf(id));
    }

    //getters
    
    public ArrayList<Integer> getReservations(){
        return this.reservations;
    }
}
