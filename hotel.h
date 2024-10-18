#ifndef HOTEL_MANAGEMENT_SYSTEM_H
#define HOTEL_MANAGEMENT_SYSTEM_H

#include <string>
#include <vector>
#include <list>

using namespace std;


class DateTime {
public:
    int year, month, day, hour, minute, second;

    DateTime getCurrentTime();
    bool isLeap() const;
    bool isValid() const;
    bool hasPastCurrentTime() const;
    int dateDifference(const DateTime&) const;
    int timeDifference(const DateTime&) const;
    void incrementDay(int);
    void incrementTime(int);
    string getTimeString() const;
    string getDateString() const;
};


class RoomType {
public:
    string roomTypeID;
    string name;
    float tariff;
    vector<string> amenities;
};


class Room {
public:
    string roomID;
    RoomType* roomType;
    int capacity;
    DateTime housekeepingLast;

    
};


class Table {
public:
    int tableID;
    int capacity;
    bool occupied;

    
};


class Dish {
public:
    int dishID;
    string name;
    float price;
    string dishType;  

    
};


class Restaurant {
public:
    vector<Dish> dishes;
    vector<Table> tables;

    
};


class Staff {
public:
    int staffID;
    string name;
    float salary;
    string phone;
    string address;
    string role;
    DateTime workingFrom;
    DateTime retiredOn;
    string assignedTo;  

    
};


class Role {
public:
    int roleID;
    string access;  
    string email;
    string encryptedPassword;

    
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

    
};


class Reservation {
public:
    Room room;
    DateTime reservedFrom;
    DateTime reservedTo;
    float tariff;
    Bill bill;

    
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

    void method() override;
};


class HotelCustomer : public Customer {
public:
    list<Reservation*> reservations;

    void method() override;
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

    
};

#endif  
