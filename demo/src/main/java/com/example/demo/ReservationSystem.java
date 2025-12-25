package com.example.demo;

import java.util.*;

public class ReservationSystem {

    private final Map<String, Vehicle> vehiclesById = new HashMap<>();
    private final Map<String, Customer> customersById = new HashMap<>();
    private final Map<String, VehicleReservation> reservationsByNo = new HashMap<>();

    private int nextCustomerNo = 1;
    private int nextReservationNo = 1;

    // ===== Vehicles =====
    public void addVehicle(Vehicle v) {
        if (v == null || v.getVehID() == null || v.getVehID().isBlank()) {
            throw new IllegalArgumentException("Vehicle must have a vehID");
        }
        vehiclesById.put(v.getVehID(), v);
    }

    public Vehicle getVehicle(String vehId) {
        return vehiclesById.get(vehId);
    }

    public List<Vehicle> listVehicles() {
        return new ArrayList<>(vehiclesById.values());
    }

    // ===== Customers =====
    public Customer registerCustomer(String surname, String firstName, String otherInitials, String title) {
        String id = "CUST-" + String.format("%06d", nextCustomerNo++);
        Customer c = new Customer();
        c.setCustomerID(id);
        c.setSurname(surname);
        c.setFirstName(firstName);
        c.setOtherInitials(otherInitials == null ? "" : otherInitials);
        c.setTitle(title);
        customersById.put(id, c);
        return c;
    }

    // ===== Reservations =====
    public VehicleReservation makeReservation(String customerId, String vehId, String startDateStr, int noOfDays) {
        if (!customersById.containsKey(customerId)) return null;

        Vehicle v = vehiclesById.get(vehId);
        if (v == null) return null;

        Date startDate = DateUtil.convertStringToDate(startDateStr);
        if (!isVehicleAvailable(vehId, startDate, noOfDays)) {
            return null;
        }

        String resNo = String.format("%06d", nextReservationNo++);
        
        // FIXED: Using setters because the old constructor was removed/changed
        VehicleReservation r = new VehicleReservation();
        r.setReservationNo(resNo);
        r.setVehicle(v); // Links the reservation to the actual Vehicle object
        r.setCustomerID(customerId);
        r.setStartDate(startDate);
        r.setNoOfDays(noOfDays);

        reservationsByNo.put(resNo, r);
        return r;
    }

    /**
     * Overlap check formula: (StartA <= EndB) and (EndA >= StartB)
     */
    public boolean isVehicleAvailable(String vehId, Date requestedStart, int noOfDays) {
        Date requestedEnd = DateUtil.incrementDate(requestedStart, noOfDays - 1);

        for (VehicleReservation existing : reservationsByNo.values()) {
            // FIXED: Use r.getVehicle().getVehID() to access the ID from the linked object
            if (existing.getVehicle() != null && existing.getVehicle().getVehID().equals(vehId)) {
                Date existingStart = existing.getStartDate();
                Date existingEnd = DateUtil.incrementDate(existingStart, existing.getNoOfDays() - 1);

                if (!requestedStart.after(existingEnd) && !requestedEnd.before(existingStart)) {
                    return false; // Overlap found!
                }
            }
        }
        return true;
    }

    public int activeBookingsCountForDate(String dateStr) {
        Date checkDate = DateUtil.convertStringToDate(dateStr);
        int count = 0;
        for (VehicleReservation r : reservationsByNo.values()) {
            Date end = DateUtil.incrementDate(r.getStartDate(), r.getNoOfDays() - 1);
            if (!checkDate.before(r.getStartDate()) && !checkDate.after(end)) {
                count++;
            }
        }
        return count;
    }

    // ===== Wrapper Methods for Controller =====
    public String registerCustomerDb(String surname, String firstName, String otherInitials, String title) {
        Customer c = registerCustomer(surname, firstName, otherInitials, title);
        return c.getCustomerID();
    }

// Update this method to include the 'pin'
public String makeReservationDb(String customerId, String vehId, String startDateStr, int noOfDays, String name, String brand, String color, String reg, String pin) {
    VehicleReservation r = makeReservation(customerId, vehId, startDateStr, noOfDays);
    if (r != null) {
        r.setCustomerName(name);
        r.setBrand(brand);
        r.setColor(color);
        r.setRegistration(reg);
        r.setPin(pin); // SAVE THE PIN
        return r.getReservationNo();
    }
    return null;
}

// NEW METHOD to delete
public boolean deleteReservation(String vehId, String dateStr, String enteredPin) {
    Date targetDate = DateUtil.convertStringToDate(dateStr);
    
    // We use an Iterator to safely remove items while looping
    Iterator<Map.Entry<String, VehicleReservation>> iterator = reservationsByNo.entrySet().iterator();
    
    while (iterator.hasNext()) {
        Map.Entry<String, VehicleReservation> entry = iterator.next();
        VehicleReservation r = entry.getValue();
        
        // 1. Match the Vehicle ID
        if (r.getVehicle() != null && r.getVehicle().getVehID().equals(vehId)) {
            
            Date end = DateUtil.incrementDate(r.getStartDate(), r.getNoOfDays() - 1);
            
            // 2. Check if the target date falls within this reservation period
            if (!targetDate.before(r.getStartDate()) && !targetDate.after(end)) {
                
                // 3. SAFE PIN CHECK: Prevent NullPointerException
                // If the stored PIN is null, we treat it as a failure instead of crashing
                if (r.getPin() != null && r.getPin().equals(enteredPin)) {
                    iterator.remove(); // Safely remove the reservation
                    return true;
                } else {
                    // PIN was either null or didn't match
                    return false; 
                }
            }
        }
    }
    return false;
}
    // Add this method inside ReservationSystem.java
    public Collection<VehicleReservation> getReservations() {
        return reservationsByNo.values();
    }
}