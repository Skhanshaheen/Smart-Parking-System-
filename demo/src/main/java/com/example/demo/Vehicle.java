package com.example.demo;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity 
public class Vehicle {

    @Id 
    protected String vehID; // e.g., "SPACE-1"
    
    protected String vehicleGroup; 
    protected String regNo;
    protected String make; // This is the "Parking" label you used in seeding

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<VehicleReservation> reservations = new ArrayList<>();

    public Vehicle() {}

    public Vehicle(String vehID) {
        this.vehID = vehID;
    }

    // --- GETTERS AND SETTERS ---
    public void setVehID(String vehID){ this.vehID = vehID; }
    public String getVehID(){ return vehID; }

    public void setVehicleGroup(String vehicleGroup){ this.vehicleGroup = vehicleGroup; }
    public String getVehicleGroup(){ return vehicleGroup; }

    public void setRegNo(String regNo){ this.regNo = regNo; }
    public String getRegNo(){ return regNo; }

    public void setMake(String make){ this.make = make; }
    public String getMake(){ return make; }

    public List<VehicleReservation> getReservations() { return reservations; }
    public void setReservations(List<VehicleReservation> reservations) { this.reservations = reservations; }
}