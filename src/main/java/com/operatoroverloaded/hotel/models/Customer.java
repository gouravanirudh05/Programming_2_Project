import java.sql.Date;
import java.util.ArrayList;

abstract public class Customer {
    //Attributes
    protected int customerId;
    protected String name;
    protected String email;
    protected int phone;
    protected String address;
    protected float bill_amt;
    protected float bill_payed;
    protected float bill_left;
    protected ArrayList<Integer> bills;
    DateTime reservedFrom, reservedTo;

    //methods
    //Constructor
    Customer(int id, String name, String email, int phone, String address){
        this.customerId = id; //Assuming that id will be changed in the database for storing..
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bill_amt = 0;
        this.bill_left = 0;
        this.bill_payed = 0;
        this.bills = new ArrayList<Integer>();
        reservedFrom = new DateTime();
        reservedTo = new DateTime();
    }
    Customer(int id, String name, String email, int phone, String address, DateTime from, DateTime to){
        this.customerId = id; //Assuming that id will be changed in the database for storing..
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bill_amt = 0;
        this.bill_left = 0;
        this.bill_payed = 0;
        this.bills = new ArrayList<Integer>();
        reservedFrom = from;
        reservedTo = to;
    }
    Customer(int id, String name, String email, int phone, String address, DateTime from){
        this.customerId = id; //Assuming that id will be changed in the database for storing..
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bill_amt = 0;
        this.bill_left = 0;
        this.bill_payed = 0;
        this.bills = new ArrayList<Integer>();
        reservedFrom = from;
        reservedTo = new DateTime();
    }

    Customer() {
        this.customerId = -1;
        this.name = "";
        this.email = "";
        this.phone = 0;
        this.address = "";
        this.bill_amt = 0;
        this.bill_left = 0;
        this.bill_payed = 0;
        this.bills = new ArrayList<Integer>();
        reservedFrom = new DateTime();
        reservedTo = new DateTime();
    }

    //setter methods
    void setCustomerId(int id){
        this.customerId = id;
    }
    void setName(String name){
        this.name = name;
    }
    void setEmail(String name){
        this.email = name;
    }
    void setPhone(int ph){
        this.phone = ph;
    }
    void setAddress(String name){
        this.address = name;
    }
    void setBillLeft(float billleft){
        this.bill_left = billleft;
        this.bill_payed = this.bill_amt - billleft;
    }
    void setBillPayed(float billpayed){
        this.bill_payed = billpayed;
        this.bill_left = this.bill_amt - billpayed;
    }
    private void setBillAmount(float billamt){ //when you add bill, it automatically sets the bill amount thats why it is a private method
        this.bill_amt = billamt;
        this.bill_left = billamt-this.bill_payed;
    }
    void addBill(int billid){ //expects bill id
        for (int id : this.bills) if (id == billid) return; //if billid already exists simply return
        this.bills.add(billid);
        Bill bill = BillStore.getBill(billid);
        setBillAmount(bill_amt+bill.getAmount());
    }

    void addBill(Bill bill){ //expects bill object
        for (int id : this.bills) if (id == bill.getBillId) return; //if bill already exists simply return
        bills.add(bill.getBillId);
        setBillAmount(this.bill_amt+bill.getAmount());
    }

    void deleteBill(int billid){ //expects bill id
        Bill b = BillStore.getBill(billid);
        for (int id : bills) if (id == billid) {
            setBillAmount(bill_amt-b.getAmount());
        } 
        bills.remove(Integer.valueOf(billid));
    }

    void setReservedFrom(DateTime from){
        this.reservedFrom = from;
    }
    
    void setReservedTo(DateTime to){
        this.reservedTo = to;
    }

    //getter methods
    int getCustomerId(){
        return this.customerId;
    }
    String getName(){
        return this.name;
    }
    String getAddress(){
        return this.address;
    }
    String getEmail(){
        return this.email;
    }
    int getPhone(){
        return this.phone;
    }
    float getBillAmount(){
        return this.bill_amt;
    }
    float getBillPayed(){
        return this.bill_payed;
    }
    float getBillLeft(){
        return this.bill_left;
    }
    ArrayList<Integer> getBills(){ //Returns an ArrayList of all the Billid's
        return this.bills;
    }
    DateTime getReservedFrom(){
        return this.reservedFrom;
    }
    DateTime getReservedTo(){
        return this.reservedTo;
    }
}
