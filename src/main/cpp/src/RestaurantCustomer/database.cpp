#include<bits/stdc++.h>
#include<fstream>
#include<sstream>
using namespace std;

// format of storing:
// id name email phone address bill_amt bill_payed bill_left number_of_bills <all_bill_ids> <year month day hour min sec> <year month day hour min sec> no_of_dishs <all_dish_ids> tableId serverId

class DateTime {
public:
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int second;

    // Constructor
    DateTime(int yr, int mo, int d, int h, int min, int sec)
        : year(yr), month(mo), day(d), hour(h), minute(min), second(sec) {}

    // Default Constructor
    DateTime() : year(0), month(0), day(0), hour(0), minute(0), second(0) {}
};

class Customer {
protected:
    int customerId;
    string name;
    string email;
    string phone;
    string address;
    double bill_amt;
    double bill_payed;
    double bill_left;
    vector<int> bills; 
    DateTime reservedFrom;
    DateTime reservedTo;

public:
    // Constructors
    Customer() : customerId(0), bill_amt(0), bill_payed(0), bill_left(0) {}

    Customer(int id, string name, string email, string phone, string address, double billAmt, double billPayed, double billLeft, DateTime reservedFrom, DateTime reservedTo)
        : customerId(id), name(name), email(email), phone(phone), address(address), bill_amt(billAmt), bill_payed(billPayed), bill_left(billLeft), reservedFrom(reservedFrom), reservedTo(reservedTo) {}

    Customer(int id, string name, string email, string phone, string address, double billAmt, double billPayed, double billLeft, DateTime reservedFrom, DateTime reservedTo, vector<int> bills)
        : customerId(id), name(name), email(email), phone(phone), address(address), bill_amt(billAmt), bill_payed(billPayed), bill_left(billLeft), reservedFrom(reservedFrom), reservedTo(reservedTo), bills(bills) {}


    // Getters
    int getCustomerId() const { return customerId; }
    string getName() const { return name; }
    string getEmail() const { return email; }
    string getPhone() const { return phone; }
    string getAddress() const { return address; }
    double getBillAmt() const { return bill_amt; }
    double getBillPayed() const { return bill_payed; }
    double getBillLeft() const { return bill_left; }
    vector<int> getBills() const { return bills; }
    DateTime getReservedFrom() const { return reservedFrom; }
    DateTime getReservedTo() const { return reservedTo; }

    // Setters
    void setCustomerId(int id) { customerId = id; }
    void setName(string name) { this->name = name; }
    void setEmail(string email) { this->email = email; }
    void setPhone(string phone) { this->phone = phone; }
    void setAddress(string address) { this->address = address; }
    void setBillAmt(double amount) { bill_amt = amount; }
    void setBillPayed(double payed) { bill_payed = payed; }
    void setBillLeft(double left) { bill_left = left; }
    void setReservedFrom(DateTime from) { reservedFrom = from; }
    void setReservedTo(DateTime to) { reservedTo = to; }

    void display() const {
    cout << "Customer Details:" << endl;
    cout << "ID: " << customerId << ", Name: " << name << ", Email: " << email << endl;
    cout << "Phone: " << phone << ", Address: " << address << endl;
    cout << "Bill Amount: $" << bill_amt << ", Paid: $" << bill_payed << ", Left: $" << bill_left << endl;
    }
};

// Derived RestaurantCustomer Class
class RestaurantCustomer : public Customer {
private:
    vector<int> dishes;
    int tableId;
    int serverId;

public:
    // Constructors
    RestaurantCustomer() : tableId(0), serverId(0) {}

    RestaurantCustomer(int id, string name, string email, string phone, string address, int tableId, int serverId, DateTime reservedFrom, DateTime reservedTo)
        : Customer(id, name, email, phone, address, 0, 0, 0, reservedFrom, reservedTo), tableId(tableId), serverId(serverId) {}

    RestaurantCustomer(int id, string name, string email, string phone, string address, double bill_amt, double bill_payed, double bill_left, DateTime reservedFrom, DateTime reservedTo, vector<int> bills, vector<int> dishes, int tableId, int serverId)
        : Customer(id, name, email, phone, address, bill_amt, bill_payed, bill_left, reservedFrom, reservedTo, bills), dishes(dishes), tableId(tableId), serverId(serverId) {}

    // Getters
    vector<int> getDishes() const { return dishes; }
    int getTableId() const { return tableId; }
    int getServerId() const { return serverId; }

    // Setters
    void addDish(int dishId) { dishes.push_back(dishId); }
    void removeDish(int dishId) {
        dishes.erase(remove(dishes.begin(), dishes.end(), dishId), dishes.end());
    }
    void setTableId(int id) { tableId = id; }
    void setServerId(int id) { serverId = id; }
};

// Derived HotelCustomer Class
class HotelCustomer : public Customer {
private:
    vector<int> reservations;

public:
    // Constructors
    HotelCustomer() {}

    HotelCustomer(int id, string name, string email, string phone, string address, DateTime reservedFrom, DateTime reservedTo)
        : Customer(id, name, email, phone, address, 0, 0, 0, reservedFrom, reservedTo) {}

    HotelCustomer(int id, string name, string email, string phone, string address, double bill_amt, double bill_payed, double bill_left, DateTime reservedFrom, DateTime reservedTo, vector<int> bills, vector<int> reservations)
        : Customer(id, name, email, phone, address, bill_amt, bill_payed, bill_left, reservedFrom, reservedTo, bills), reservations(reservations) {}

    // Getters
    vector<int> getReservations() const { return reservations; }

    // Setters
    void addReservation(int reservationId) { reservations.push_back(reservationId); }
    void removeReservation(int reservationId) {
        reservations.erase(remove(reservations.begin(), reservations.end(), reservationId), reservations.end());
    }
};

vector<RestaurantCustomer> loadFromFile(){ //filename: RestaurantCustomerStore.txt
    ifstream infile;
    vector<RestaurantCustomer> v = {};
    infile.open("RestaurantCustomerStore.txt", ios::out);
    string customerData = "";
    while (getline(infile, customerData)){
        istringstream ss(customerData);
        int customerId;
        string name;
        string email;
        string phone;
        string address;
        double bill_amt;
        double bill_payed;
        double bill_left;
        ss >> customerId >> name >> email >> phone >> address >> bill_amt >> bill_payed >> bill_left;
        int bills_size;
        vector<int> bills = {}; 
        ss >> bills_size;
        while (bills_size--){
            int x;
            ss >> x;
            bills.push_back(x);
        }
        int year, month, day, hour, min, sec;
        ss >> year >> month >> day >> hour >> min >> sec;
        DateTime reservedFrom(year, month, day, hour, min, sec);
        ss >> year >> month >> day >> hour >> min >> sec;
        DateTime reservedTo(year, month, day, hour, min, sec);
        int dishes_size;
        vector<int> dishes;
        ss >> dishes_size;
        while (dishes_size--){
            int x;
            ss >> x;
            dishes.push_back(x);
        }
        int tableId, serverId;
        ss >> tableId >> serverId;
        RestaurantCustomer customer(customerId, name, email, phone, address, bill_amt, bill_payed, bill_left, reservedFrom, reservedTo, bills, dishes, tableId, serverId);
        v.push_back(customer);
    }
    infile.close();
    return v;
}

void storeToFile(vector<RestaurantCustomer> v){
    ofstream outfile;
    outfile.open("HotelCustomerStore.txt");
    for (auto customer: v){
        outfile << customer.getCustomerId() << " "<< customer.getName() << " "<< customer.getEmail() << " "<< customer.getPhone() << " "<< customer.getAddress()<< " " << customer.getBillAmt() << " "<< customer.getBillPayed() << " "<< customer.getBillLeft() << " ";
        vector<int> v = customer.getBills();
        outfile << v.size() << " ";
        for (int i: v) outfile << i << " ";
        DateTime from = customer.getReservedFrom();
        DateTime to = customer.getReservedTo();
        outfile << from.year << " "<< from.month << " "<< from.day << " "<< from.hour << " "<< from.minute << " "<< from.second<< " ";
        outfile << to.year << " "<< to.month << " "<< to.day << " "<< to.hour << " "<< to.minute << " "<< to.second<< " ";
        v = customer.getDishes();
        outfile << v.size() << " ";
        for (int i: v) outfile << i << " ";
        int tableId, serverId;
        tableId = customer.getTableId();
        serverId = customer.getServerId();
        outfile << tableId << " "<< serverId << endl;
    }
    outfile.close();
}


