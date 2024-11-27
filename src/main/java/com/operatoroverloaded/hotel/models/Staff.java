package com.operatoroverloaded.hotel.models;

public class Staff{
    private int staffID;
    private String name;
    private float salary;
    private int phone;
    private String address;
    private String role;
    private DateTime workingFrom;
    private DateTime retiredOn;
    private String assignedTo;

    public Staff(String staffID, String name, String salary, String phone, String address, String role, String workingFrom, String retiredOn, String assignedTo) {
        this.staffID = Integer.parseInt(staffID);
        this.name = name;
        this.salary = Float.parseFloat(salary);
        this.phone = Integer.parseInt(phone);
        this.address = address;
        this.role = role;
        this.workingFrom = DateTime.parse(workingFrom);
        this.retiredOn = DateTime.parse(retiredOn);
        this.assignedTo = assignedTo;
    }

    public Staff(int staffID, String name, float salary, int phone, String address, String role, String workingFrom, String retiredOn, String assignedTo) {
        this.staffID = staffID;
        this.name = name;
        this.salary = salary;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.workingFrom = DateTime.parse(workingFrom);
        this.retiredOn = DateTime.parse(retiredOn);
        this.assignedTo = assignedTo;
    }

    public Staff(int staffID, String name, float salary, int phone, String address, String role, DateTime workingFrom, DateTime retiredOn, String assignedTo) {
        this.staffID = staffID;
        this.name = name;
        this.salary = salary;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.workingFrom = workingFrom;
        this.retiredOn = retiredOn;
        this.assignedTo = assignedTo;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public DateTime getWorkingFrom() {
        return workingFrom;
    }

    public void setWorkingFrom(DateTime workingFrom) {
        this.workingFrom = workingFrom;
    }

    public DateTime getRetiredOn() {
        return retiredOn;
    }

    public void setRetiredOn(DateTime retiredOn) {
        this.retiredOn = retiredOn;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String toString() {
        return "Staff{" +
                "staffID=" + staffID +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", workingFrom=" + workingFrom +
                ", retiredOn=" + retiredOn +
                ", assignedTo=" + assignedTo +
                '}';
    }

    public void assignToRoom(){
        this.assignedTo = "Room";
    }

    public void assignToRestaurant(){
        this.assignedTo = "Restaurant";
    }

    public void retire(DateTime retiredOn){
        this.retiredOn = retiredOn;
    }

    public void changeRole(String role){
        this.role = role;
    }

    public void changeSalary(float salary){
        this.salary = salary;
    }

    public void changeAddress(String address){
        this.address = address;
    }

    public void changePhone(int phone){
        this.phone = phone;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeWorkingFrom(DateTime workingFrom){
        this.workingFrom = workingFrom;
    }

    public void changeAssignedTo(String assignedTo){
        this.assignedTo = assignedTo;
    }

    public void changeRetiredOn(DateTime retiredOn){
        this.retiredOn = retiredOn;
    }

    public void changeStaffID(int staffID){
        this.staffID = staffID;
    }

    // Method to calculate years of service
    public int getYearsOfService() {
        DateTime current = DateTime.getCurrentTime();
        return workingFrom.dateDifference(current) / 365;
    }

    // Method to check if staff is currently employed
    public boolean isCurrentlyEmployed() {
        DateTime current = DateTime.getCurrentTime();
        return retiredOn == null || current.compareTo(retiredOn) < 0;
    }

    // Method to give a raise to the staff
    public void giveRaise(float percentage) {
        if (percentage > 0) {
            salary += salary * (percentage / 100);
        }
    }

    // Method to assign the staff to a new location
    public void reassign(String newAssignment) {
        assignedTo = newAssignment;
    }

    // Method to print staff details
    public String getStaffDetails() {
        return String.format(
            "Staff ID: %d\nName: %s\nRole: %s\nAssigned To: %s\nSalary: %.2f\nYears of Service: %d",
            staffID, name, role, assignedTo, salary, getYearsOfService());
    }

    // Method to check if the staff is eligible for retirement (e.g., 60 years of age or 30 years of service)
    public boolean isEligibleForRetirement() {
        return getYearsOfService() >= 30;
    }

    // Method to calculate days until retirement (if retirement date is set)
    public int daysUntilRetirement() {
        if (retiredOn == null) {
            return -1; // Indicates no retirement date is set
        }
        DateTime current = DateTime.getCurrentTime();
        return current.dateDifference(retiredOn);
    }

    // Method to check if staff is assigned to a specific location
    public boolean isAssignedTo(String location) {
        return assignedTo.equals(location);
    }
}