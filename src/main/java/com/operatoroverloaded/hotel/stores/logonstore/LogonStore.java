package com.operatoroverloaded.hotel.stores.logonstore;

public interface LogonStore {
    public static LogonStore logonStore = null;
    public static LogonStore getInstance(){
        return logonStore;
    }
    public static void setInstance(LogonStore logonStore){
        logonStore = logonStore;
    }
    public void addNewUser(String access, String email, String password);
    public void deleteUser(int id);
    public boolean tryLogon(String email, String password);
    public void updateUser(int id, String access, String email, String password);
    public void load();
    public void save();
}
