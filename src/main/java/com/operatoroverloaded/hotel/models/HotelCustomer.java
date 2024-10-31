import java.util.ArrayList;

public class HotelCustomer extends Customer{
    //Attributes
    private ArrayList<Integer> reservations;
    DateTime reservedFrom, reservedTo;
    //methods
    //Constructor
    HotelCustomer(){
        super();
        this.reservations = new ArrayList<Integer>();
    }
    HotelCustomer(int id, String name, String email, int phone, String address, DateTime reservedfrom, DateTime reservedto){
        super(id, name, email, phone, address, reservedfrom, reservedto);
        this.reservations = new ArrayList<Integer>();
    }

    HotelCustomer(int id, String name, String email, int phone, String address, DateTime reservedfrom){
        super(id, name, email, phone, address, reservedfrom);
        this.reservations = new ArrayList<Integer>();
    }

    //setters
    void addReservation(int reservationId){ //expects dish id
        for (int id : this.reservations) if (reservationId == id) return; //if billid already exists simply return
        this.reservations.add(reservationId);
    }

    void removeReservation(int id){
        this.reservations.remove(Integer.valueOf(id));
    }

    //getters
    
    ArrayList<Integer> getReservations(){
        return this.reservations;
    }
}
