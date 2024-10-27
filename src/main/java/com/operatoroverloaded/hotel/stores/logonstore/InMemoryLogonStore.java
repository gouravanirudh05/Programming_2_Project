package com.operatoroverloaded.hotel.stores.logonstore;

import com.operatoroverloaded.hotel.models.Logon;
import java.util.ArrayList;

public class InMemoryLogonStore implements LogonStore {
    private ArrayList<Logon> logonData;

    public InMemoryLogonStore() {
        this.logonData = new ArrayList<Logon>();
        load();
    }

    public ArrayList<Logon> getLogonData(){
        return this.logonData;
    }

    // Native method declaration
    private native void saveLogin();
    private native void loadLogin();
    private native String hashString(String pwd, String salt);
    private native String getRandomSalt();


    // Load the native library
    static {
        // TODO: Replace the path with the path to the shared library
        // System.loadLibrary("LogonCPP");
    }

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

    private boolean checkCredibilityOfPassword(String password){
        if(password.length() < 8) return true;
        if(password.charAt(0) == ' ' || password.charAt(password.length() - 1) == ' ') return true;
        return false;
    }

    private void updateAccess(int index, String access){
        this.logonData.get(index).setAccess(access);
    }

    private void updateEmail(int index, String email){
        checkCredibilityOfEmail(email);
        this.logonData.get(index).setEmail(email.trim().toLowerCase());
    }

    private void updatePassword(int index, String password){
        checkCredibilityOfPassword(password);
        this.logonData.get(index).setPassword(hashString(password, this.logonData.get(index).getSalt()));
    }

    public void addNewUser(String access, String email, String password) {
        if(checkCredibilityOfEmail(email) || checkCredibilityOfPassword(password)){
            System.out.println("Incorrect credentials");
            return;
        }
        String salt = getRandomSalt();
        String hashedPassword = hashString(password, salt);

        Logon logon = new Logon(
            this.logonData.size() == 0? 1: this.logonData.get(logonData.size()-1).getRoleId() + 1, 
            access, 
            email, 
            hashedPassword, 
            salt
        );
        this.logonData.add(logon);
    }

    public void deleteUser(int id){
        for(int i = 0; i < this.logonData.size(); i++){
            if(this.logonData.get(i).getRoleId() == id){
                this.logonData.remove(i);
                return;
            }
        }   
        System.out.println("User not found");
    }

    public boolean tryLogin(String email, String password){
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

    public void updateUser(int id, String access, String email, String password){
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

    private void show(){
        for(int i = 0; i < this.logonData.size(); i++){
            System.out.println(this.logonData.get(i).getRoleId() + " " + this.logonData.get(i).getEmail());
        }
    }

    public void load(){
        this.loadLogin();
    }

    public void save(){
        this.saveLogin();
    }
}
