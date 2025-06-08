package com.example.project3.rubank.account;

import com.example.project3.rubank.banking.Profile;

/**
 * Savings account implementation
 * @author Mohith Kodavati and Nalita Pillay
 */
public class Savings extends Account{
    private static final double INTEREST_RATE = 0.025; // 2.5% annual
    private static final double LOYAL_INTEREST_BONUS = 0.0025; // 0.25% bonus
    private static final double MONTHLY_FEE = 25.0;
    private static final double NO_FEE_BALANCE = 500.0;
    private static final int MONTHS_OF_YEAR = 12;
    
    protected boolean isLoyal; //loyal customer status

    /**
     * Constructor for Savings account
     * @param number account number
     * @param holder account holder profile
     * @param balance initial balance
     */
    public Savings(AccountNumber number, Profile holder, double balance) {
        super(number, holder, balance);
        this.isLoyal = false;
    }

    /**
     * Constructor for Savings account with loyalty status
     * @param number account number
     * @param holder account holder profile
     * @param balance initial balance
     * @param isLoyal loyalty status
     */
    public Savings(AccountNumber number, Profile holder, double balance, boolean isLoyal) {
        super(number, holder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * Gets loyal customer status
     * @return true if loyal, false otherwise
     */
    public boolean isLoyal() {
        return isLoyal;
    }

    /**
     * Sets loyal customer status
     * @param isLoyal new loyalty status
     */
    public void setLoyal(boolean isLoyal) {
        this.isLoyal = isLoyal;
    }

    /**
     * Calculate monthly interest
     * @return interest amount
     */
    @Override
    public double interest() {
        double rate = INTEREST_RATE;
        if (isLoyal) {
            rate += LOYAL_INTEREST_BONUS;
        }
        return balance * (rate / MONTHS_OF_YEAR);
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
     * Returns a string representation of the savings account
     * @return string representation
     */
    @Override
    public String toString() {
        String loyaltyString = isLoyal ? " [LOYAL]" : "";
        return String.format("Account#[%s] Holder[%s] Balance[$%,.2f] Branch[%s]%s", 
                number, holder, balance, number.getBranch(), loyaltyString);
    }

}
