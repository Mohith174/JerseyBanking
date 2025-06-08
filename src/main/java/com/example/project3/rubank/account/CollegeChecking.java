package com.example.project3.rubank.account;
import com.example.project3.rubank.banking.Campus;
import com.example.project3.rubank.banking.Profile;

/**
 * College Checking account implementation
 * @author Mohith Kodavati and Nalita Pillay
 */
public class CollegeChecking extends Checking{
    private static final double INTEREST_RATE = 0.015; // 1.5% annual
    private static final double MONTHLY_FEE = 0.0; // No fee
    private static final int MONTHS_OF_YEAR = 12;

    private Campus campus; //campus code

    /**
     * Constructor for College Checking account
     * @param number account number
     * @param holder account holder profile
     * @param balance initial balance
     * @param campus campus code
     */
    public CollegeChecking(AccountNumber number, Profile holder, double balance, Campus campus) {
        super(number, holder, balance);
        this.campus = campus;
    }

    /**
     * Gets the campus
     * @return campus
     */
    public Campus getCampus() {
        return campus;
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
     * Calculate monthly fee (always 0 for college checking)
     * @return 0 (no fee)
     */
    @Override
    public double fee() {
        return MONTHLY_FEE;
    }

    /**
     * Returns a string representation of the college checking account
     * @return string representation
     */
    @Override
    public String toString() {
        return String.format("Account#[%s] Holder[%s] Balance[$%,.2f] Branch[%s] Campus[%s]", 
                number, holder, balance, number.getBranch(), campus);
    }
}
