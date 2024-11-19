package com.operatoroverloaded.hotel.stores.logonstore;

import com.operatoroverloaded.hotel.models.Logon;

public abstract class LogonStore {
    public static LogonStore logonStore = null;
    public static LogonStore getInstance(){
        return logonStore;
    }
    public static void setInstance(LogonStore logonStore){
        LogonStore.logonStore = logonStore;
    }
    public abstract Logon addNewUser(String access, String email, String password);
    public abstract void deleteUser(int id);
    public abstract Logon tryLogon(String email, String password);
    public abstract Logon updateUser(int id, String access, String email, String password);
    public abstract void load();
    public abstract void save();
}
