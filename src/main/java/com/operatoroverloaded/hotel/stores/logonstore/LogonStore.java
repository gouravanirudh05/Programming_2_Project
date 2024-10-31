package com.operatoroverloaded.hotel.stores.logonstore;

public interface LogonStore {
    public void addNewUser(String access, String email, String password);
    public void deleteUser(int id);
    public boolean tryLogin(String email, String password);
    public void updateUser(int id, String access, String email, String password);
    public void load();
    public void save();
}
