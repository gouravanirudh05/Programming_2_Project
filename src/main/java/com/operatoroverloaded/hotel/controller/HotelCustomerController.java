package com.operatoroverloaded.hotel.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.models.HotelCustomer;
import com.operatoroverloaded.hotel.stores.hotelcustomerstore.HotelCustomerStore;
import com.operatoroverloaded.hotel.stores.hotelcustomerstore.InMemoryHotelCustomerStore;

@RestController
@RequestMapping("/api/hotelcustomer")
public class HotelCustomerController {
    private HotelCustomerStore hotelCustomerStore;
    public HotelCustomerController(){
        this.hotelCustomerStore = new InMemoryHotelCustomerStore();
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<?> getBill(@PathVariable int customerId) {
        HotelCustomer customer = hotelCustomerStore.getCustomer(customerId);
        if (customer == null) {
            return ResponseEntity.status(404).body("Customer not found");
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok().body(hotelCustomerStore.getCustomers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody JsonNode json) {
        // JsonNode reservationsNode = json.get("reservations"); // ArrayNode
        ArrayList<Integer> reservations = new ArrayList<>();
        // for (JsonNode reservation : reservationsNode) {
        //     reservations.add(reservation.asInt());
        // }
        DateTime reservedTo = DateTime.fromISOString("2024-11-30T19:10:00.000Z");
        DateTime reservedFrom = DateTime.fromISOString("2024-11-30T19:10:00.000Z");
        // JsonNode billsNode = json.get("bills"); // ArrayNode
        ArrayList<Integer> bills = new ArrayList<>();
        // for (JsonNode bill : billsNode) {
        //     bills.add(bill.asInt());
        // }
        double bill_amt = 0;
        double bill_left = 0;
        double bill_payed = 0;
        String address = json.get("address").asText();
        String phone = json.get("phone").asText();
        String email = json.get("email").asText();
        String name = json.get("name").asText();
        HotelCustomer customer = new HotelCustomer(name, email, phone, address, bill_amt, bill_payed, bill_left, bills, reservedFrom, reservedTo, reservations);
        // this.hotelCustomerStore.addCustomer(customer);
        return ResponseEntity.ok().body(hotelCustomerStore.addCustomer(customer)); //it will return the id of the customer added
    }
    @PostMapping("/remove/{customerId}")
    public ResponseEntity<?> removeBill(@PathVariable int customerId) {
        hotelCustomerStore.deleteCustomer(customerId);
        return ResponseEntity.ok().body("Customer removed successfully");
    }
    @PostMapping("/update/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable int customerId, @RequestBody JsonNode json) {
        JsonNode reservationsNode = json.get("reservations"); // ArrayNode
        ArrayList<Integer> reservations = new ArrayList<>();
        for (JsonNode reservation : reservationsNode) {
            reservations.add(reservation.asInt());
        }
        JsonNode reservedToNode = json.get("reservedTo");
        JsonNode reservedFromNode = json.get("reservedFrom");
        DateTime reservedFrom = new DateTime(reservedFromNode.get("year").asInt(),reservedFromNode.get("month").asInt(), reservedFromNode.get("day").asInt(), reservedFromNode.get("hour").asInt(), reservedFromNode.get("minute").asInt(), reservedFromNode.get("second").asInt());
        DateTime reservedTo = new DateTime(reservedToNode.get("year").asInt(),reservedToNode.get("month").asInt(), reservedToNode.get("day").asInt(), reservedToNode.get("hour").asInt(), reservedToNode.get("minute").asInt(), reservedToNode.get("second").asInt());
        JsonNode billsNode = json.get("bills"); // ArrayNode
        ArrayList<Integer> bills = new ArrayList<>();
        for (JsonNode bill : billsNode) {
            bills.add(bill.asInt());
        }
        double bill_amt = json.get("billAmt").asDouble();
        double bill_left = json.get("billLeft").asDouble();
        double bill_payed = json.get("billPayed").asDouble();
        String address = json.get("address").asText();
        String phone = json.get("phone").asText();
        String email = json.get("email").asText();
        String name = json.get("name").asText();
        HotelCustomer customer = new HotelCustomer(name, email, phone, address, bill_amt, bill_payed, bill_left, bills, reservedFrom, reservedTo, reservations);
        hotelCustomerStore.updateCustomer(customerId, customer);
        return ResponseEntity.ok().body("Customer details updated successfully");
    }
    
}
