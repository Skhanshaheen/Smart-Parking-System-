package com.example.demo;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Customer class.
 */
public class Customer {
    private String customerID;
    private String surname;
    private String firstName;
    private String otherInitials;
    private String title;

    public Customer() {}

    public Customer(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerID() { return customerID; }
    public void setCustomerID(String customerID) { this.customerID = customerID; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getOtherInitials() { return otherInitials; }
    public void setOtherInitials(String otherInitials) { this.otherInitials = otherInitials; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    /**
     * Reads customer data (space-separated):
     * customerID surname firstName otherInitials title
     */
    public void readData(Scanner scanner) {
        if (scanner.hasNext()) customerID = scanner.next();
        if (scanner.hasNext()) surname = scanner.next();
        if (scanner.hasNext()) firstName = scanner.next();
        if (scanner.hasNext()) otherInitials = scanner.next();
        if (scanner.hasNext()) title = scanner.next();
    }

    public void writeData(PrintWriter writer) {
        writer.println(customerID + " " + surname + " " + firstName + " " + otherInitials + " " + title);
    }

    public void printDetails() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("Title: " + title);
        System.out.println("First Name: " + firstName);
        System.out.println("Other Initials: " + otherInitials);
        System.out.println("Surname: " + surname);
    }
}
