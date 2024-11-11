package com.operatoroverloaded.hotel.stores.logonstore;

import com.operatoroverloaded.hotel.models.Logon;

public interface LogonStore {
    public static LogonStore logonStore = null;
    public static LogonStore getInstance(){
        return logonStore;
    }
    public static void setInstance(LogonStore logonStore){
        logonStore = logonStore;
    }
    public Logon addNewUser(String access, String email, String password);
    public void deleteUser(int id);
    public Logon tryLogon(String email, String password);
    public Logon updateUser(int id, String access, String email, String password);
    public void load();
    public void save();
}
