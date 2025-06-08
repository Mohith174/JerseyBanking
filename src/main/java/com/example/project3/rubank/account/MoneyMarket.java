package com.example.project3.rubank.account;

import com.example.project3.rubank.banking.Profile;

/**
 * Money Market account implementation
 * @author Mohith Kodavati and Nalita Pillay
 */
public class MoneyMarket extends Savings {
    private static final double INTEREST_RATE = 0.035; // 3.5% annual
    private static final double LOYAL_INTEREST_BONUS = 0.0025; // 0.25% bonus
    private static final double MONTHLY_FEE = 25.0;
    private static final double NO_FEE_BALANCE = 2000.0;
    private static final double LOYAL_THRESHOLD = 5000.0;
    private static final int MAX_WITHDRAWALS = 3;
    private static final double EXCESS_WITHDRAWAL_FEE = 10.0;
    private static final int MONTHS_OF_YEAR = 12;
    
    private int withdrawal; //number of withdrawals

    /**
     * Constructor for Money Market account
     * @param number account number
     * @param holder account holder profile
     * @param balance initial balance
     */
    public MoneyMarket(AccountNumber number, Profile holder, double balance) {
        super(number, holder, balance);
        this.withdrawal = 0;
        this.isLoyal = balance >= LOYAL_THRESHOLD;
    }

    /**
     * Gets the withdrawal count
     * @return number of withdrawals
     */
    public int getWithdrawal() {
        return withdrawal;
    }

    /**
     * Increment the withdrawal count
     */
    public void incrementWithdrawal() {
        withdrawal++;
    }

    /**
     * Reset the withdrawal count to zero
     */
    public void resetWithdrawal() {
        withdrawal = 0;
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
        double baseFee = (balance >= NO_FEE_BALANCE) ? 0 : MONTHLY_FEE;
        double excessWithdrawalFee = (withdrawal > MAX_WITHDRAWALS) ? EXCESS_WITHDRAWAL_FEE : 0;
        return baseFee + excessWithdrawalFee;
    }

    /**
     * Withdraw money and increment withdrawal count
     * @param amount amount to withdraw
     */
    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            incrementWithdrawal();
            // Update loyalty status
            if (balance < LOYAL_THRESHOLD) {
                setLoyal(false);
            }
        }
    }

    /**
     * Deposit money and update loyalty status
     * @param amount amount to deposit
     */
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            // Update loyalty status
            if (balance >= LOYAL_THRESHOLD) {
                setLoyal(true);
            }
        }
    }

    /**
     * Returns a string representation of the money market account
     * @return string representation
     */
    @Override
    public String toString() {
        String loyaltyString = isLoyal ? " [LOYAL]" : "";
        return String.format("Account#[%s] Holder[%s] Balance[$%,.2f] Branch[%s]%s Withdrawal[%d]", 
                number, holder, balance, number.getBranch(), loyaltyString, withdrawal);
    }
}
