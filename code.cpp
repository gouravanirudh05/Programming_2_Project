#include<iostream>
#include<algorithm>
#include<string>
#include<vector>
#include<ctime>
#include<sstream>
#include<iomanip>
#include<list>
using namespace std;

class DateTime 
{
public:
    int day, month, year, hour, minute, second;
    DateTime getCurrentTime() {
        time_t now = time(0);
        tm* localTime = localtime(&now);
        day = localTime->tm_mday;
        month = localTime->tm_mon + 1;
        year = localTime->tm_year + 1900;
        hour = localTime->tm_hour;
        minute = localTime->tm_min;
        second = localTime->tm_sec;
        return *this;
    }
    bool isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    bool isValid() {
        if (month < 1 || month > 12)
            return false;
        if (day < 1)
            return false;
        if (month == 2 && day > 28 + isLeapYear(year))
            return false;
        if (month == 4 || month == 6 || month == 9 || month == 11)
            return day <= 30;
        return true;
    }
    bool hasPastCurrentTime() {
        DateTime currentTime = getCurrentTime();
        if (year < currentTime.year) return true;
        if (year == currentTime.year && month < currentTime.month) return true;
        if (year == currentTime.year && month == currentTime.month && day < currentTime.day) return true;
        if (year == currentTime.year && month == currentTime.month && day == currentTime.day && hour < currentTime.hour) return true;
        if (year == currentTime.year && month == currentTime.month && day == currentTime.day && hour == currentTime.hour && minute < currentTime.minute) return true;
        return (year == currentTime.year && month == currentTime.month && day == currentTime.day && hour == currentTime.hour && minute == currentTime.minute && second < currentTime.second);
    }

    string getTimeString() {
        stringstream ss;
        ss << setw(2) << setfill('0') << hour << ":" << setw(2) << setfill('0') << minute << ":" << setw(2) << setfill('0') << second;
        return ss.str();
    }

    string getDateString() {
        stringstream ss;
        ss << setw(2) << setfill('0') << day << "/" << setw(2) << setfill('0') << month << "/" << setw(4) << setfill('0') << year;
        return ss.str();
    }
};

class RoomType {
public:
    string name;
    double price;
    string roomtypeid;
    vector<string> amenities;

    RoomType(string n, double p, string id)
    {
        this->name = n;
        this->price = p;
        this->roomtypeid = id;
    }
};

class Room 
{
public:
    string roomid;
    RoomType* roomtype;
    int capacity;
    bool isBooked;
    DateTime checkin;
    DateTime checkout;
    DateTime housekeepingLast;
    Room(string id, RoomType* type, int cap)
    {
        this->roomid = id;
        this->roomtype = type;
        this->capacity = cap;
        this->isBooked = false;
    }
    void bookRoom(DateTime& in,DateTime& out) 
    {
        if (!isBooked) 
        {
            checkin = in;
            checkout = out;
            isBooked = true;
        } 
        else 
        {
            cout << "Room is already booked!" << endl;
        }
    }

    void vacateRoom() 
    {
        isBooked = false;
    }
};
class Table 
{
public:
    int tableID;
    int capacity;
    bool occupied;
    Table(int id, int cap)
    {
        this->tableID = id;
        this->capacity = cap;
        this->occupied = false;
    }
};
class Dish 
{
public:
    int dishID;
    string name;
    double price;
    string dishType;
    Dish(int id, string n, double p, string type)
    {
        this->dishID = id;
        this->name = n;
        this->price = p;
        this->dishType = type;
    }
};

class Restaurant 
{
public:
    vector<Dish> dishes;
    vector<Table> tables;

    void addDish(Dish dish) 
    {
        dishes.push_back(dish);
    }
    void deleteDish(Dish dish)
    {
        for (auto it = dishes.begin(); it!= dishes.end(); ++it) {
            if (it->dishID == dish.dishID) {
                dishes.erase(it);
                return;
            }
        }
    }
    void addTable(Table table) 
    {
        tables.push_back(table);
    }
    void deleteTable(Table table)
    {
        for (auto it = tables.begin(); it!= tables.end(); ++it) {
            if (it->tableID == table.tableID) {
                tables.erase(it);
                return;
            }
        }
    }
};
class Staff 
{
private:
    int staffID;
    string name;
    double salary;
    string phone;
    string address;
    string role;
    DateTime workingFrom;
    DateTime retiredOn;
    string assignedTo;
public:
    Staff(int id, string n, double sal, string ph, string add, string r, DateTime wf, DateTime ro, string a)
    {
        this->staffID = id;
        this->name = n;
        this->salary = sal;
        this->phone = ph;
        this->address = add;
        this->role = r;
        this->workingFrom = wf;
        this->retiredOn = ro;
        this->assignedTo = a;
    }
    Staff()
    {
        this->staffID = 0;
        this->name = "";
        this->salary = 0;
        this->phone = "";
        this->address = "";
        this->role = "";
        this->workingFrom.getCurrentTime();
        this->retiredOn.year = 0;
        this->assignedTo = "";
    }
    Staff(int id, string n, double sal, string ph, string add, string r)
    {
        this->staffID = id;
        this->name = n;
        this->salary = sal;
        this->phone = ph;
        this->address = add;
        this->role = r;
        this->workingFrom.getCurrentTime();
        this->retiredOn.year = 0;
        this->assignedTo = "";
    }
    Staff(int id, string n, float sal, string ph) 
    {
        this->staffID = id;
        this->name = n;
        this->salary = sal;
        this->phone = ph;
        this->address = "";
        this->role = "";
        this->workingFrom.getCurrentTime();
        this->retiredOn.year = 0;
        this->assignedTo = "";
    }
    //get methods
    int getStaffID() 
    { 
        return staffID; 
    }
    string getName()
    {
        return name;
    }
    double getSalary()
    {
        return salary;
    }
    string getPhone()
    {
        return phone;
    }
    string getAddress()
    {
        return address;
    }
    string getRole()
    {
        return role;
    }
    DateTime getWorkingFrom()
    {
        return workingFrom;
    }
    DateTime getRetiredOn()
    {
        return retiredOn;
    }
    string getAssignedTo()
    {
        return assignedTo;
    }
    //set methods
    void setStaffID(int id)
    {
        staffID = id;
    }
    void setName(string n)
    {
        name = n;
    }
    void setSalary(double sal)
    {
        salary = sal;
    }
    void setPhone(string ph)
    {
        phone = ph;
    }
    void setAddress(string add)
    {
        address = add;
    }
    void setRole(string r)
    {
        role = r;
    }
    void setWorkingFrom(DateTime wf)
    {
        workingFrom = wf;
    }
    void setRetiredOn(DateTime ro)
    {
        retiredOn = ro;
    }
    void setAssignedTo(string a)
    {
        assignedTo = a;
    }
    bool isRetired()
    {
        return retiredOn.year!= 0;
    }
    void promote(string newRole, DateTime retirementDate)
    {
        role = newRole;
        retiredOn = retirementDate;
    }
    void demote(string newRole)
    {
        role = newRole;
        retiredOn.year = 0;
    }
    void transfer(string newAssignedTo)
    {
        assignedTo = newAssignedTo;
    }
    void updateAddress(string newAddress)
    {
        address = newAddress;
    }
    void updatePhone(string newPhone)
    {
        phone = newPhone;
    }
    void updateSalary(double newSalary)
    {
        salary = newSalary;
    }
    void updateWorkingFrom(DateTime newWorkingFrom)
    {
        workingFrom = newWorkingFrom;
    }
    void updateRetiredOn(DateTime newRetiredOn)
    {
        retiredOn = newRetiredOn;
    }
    void updateAssignedTo(string newAssignedTo)
    {
        assignedTo = newAssignedTo;
    }
    void updateName(string newName)
    {
        name = newName;
    }
    void updateRole(string newRole)
    {
        role = newRole;
    }
    void updateStaffID(int newStaffID)
    {
        staffID = newStaffID;
    }
};

class Role 
{
private:
    int roleID;
    string access;
    string email;
    string encryptedPassword;
public:

    Role(int id, string acc, string mail) : roleID(id), access(acc), email(mail) {}
};

class Bill {
public:
    int billID;
    float amount;
    vector<string> purchased;
    vector<float> purchasedVal;
    bool payed;
    DateTime generatedOn;
    DateTime payedOn;

    Bill(int id, float amt) : billID(id), amount(amt), payed(false) {}

    void addItem(string item, float price) {
        purchased.push_back(item);
        purchasedVal.push_back(price);
        amount += price;
    }

    void markAsPaid() {
        payed = true;
        payedOn.getCurrentTime();
    }
};

class Reservation {
public:
    Room* room;
    DateTime reservedFrom;
    DateTime reservedTo;
    float tariff;
    Bill bill;

    Reservation(Room* r, const DateTime& from, const DateTime& to, float t) : room(r), reservedFrom(from), reservedTo(to), tariff(t), bill(0, t) {}

    void finalizeBill() {
        bill.markAsPaid();
    }
};

class Customer {
public:
    int customerID;
    string name;
    string email;
    string phone;
    string address;
    float billPayed;
    float billLeft;
    vector<Bill> bills;

    virtual void method() = 0;
};

class RestaurantCustomer : public Customer {
public:
    vector<Dish> dishes;
    Table table;
    Staff server;
    DateTime reservedFrom;
    DateTime reservedTo;

    void method() override {
        cout << "Restaurant customer order handling" << endl;
    }
};

class HotelCustomer : public Customer {
public:
    list<Reservation*> reservations;

    void method() override {
        cout << "Hotel customer reservation handling" << endl;
    }
};

class Hotel {
public:
    string name;
    string description;
    string location;
    string city;
    string state;
    int rating;
    int phone;
    Restaurant restaurant;
    vector<Room> rooms;
    vector<RoomType> roomTypes;
    vector<Staff> staff;
    vector<Reservation> reservations;
    vector<Role> accessRoles;

    Hotel(string n, string desc, string loc, string cty, string st, int rate, int ph) 
    : name(n), description(desc), location(loc), city(cty), state(st), rating(rate), phone(ph) {}

    void addRoom(Room room) {
        rooms.push_back(room);
    }

    void addStaff(Staff s) {
        staff.push_back(s);
    }

    void makeReservation(Room* room, DateTime from, DateTime to, float tariff) {
        if (!room->isBooked) {
            reservations.push_back(Reservation(room, from, to, tariff));
            room->bookRoom(from, to);
        } else {
            cout << "Room already booked." << endl;
        }
    }
};

int main() {
    DateTime current;
    current.getCurrentTime();

    // Sample usage of Hotel Management System
    RoomType deluxe("Deluxe", 200.0, "RT001");
    RoomType suite("Suite", 400.0, "RT002");

    Room room1("R001", &deluxe, 2);
    Room room2("R002", &suite, 3);

    Hotel hotel("Grand Hotel", "Luxury Stay", "Main St", "Metropolis", "State", 5, 123456789);

    hotel.addRoom(room1);
    hotel.addRoom(room2);

    DateTime checkin, checkout;
    checkin.getCurrentTime();
    checkout = checkin;
    checkout.day += 2; // Adding 2 days to checkout

    hotel.makeReservation(&room1, checkin, checkout, 400.0);
    
    return 0;
}
