package com.example.demo;

import jakarta.persistence.Entity;

@Entity // This allows Cars to be saved in the database too
public class Car extends Vehicle {
    // We only keep what is unique to a Car
    private String bodyType;
    private int noOfDoors;
    private int noOfSeats;

    public Car() {}

    // Getters and Setters
    public String getBodyType() { return bodyType; }
    public void setBodyType(String bodyType) { this.bodyType = bodyType; }

    public int getNoOfDoors() { return noOfDoors; }
    public void setNoOfDoors(int noOfDoors) { this.noOfDoors = noOfDoors; }

    public int getNoOfSeats() { return noOfSeats; }
    public void setNoOfSeats(int noOfSeats) { this.noOfSeats = noOfSeats; }
}