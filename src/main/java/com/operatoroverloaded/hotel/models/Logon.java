package com.operatoroverloaded.hotel.models;
import java.io.Serializable;

public class Logon implements Serializable {
    private int roleId = 0;
    enum AccessLevel {Room, Restaurant, Admin, None}    
    private AccessLevel access;
    private String email;
    private String password;
    private String salt;

    public Logon(int roleId, String email, String password){
        this.roleId = roleId;
        this.access = AccessLevel.None;
        this.email = email.trim().toLowerCase();
        this.password = password;
    }

    public Logon(int roleID, String access, String email, String password) {
        this.roleId = roleID;
        this.access = access.equalsIgnoreCase("Admin") ? AccessLevel.Admin : 
                      access.equalsIgnoreCase("Room") ? AccessLevel.Room : 
                      access.equalsIgnoreCase("Restaurant") ? AccessLevel.Restaurant : 
                      AccessLevel.None;
        this.email = email.trim().toLowerCase();
        this.password = password;
    }

    public Logon(int roleId, String access, String email, String password, String salt) {
        this.roleId = roleId;
        this.access = access.equalsIgnoreCase("Admin") ? AccessLevel.Admin : 
                      access.equalsIgnoreCase("Room") ? AccessLevel.Room : 
                      access.equalsIgnoreCase("Restaurant") ? AccessLevel.Restaurant : 
                      AccessLevel.None;
        this.email = email.trim().toLowerCase();
        this.password = password;
        this.salt = salt;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAccess() {
        return "" + access;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSalt(){
        return this.salt;
    }

    public void setAccess(String access){
        this.access = access.equalsIgnoreCase("Admin") ? AccessLevel.Admin : 
                      access.equalsIgnoreCase("Room") ? AccessLevel.Room : 
                      access.equalsIgnoreCase("Restaurant") ? AccessLevel.Restaurant : 
                      AccessLevel.None;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
