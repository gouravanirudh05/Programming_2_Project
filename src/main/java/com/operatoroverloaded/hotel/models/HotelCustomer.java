import java.util.ArrayList;

public class HotelCustomer extends Customer{

    private ArrayList<Integer> reservations;
    DateTime reservedFrom, reservedTo;

//-------------------------------------------------------------------Constructors--------------------------------------------------------------------------------------------------


    HotelCustomer(){
        super();
        this.reservations = new ArrayList<Integer>();
    }
    HotelCustomer(int id, String name, String email, String phone, String address, DateTime reservedfrom, DateTime reservedto){
        super(id, name, email, phone, address, reservedfrom, reservedto);
        this.reservations = new ArrayList<Integer>();
    }

    HotelCustomer(String name, String email, String phone, String address, DateTime reservedfrom, DateTime reservedto){
        super(name, email, phone, address, reservedfrom, reservedto);
        this.reservations = new ArrayList<Integer>();
    }



//-------------------------------------------------------------------Setter Methods--------------------------------------------------------------------------------------------------


    void addReservation(int reservationId){ //expects dish id
        for (int id : this.reservations) if (reservationId == id) return; //if billid already exists simply return
        this.reservations.add(reservationId);
    }

    void removeReservation(int id){
        this.reservations.remove(Integer.valueOf(id));
    }


//-------------------------------------------------------------------Getter Methods--------------------------------------------------------------------------------------------------
    

    ArrayList<Integer> getReservations(){
        return this.reservations;
    }


//-------------------------------------------------------------------Other Constructors--------------------------------------------------------------------------------------------------


    HotelCustomer(int id, String name, String email, String phone, String address, DateTime reservedfrom){
        super(id, name, email, phone, address, reservedfrom);
        this.reservations = new ArrayList<Integer>();
    }

    HotelCustomer(String name, String email, String phone, String address, DateTime reservedfrom){
        super(name, email, phone, address, reservedfrom);
        this.reservations = new ArrayList<Integer>();
    }

    HotelCustomer(String name, String email, String phone, String address){
        super(name, email, phone, address);
        this.reservations = new ArrayList<Integer>();
    }

}
