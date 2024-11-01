package com.operatoroverloaded.hotel.stores.logonstore;

import java.util.ArrayList;
import com.operatoroverloaded.hotel.models.Logon;

public class InMemoryLogonStore implements LogonStore {
    private ArrayList<Logon> logonData;

    // Constructor which also loads the data from the native library
    public InMemoryLogonStore() {
        this.logonData = new ArrayList<Logon>();
        this.load();
    }

    // Getter for the logonData
    public ArrayList<Logon> getLogonData(){
        return this.logonData;
    }
    
    // Native method declaration
    private native void saveLogon();
    private native void loadLogon();
    private native String hashString(String pwd, String salt);
    private native String getRandomSalt();


    // Load the native library
    static {
        // TODO: change the path to the library
        // System.loadLibrary("LogonCPP");
    }

    // Check the credibility of the email
    private boolean checkCredibilityOfEmail(String email){
        if(email.length() < 5) return true;
        if(!email.contains("@") || email.contains(" ")) return true;
        if(email.charAt(0) == '@' || email.charAt(email.length() - 1) == '@') return true;
        for(int i = 0; i < this.logonData.size(); i++){
            if(this.logonData.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    // Check the credibility of the password
    private boolean checkCredibilityOfPassword(String password){
        if(password.length() < 8) return true;
        if(password.charAt(0) == ' ' || password.charAt(password.length() - 1) == ' ') return true;
        return false;
    }

    // Update the access of the user
    private void updateAccess(int index, String access){
        this.logonData.get(index).setAccess(access);
    }

    // Update the email of the user
    private void updateEmail(int index, String email){
        checkCredibilityOfEmail(email);
        this.logonData.get(index).setEmail(email.trim().toLowerCase());
    }

    // Update the password of the user
    private void updatePassword(int index, String password){
        checkCredibilityOfPassword(password);
        this.logonData.get(index).setPassword(hashString(password, this.logonData.get(index).getSalt()));
    }

    // Add a new user
    @Override public void addNewUser(String access, String email, String password) {
        if(checkCredibilityOfEmail(email) || checkCredibilityOfPassword(password)){
            // System.out.println("Incorrect credentials");
            return;
        }

        // get random salt and hash the password using native method 
        String salt = getRandomSalt();
        String hashedPassword = hashString(password, salt);

        // create a new logon object and add it to the logonData
        Logon logon = new Logon(
            this.logonData.size() == 0? 1: this.logonData.get(logonData.size()-1).getRoleId() + 1, 
            access, 
            email, 
            hashedPassword, 
            salt
        );
        this.logonData.add(logon);
    }

    // delete a user by id
    @Override public void deleteUser(int id){
        for(int i = 0; i < this.logonData.size(); i++){
            if(this.logonData.get(i).getRoleId() == id){
                this.logonData.remove(i);
                return;
            }
        }   
        // System.out.println("User not found");
    }

    // try to login with the email and password
    @Override public boolean tryLogon(String email, String password){
        String hashedPassword = "";
        for(int i = 0; i < this.logonData.size(); i++){
            if(this.logonData.get(i).getEmail().equals(email)){
                if(hashedPassword == ""){
                    hashedPassword = hashString(password, this.logonData.get(i).getSalt());
                }
                if(this.logonData.get(i).getPassword().equals(hashedPassword)){
                    return true;
                }
            }
        }
        return false;
    }

    // update the user data with the given id
    @Override public void updateUser(int id, String access, String email, String password){
        show();
        for(int i = 0; i < this.logonData.size(); i++){
            if(this.logonData.get(i).getRoleId() == id){
                if(access != "" && access != this.logonData.get(i).getAccess()){
                    updateAccess(i, access);
                }
                if(email != "" && email != this.logonData.get(i).getEmail()){
                    updateEmail(i, email);
                }
                if(password != "" && password != this.logonData.get(i).getPassword()){
                    updatePassword(i, password);
                }
                return;
            }
        }
        // System.out.println("User not found");
    }

    // show the data
    private void show(){
        for(int i = 0; i < this.logonData.size(); i++){
            System.out.println(this.logonData.get(i).getRoleId() + " " + this.logonData.get(i).getEmail());
        }
    }

    // load the data from the native library
    @Override public void load(){
        this.loadLogon();
    }

    // save the data to the native library
    @Override public void save(){
        this.saveLogon();
    }
}
