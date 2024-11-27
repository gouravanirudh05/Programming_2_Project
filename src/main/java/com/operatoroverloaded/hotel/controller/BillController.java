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
import com.operatoroverloaded.hotel.models.Bill;
import com.operatoroverloaded.hotel.models.DateTime;
import com.operatoroverloaded.hotel.stores.billstore.BillStore;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    private final BillStore billStore;

    public BillController() {
        this.billStore =BillStore.getInstance();
    }

    @GetMapping("/{billId}")
    public ResponseEntity<?> getBill(@PathVariable int billId) {
        Bill bill = billStore.getBill(billId);
        if (bill == null) {
            return ResponseEntity.status(404).body("Bill not found");
        }
        return ResponseEntity.ok().body(bill);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBill(@RequestBody JsonNode json) {
        ArrayList<String> purchased = new ArrayList<String>();
        ArrayList<Float> purchasedList = new ArrayList<Float>();
        ArrayList<Integer> quantity = new ArrayList<Integer>();
        boolean payed = json.get("payed").asBoolean();
        String customerID = json.get("customerID").asText();
        for (JsonNode item : json.get("items")) {
            purchased.add(item.get("name").asText());
            purchasedList.add((float)item.get("price").asDouble());
            quantity.add(item.get("quantity").asInt());
        }
        DateTime payedOn = DateTime.fromISOString(json.get("payedOn").asText());
        billStore.addBill(purchased, purchasedList, quantity, payedOn, payed, customerID);
        return ResponseEntity.ok().body("Bill added successfully");
    }

    @PostMapping("/remove/{billId}")
    public ResponseEntity<?> removeBill(@PathVariable int billId) {
        billStore.removeBill(billId);
        return ResponseEntity.ok().body("Bill removed successfully");
    }

    @PostMapping("/update/{billId}")
    public ResponseEntity<?> updateBill(@PathVariable int billId, @RequestBody JsonNode json) {
        ArrayList<String> purchased = new ArrayList<String>();
        ArrayList<Float> purchasedList = new ArrayList<Float>();
        ArrayList<Integer> quantity = new ArrayList<Integer>();
        boolean payed = json.get("payed").asBoolean();
        String customerID = json.get("customerID").asText();
        for (JsonNode item : json.get("items")) {
            purchased.add(item.get("name").asText());
            purchasedList.add((float) item.get("price").asDouble());
            quantity.add(item.get("quantity").asInt());
        }
        DateTime payedOn = DateTime.fromISOString(json.get("payedOn").asText());
        billStore.updateBill(billId, purchased, purchasedList, quantity, payedOn,payed, customerID);
        return ResponseEntity.ok().body("Bill updated successfully");
    }
    @GetMapping("/list")
    public ResponseEntity<?> listBills(){
        ArrayList<Bill> bills = billStore.getBills();
        return ResponseEntity.ok().body(bills);
    }
}   