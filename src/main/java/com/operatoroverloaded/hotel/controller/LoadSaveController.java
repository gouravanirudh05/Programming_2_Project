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
        try {
            billStore.load();
        dishStore.loadFromFile();
        hotelCustomerStore.loadFromFile();
        logonStore.load();
        restaurantCustomerStore.loadFromFile();
        roomStore.load();
        roomTypeStore.load();
        tableStore.loadFromFile();
        return ResponseEntity.ok().body("All data loaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load data: " + e.getMessage());
        }
    }
    @GetMapping("/saveall")
    public ResponseEntity<?> saveAll() {
        try {
            billStore.save();
        dishStore.saveToFile();
        hotelCustomerStore.storeToFile();
        logonStore.save();
        restaurantCustomerStore.storeToFile();
        roomStore.save();
        roomTypeStore.save();
        tableStore.saveToFile();
        return ResponseEntity.ok().body("All data loaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load data: " + e.getMessage());
        }
    }
}


    