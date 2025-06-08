package com.example.project3.util;

import java.util.Calendar;

/**
 * Date class for checking date and validity of date
 * @author Mohith Kodavati and Nalita Pillay
 */
public class Date implements Comparable<Date>{
    private int month;
    private int day;
    private int year;

    // Constants for months
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;

    //Constants for days in month
    public static final int MONTHWITHTHIRTYDAYS = 30;
    public static final int MONTHWITHTHIRTYONE = 31;
    public static final int NOLEAPYEAR = 28;
    public static final int LEAPYEAR = 29;

    // Constants for leap year calculations
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    /**
     * Constructor method
     * @param month
     * @param day
     * @param year
     */
    public Date(int month, int day, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Getter method for year
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter method for month
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter method for day
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * private method to check if it's a leap year
     * @return true or false depending on if it's a leap year
     */
    private boolean isLeapYear() {
        if (year % QUADRENNIAL != 0) {
            return false;
        }
        if (year % CENTENNIAL != 0) {
            return true;
        }
        return year % QUATERCENTENNIAL == 0;
    }

    /**
     * private method to get the current year
     * @return the current year
     */
    private int getThisYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * private method to get the number of days in a specific month
     * @param month the month to check
     * @return the number of days in the month
     */
    public int findNumInMonth(int month) {
        int maxDays = 0;
        switch (month) {
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                maxDays = MONTHWITHTHIRTYDAYS;
                break;
            case FEBRUARY:
                maxDays = isLeapYear() ? LEAPYEAR : NOLEAPYEAR;
                break;
            default:
                maxDays = MONTHWITHTHIRTYONE;
                break;
        }
        return maxDays;
    }

    /**
     * Checks if date is valid
     * @return true if it is a valid date else false
     */
    public boolean isValid() {
        if (year < 1 || year > getThisYear() || month < 1 || month > 12 || day < 1) {
            return false;
        }
        int maxDays = findNumInMonth(month);
        return day <= maxDays;
    }

    /**
     * Checks if date is valid
     * @return true if it is a valid date else false
     */
    public boolean isValidMaturityDate() {
        if (year < 1 || month < 1 || month > 12 || day < 1) {
            return false;
        }
        int maxDays = findNumInMonth(month);
        return day <= maxDays;
    }

    /**
     * Checks equality of two Date objects
     * @param obj the object to compare with
     * @return true if the dates are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date other = (Date) obj;
        return year == other.year && month == other.month &&
                day == other.day;
    }

    /**
     * Returns the date in the format "MM/DD/YYYY"
     * @return the date in the format "MM/DD/YYYY"
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Compares two Date objects based on year, month, and day
     * @param date the Date to compare with
     * @return 1 if this date is greater, -1 if less, 0 if equal
     */
    @Override
    public int compareTo(Date date) {
        if (this.year != date.year) {
            return this.year - date.year;
        }
        if (this.month != date.month) {
            return this.month - date.month;
        }
        return this.day - date.day;
    }

}
