package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class BookingController {

    private final ReservationSystem system = ParkingController.system;

    @PostMapping("/quick-book")
    public Map<String, Object> quickBook(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            String vehId = (String) payload.get("vehId");
            String name = (String) payload.get("name");
            String brand = (String) payload.get("brand");
            String color = (String) payload.get("color");
            String reg = (String) payload.get("reg");
            String startDateStr = (String) payload.get("startDate");
            // NEW: Extract the PIN from the payload
            String pin = (String) payload.get("pin"); 
            int days = Integer.parseInt(payload.get("days").toString());

            String customerId = system.registerCustomerDb(name, "", "", "");

            Vehicle v = system.getVehicle(vehId);
            if (v == null) {
                v = new Car();
                v.setVehID(vehId);
                system.addVehicle(v);
            }

            // ADDED: Passing 'pin' to the system so it can be saved
            String resNo = system.makeReservationDb(customerId, vehId, startDateStr, days, name, brand, color, reg, pin);

            if (resNo != null) {
                response.put("ok", true);
            } else {
                response.put("ok", false);
                response.put("message", "This space is already occupied.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("ok", false);
            response.put("message", "Server Error: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/cancel-book")
    public Map<String, Object> cancelBook(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            String vehId = (String) payload.get("vehId");
            String date = (String) payload.get("date");
            String pin = (String) payload.get("pin");

            // This calls the logic in ReservationSystem to verify PIN and delete
            boolean success = system.deleteReservation(vehId, date, pin);
            
            response.put("ok", success);
            if (!success) response.put("message", "Incorrect PIN or no reservation found.");
        } catch (Exception e) {
            response.put("ok", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
}