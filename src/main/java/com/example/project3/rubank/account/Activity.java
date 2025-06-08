package com.example.project3.rubank.account;;

import com.example.project3.rubank.banking.Branch;
import com.example.project3.util.Date;

/**
 * Activity class represents a banking transaction (deposit or withdrawal)
 * @author Mohith Kodavati and Nalita Pillay
 */
public class Activity implements Comparable<Activity>{
    private Date date;
    private Branch location; //the location of the activity
    private char type; //D or W
    private double amount;
    private boolean atm; //true if this is made at an ATM (from the text file)
    private long transactionId; // New field
    // Static counter to generate unique IDs
    private static long nextId = 0;

    /**
     * Constructor for Activity class
     * @param date the date of the activity
     * @param location the branch where activity occurred
     * @param type the type of activity ('D' for deposit, 'W' for withdrawal)
     * @param amount the transaction amount
     * @param atm true if transaction was made at ATM, false otherwise
     */
    public Activity(Date date, Branch location, char type, double amount, boolean atm) {
        this.date = date;
        this.location = location;
        this.type = type;
        this.amount = amount;
        this.atm = atm;
        this.transactionId = nextId++;
    }

    /**
     * Get the date of the activity
     * @return the date of the activity
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get the location of the activity
     * @return the location of the activity
     */
    public Branch getLocation() {
        return location;
    }

    /**
     * Get the type of the activity
     * @return the type of the activity
     */
    public char getType() {
        return type;
    }

    /**
     * Get the amount of the activity
     * @return the amount of the activity
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Check if the activity was made at an ATM
     * @return true if the activity was made at an ATM, false otherwise
     */
    public boolean isAtm() {
        return atm;
    }

    /**
     * Compares this activity with another activity based on date
     * @param other the activity to be compared
     * @return negative if this activity is earlier, 0 if same date, positive if later
     */
    @Override
    public int compareTo(Activity other) {
        return this.date.compareTo(other.date);
    }

    /**
     * Returns a string representation of the activity
     * Format: date::location[ATM]::type::$amount
     * @return string representation
     */
    @Override
    public String toString() {
        String atmIndicator = atm ? "[ATM]" : "";
        String typeString = (type == 'D') ? "deposit" : "withdrawal";
        return String.format("%s::%s%s::%s::$%,.2f", 
                date, location, atmIndicator, typeString, amount);
    }

    /**
     * Checks if this activity equals another object
     * @param obj the object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Activity)) return false;
        
        Activity other = (Activity) obj;
        return this.date.equals(other.date) && 
               this.location == other.location && 
               this.type == other.type && 
               Math.abs(this.amount - other.amount) < 0.001 && 
               this.atm == other.atm && 
               this.transactionId == other.transactionId;
    }

}
