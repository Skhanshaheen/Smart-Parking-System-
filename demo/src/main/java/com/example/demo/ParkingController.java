package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api")
public class ParkingController {

    public static final ReservationSystem system = new ReservationSystem();

    public ParkingController() {
        if (system.listVehicles().isEmpty()) {
            for (int i = 1; i <= 20; i++) {
                Car c = new Car();
                c.setVehID("SPACE-" + i);
                c.setMake("Parking");
                system.addVehicle(c); 
            }
        }
    }

    @GetMapping("/spots")
    public Map<String, Object> getSpots(@RequestParam(required = false) String date) {
        Date targetDate;
        try {
            targetDate = (date == null || date.isBlank()) ? new Date() : DateUtil.convertStringToDate(date);
        } catch (Exception e) {
            targetDate = new Date();
        }
        
        String dateStr = DateUtil.convertDateToShortString(targetDate);
        List<Map<String, Object>> spotList = new ArrayList<>();
        int bookedCount = 0;

        List<Vehicle> vehicles = system.listVehicles();

        // Sort SPACE-1, SPACE-2, etc.
        vehicles.sort((v1, v2) -> {
            try {
                int id1 = Integer.parseInt(v1.getVehID().replace("SPACE-", ""));
                int id2 = Integer.parseInt(v2.getVehID().replace("SPACE-", ""));
                return Integer.compare(id1, id2);
            } catch (Exception e) {
                return v1.getVehID().compareTo(v2.getVehID());
            }
        });

        for (Vehicle v : vehicles) {
            VehicleReservation activeRes = null;
            
            // Look through all reservations to find one matching this spot AND date
            for (VehicleReservation r : system.getReservations()) {
                if (r.getVehicle().getVehID().equals(v.getVehID())) {
                    // Check if targetDate falls within the reservation period
                    Date end = DateUtil.incrementDate(r.getStartDate(), r.getNoOfDays() - 1);
                    if (!targetDate.before(r.getStartDate()) && !targetDate.after(end)) {
                        activeRes = r;
                        break;
                    }
                }
            }

            boolean isOccupied = (activeRes != null);
            if (isOccupied) bookedCount++;

            Map<String, Object> spotMap = new HashMap<>();
            spotMap.put("id", v.getVehID());
            spotMap.put("spotNumber", "Spot " + v.getVehID().replace("SPACE-", ""));
            spotMap.put("occupied", isOccupied);

            if (isOccupied) {
                // Formatting the string that the HTML tooltip will display
                spotMap.put("details", 
                    "<b>Name:</b> " + activeRes.getCustomerName() + "<br>" +
                    "<b>Car:</b> " + (activeRes.getBrand() != null ? activeRes.getBrand() : "N/A") + "<br>" +
                    "<b>Color:</b> " + (activeRes.getColor() != null ? activeRes.getColor() : "N/A") + "<br>" +
                    "<b>Reg:</b> " + (activeRes.getRegistration() != null ? activeRes.getRegistration() : "N/A")
                );
            }
            
            spotList.add(spotMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        response.put("date", dateStr);
        response.put("total", 20);
        response.put("booked", bookedCount);
        response.put("free", 20 - bookedCount);
        response.put("spots", spotList);
        
        return response;
    }
}