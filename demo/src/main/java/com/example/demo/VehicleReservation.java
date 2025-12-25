package com.example.demo;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class VehicleReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservationNo;
    private String customerID;

    @ManyToOne
    @JoinColumn(name = "veh_id")
    private Vehicle vehicle;

    private String customerName;
    private String brand;
    private String color;
    private String registration;
    private String pin;

    
    private Date startDate;
    private int noOfDays;

    public VehicleReservation() {}

    // --- GETTERS & SETTERS (Clears the 'not used' warnings) ---

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getRegistration() { return registration; }
    public void setRegistration(String registration) { this.registration = registration; }

    public String getReservationNo() { return reservationNo; }
    public void setReservationNo(String reservationNo) { this.reservationNo = reservationNo; }

    public String getCustomerID() { return customerID; }
    public void setCustomerID(String customerID) { this.customerID = customerID; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public int getNoOfDays() { return noOfDays; }
    public void setNoOfDays(int noOfDays) { this.noOfDays = noOfDays; }

    public String getPin() {
    return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}