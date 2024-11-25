package com.operatoroverloaded.hotel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operatoroverloaded.hotel.stores.billstore.BillStore;
import com.operatoroverloaded.hotel.stores.dishstore.DishStore;
import com.operatoroverloaded.hotel.stores.hotelcustomerstore.HotelCustomerStore;
import com.operatoroverloaded.hotel.stores.logonstore.LogonStore;
import com.operatoroverloaded.hotel.stores.restaurantcustomerstore.InMemoryRestaurantCustomerStore;
import com.operatoroverloaded.hotel.stores.restaurantcustomerstore.RestaurantCustomerStore;
import com.operatoroverloaded.hotel.stores.roomstore.InMemoryRoomStore;
import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;
import com.operatoroverloaded.hotel.stores.roomtypestore.InMemoryRoomTypeStore;
import com.operatoroverloaded.hotel.stores.roomtypestore.RoomTypeStore;
import com.operatoroverloaded.hotel.stores.tablestore.InMemoryTableStore;
import com.operatoroverloaded.hotel.stores.tablestore.TableStore;

@RestController
@RequestMapping("/api/load")
public class LoadSaveController {
    private final BillStore billStore;
    private final DishStore dishStore;
    private final HotelCustomerStore hotelCustomerStore;
    private final LogonStore logonStore;
    private final RestaurantCustomerStore restaurantCustomerStore;
    private final RoomStore roomStore;
    private final RoomTypeStore roomTypeStore;
    private final TableStore tableStore;

    public LoadSaveController() {
        this.billStore = BillStore.getInstance();
        this.dishStore = DishStore.getInstance();
        this.hotelCustomerStore = HotelCustomerStore.getInstance();
        this.logonStore = LogonStore.getInstance();
        this.restaurantCustomerStore = InMemoryRestaurantCustomerStore.getInstance();
        this.roomStore = InMemoryRoomStore.getInstance();
        this.roomTypeStore = InMemoryRoomTypeStore.getInstance();
        this.tableStore = InMemoryTableStore.getInstance();
    }
    @GetMapping("/loadall")
    public ResponseEntity<?> loadAll() {
        String totalError = "";
        try {
            hotelCustomerStore.loadFromFile();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        } 
        try {
            restaurantCustomerStore.loadFromFile();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        System.out.println("CHECK");
        try {
            billStore.load();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        try {
            roomStore.load();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        try {
            roomTypeStore.load();
            System.out.println("Room type data loaded successfully");
        }catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }  
        try {
            logonStore.load();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        try {
            dishStore.loadFromFile();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        try {
            tableStore.loadFromFile();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        if (totalError.isEmpty()) {
            return ResponseEntity.ok().body("All data loaded successfully");
        } else {
            return ResponseEntity.status(500).body(totalError);
        }
    }
    @GetMapping("/saveall")
    public ResponseEntity<?> saveAll() {
        String totalError = "";
        try {
            hotelCustomerStore.storeToFile();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        try {
            restaurantCustomerStore.storeToFile();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        try {
            roomStore.save();
        }catch(Exception e) {            
            totalError += e.getMessage() + "\n";
        }
        try {
            billStore.save();            
        }catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }        
        try {                        
            logonStore.save();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        try {
            roomTypeStore.save();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        try {
            dishStore.saveToFile();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        
        try {
            tableStore.saveToFile();
        } catch (Exception e) {
            totalError += e.getMessage() + "\n";
        }
        if (totalError.isEmpty()) {
            return ResponseEntity.ok().body("All data saved successfully");
        }   else {
            return ResponseEntity.status(500).body(totalError);
        }
    }
}


    