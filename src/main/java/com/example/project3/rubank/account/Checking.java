package com.example.project3.rubank.account;

import com.example.project3.rubank.banking.Profile;

/**
 * Checking account implementation
 * @author Mohith Kodavati and Nalita Pillay
 */
public class Checking extends Account {
    private static final double INTEREST_RATE = 0.015; // 1.5% annual
    private static final double MONTHLY_FEE = 15.0;
    private static final double NO_FEE_BALANCE = 1000.0;
    private static final int MONTHS_OF_YEAR = 12;

    /**
     * Constructor for Checking account
     * @param number account number
     * @param holder account holder profile
     * @param balance initial balance
     */
    public Checking(AccountNumber number, Profile holder, double balance) {
        super(number, holder, balance);
    }

    /**
     * Calculate monthly interest
     * @return interest amount
     */
    @Override
    public double interest() {
        return balance * (INTEREST_RATE / MONTHS_OF_YEAR);
    }

    /**
     * Calculate monthly fee
     * @return fee amount
     */
    @Override
    public double fee() {
        return (balance >= NO_FEE_BALANCE) ? 0 : MONTHLY_FEE;
    }

    /**
     * Returns a string representation of the checking account
     * @return string representation
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
