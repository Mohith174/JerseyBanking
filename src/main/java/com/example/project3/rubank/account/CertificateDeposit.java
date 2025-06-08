package com.example.project3.rubank.account;

import com.example.project3.rubank.banking.Profile;
import com.example.project3.util.Date;

/**
 * Certificate Deposit account implementation
 * @author Mohith Kodavati and Nalita Pillay
 */
public class CertificateDeposit extends Savings {
    // Interest rates based on term
    private static final double INTEREST_RATE_3_MONTH = 0.03; // 3.00% annual
    private static final double INTEREST_RATE_6_MONTH = 0.0325; // 3.25% annual
    private static final double INTEREST_RATE_9_MONTH = 0.035; // 3.50% annual
    private static final double INTEREST_RATE_12_MONTH = 0.04; // 4.00% annual

    // Constants for leap year calculations
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    //Constants for days in month
    public static final int MONTHWITHTHIRTYDAYS = 30;
    public static final int MONTHWITHTHIRTYONE = 31;
    public static final int NOLEAPYEAR = 28;
    public static final int LEAPYEAR = 29;

    // Early withdrawal penalty rates
    private static final double PENALTY_RATE = 0.10; // 10% of earned interest
    
    // Valid terms in months
    public static final int THREE_MONTHS = 3;
    public static final int SIX_MONTHS = 6;
    public static final int NINE_MONTHS = 9;
    public static final int TWELVE_MONTHS = 12;
    public static final int DAYSOFYEAR = 365;
    public static final int DAYSOFMONTH = 30;
    public static final int MONTHSOFYEAR = 12;
    
    private int term;
    private Date open; //for computing the maturity date(open date + term)

    /**
     * Constructor for Certificate Deposit account
     * @param number account number
     * @param holder account holder profile
     * @param balance initial balance
     * @param term term in months
     * @param open opening date
     */
    public CertificateDeposit(AccountNumber number, Profile holder, double balance, int term, Date open) {
        super(number, holder, balance);
        this.term = term;
        this.open = open;
        this.isLoyal = false; // CDs don't have loyalty status
    }

    /**
     * Gets the term
     * @return term in months
     */
    public int getTerm() {
        return term;
    }

    /**
     * Gets the opening date
     * @return opening date
     */
    public Date getOpen() {
        return open;
    }

    /**
     * Calculate maturity date
     * @return maturity date
     */
    public Date getMaturityDate() {
        int maturityYear = open.getYear();
        int maturityMonth = open.getMonth() + term;
        int maturityDay = open.getDay();
        // Adjust for month overflow
        while (maturityMonth > MONTHSOFYEAR) {
            maturityMonth -= MONTHSOFYEAR;
            maturityYear += 1;
        }
        Date maturityDate = new Date(maturityMonth, maturityDay, maturityYear);
        while (!maturityDate.isValidMaturityDate()) {
            maturityDay -= 1;
            maturityDate = new Date(maturityMonth, maturityDay, maturityYear);
        }
        return maturityDate;
    }

    /**
     * Get interest rate based on term
     * @return interest rate
     */
    private double getInterestRate() {
        switch(term) {
            case THREE_MONTHS:
                return INTEREST_RATE_3_MONTH;
            case SIX_MONTHS:
                return INTEREST_RATE_6_MONTH;
            case NINE_MONTHS:
                return INTEREST_RATE_9_MONTH;
            case TWELVE_MONTHS:
                return INTEREST_RATE_12_MONTH;
            default:
                return 0;
        }
    }

    /**
     * Calculate monthly interest
     * @return interest amount
     */
    @Override
    public double interest() {
        return balance * (getInterestRate() / MONTHSOFYEAR);
    }

    /**
     * Calculate monthly fee (always 0 for CD)
     * @return 0 (no fee)
     */
    @Override
    public double fee() {
        return 0;
    }
    
    /**
     * Checks if Leap Year
     * @param year year to check
     * @return true if leap year, false otherwise
     */
    private boolean isLeapYear(int year) {
        if (year % QUADRENNIAL != 0) {
            return false;
        }
        if (year % CENTENNIAL != 0) {
            return true;
        }
        return year % QUATERCENTENNIAL == 0;
    }

    /**
    * Calculates day between open and close dates
    * @param start start date
    * @param end end date
    * @return days between start and end
    */
    private int calculateDaysBetween(Date start, Date end) {
        int startDays = 0;
        int endDays = 0;
        for (int year = 1; year < start.getYear(); year++) {
            startDays += isLeapYear(year) ? (DAYSOFYEAR + 1) : DAYSOFYEAR;
        }
        for (int month = 1; month < start.getMonth(); month++) {
                startDays += start.findNumInMonth(month);
        }
        startDays += start.getDay();
        for (int year = 1; year < end.getYear(); year++) {
            endDays += isLeapYear(year) ? 366 : 365;
        }
        for (int month = 1; month < end.getMonth(); month++) {
            endDays += start.findNumInMonth(month);
        }
        endDays += end.getDay();
        return Math.max(1, (endDays - startDays + 1));
    }
    
    /**
     * Calculate interest for early withdrawal
     * @param currentDate date of withdrawal
     * @return interest amount
     */
    public double calculateEarlyWithdrawalInterest(Date currentDate) {
        // Calculate days from opening to current
        Date maturityDate = getMaturityDate();
        if (currentDate.compareTo(maturityDate) >= 0) {
            int daysBetween = calculateDaysBetween(open, currentDate);
            double applyInterest = 0;
            if (this.getTerm() == 3) {
                applyInterest = INTEREST_RATE_3_MONTH;
            } else if (this.getTerm() == 6) {
                applyInterest = INTEREST_RATE_6_MONTH;
            } else if (this.getTerm() == 9) {
                applyInterest = INTEREST_RATE_9_MONTH;
            } else if (this.getTerm() == 12) {
                applyInterest = INTEREST_RATE_12_MONTH;
            }   
            return balance * applyInterest / DAYSOFYEAR * daysBetween;
        }
        int daysFromOpen = calculateDaysBetween(open, currentDate);
        // Determine the applicable interest rate
        double interestRate = 0;
        double monthsFromOpen = (double) daysFromOpen / DAYSOFMONTH;
        if (monthsFromOpen <= 6) {
            interestRate = INTEREST_RATE_3_MONTH;
        } else if (monthsFromOpen <= 9) {
            interestRate = INTEREST_RATE_6_MONTH;
        } else if (monthsFromOpen < 12) {
            interestRate = INTEREST_RATE_9_MONTH;
        }
        // Calculate interest
        return balance * interestRate / DAYSOFYEAR * daysFromOpen;
    }

    /**
     * Calculate penalty for early withdrawal
     * @param interest interest earned
     * @return penalty amount
     */
    public double calculateEarlyWithdrawalPenalty(double interest) {
        return interest * PENALTY_RATE;
    }

    /**
     * Withdraw is not allowed for CD accounts before maturity
     * @param amount amount to withdraw
     */
    @Override
    public void withdraw(double amount) {
        // CDs cannot be withdrawn from before maturity
        // This method intentionally does nothing
    }

    /**
     * Deposit is not allowed for CD accounts after opening
     * @param amount amount to deposit
     */
    @Override
    public void deposit(double amount) {
        // Cannot deposit to a CD after opening
        // This method intentionally does nothing
    }

    /**
     * Returns a string representation of the CD account
     * @return string representation
     */
    @Override
    public String toString() {
        Date maturityDate = getMaturityDate();
        return String.format("Account#[%s] Holder[%s] Balance[$%,.2f] Branch[%s] Term[%d] Date opened[%s] Maturity date[%s]", 
                number, holder, balance, number.getBranch(), term, open, maturityDate);
    }

}
