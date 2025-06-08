package com.example.project3.rubank.account;
import java.text.DecimalFormat;

import com.example.project3.rubank.banking.Profile;
import com.example.project3.util.List;

/**
 * Account class now is an abstract class with two abstract methods.
 * @author Mohith Kodavati and Nalita Pillay
 */
public abstract class Account implements Comparable<Account> {
    // Given
    protected AccountNumber number;
    protected Profile holder;
    protected double balance;
    protected List<Activity> activities; //list of account activities (D or W)
    public static final DecimalFormat df = new DecimalFormat("#,##0.00");


    /**
     * Constructor for Account
     * @param number account number
     * @param holder account holder profile
     * @param balance initial balance
     */
    public Account(AccountNumber number, Profile holder, double balance) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.activities = new List<>();
    }

    /**
     * Gets the account number
     * @return account number
     */
    public AccountNumber getNumber() {
        return number;
    }

    /**
     * Gets the account holder's profile
     * @return profile of account holder
     */
    public Profile getHolder() {
        return holder;
    }

    /**
     * Gets the current balance
     * @return account balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account balance
     * @param balance new balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Gets the account type code as a string
     * @return account type code
     */
    public String getAccountType() {
        return number.getType().getCode();
    }

    /**
     * Add an activity (D or W) to this account's activity list
     * @param activity the activity to add
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    /**
     * Deposit money into the account
     * @param amount amount to deposit
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    /**
     * Withdraw money from the account
     * @param amount amount to withdraw
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    /**
     * Public accessor for printing the list of activities for this account
     */
    public String printAnyActivities() {
        String result = printActivities();
        return result;
    }

    /**
     * Prints the list of activities for this account
     */
    private String printActivities() {
        if (activities.isEmpty()) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        output.append("\t[Activity]\n");
        for (Activity activity : activities) {
            output.append("\t\t").append(activity.toString()).append("\n");
        }

        return output.toString();
    }

    /**
     * Prints the calculated interest and fee
     * @param interest monthly interest amount
     * @param fee monthly fee amount
     */
    private String printInterestFee(double interest, double fee) {
        return String.format("\t[interest] $%s [Fee] $%s\n", df.format(interest), df.format(fee));
    }

    /**
     * Prints the updated balance after applying interest and fee
     * @param interest monthly interest amount
     * @param fee monthly fee amount
     */
    private String printBalance(double interest, double fee) {
        double updatedBalance = balance + interest - fee;
        return String.format("\t[Balance] $%s\n", df.format(updatedBalance));
    }

    /**
     * Template Method Design Pattern
     */
    public final void statement() { //Template Method; DO NOT modify
        printActivities(); //private helper method
        double interest = interest(); //polymorphism based on actual type
        double fee = fee(); //polymorphism based on actual type
        printInterestFee(interest, fee); //private helper method
        printBalance(interest, fee); //private helper method
    }

    /**
     * Calculate monthly interest for this account
     * @return monthly interest amount
     */
    public abstract double interest(); //monthly interest

    /**
     * Calculate monthly fee for this account
     * @return monthly fee amount
     */
    public abstract double fee(); //account fee

    /**
     * Checks if this account equals another object
     * @param obj the object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Account)) return false;
        
        Account other = (Account) obj;
        return this.number.equals(other.number);
    }

    /**
     * Returns a string representation of the account
     * @return string representation
     */
    @Override
    public String toString() {
        return String.format("Account#[%s] Holder[%s] Balance[$%,.2f] Branch[%s]", 
                number, holder, balance, number.getBranch());
    }
    
    /**
     * Compares this account with another account
     * @param other the account to be compared
     * @return comparison result
     */
    @Override
    public int compareTo(Account other) {
        return this.number.compareTo(other.number);
    }
}
