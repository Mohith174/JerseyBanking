package com.example.project3.rubank.banking;
import com.example.project3.util.Date;

/**
 * Profile Class for RU Bank Account Holders
 * @author Mohith Kodavati and Nalita Pillay
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor Method
     * @param fname First Name of Account Holder
     * @param lname Last Name of Account Holder
     * @param dob Date of Birth of Account Holder
     */
    public Profile (String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Getter Method for First Name
     * @return A String that is the First Name of Account Holder
     */
    public String getFname() {
        return fname;
    }

    /**
     * Getter Method for Last Name
     * @return A String that is the Last Name of the Account Holder
     */
    public String getLname() {
        return lname;
    }

    /**
     * Getter Method for Date of Birth
     * @return Date class with Date of Birth of Account Holder
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Get name of profile (first and last)
     * @return String in format "firstname lastname"
     */
    public String getName() {
        return fname + " " + lname;
    }

    /**
     * Checks if two Profile objects are equal
     * @param obj the object to compare with
     * @return true if the profiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile profile = (Profile) obj;
        if (!(fname.equalsIgnoreCase(profile.fname))) {
            return false;
        }
        if (!(lname.equalsIgnoreCase(profile.lname))) {
            return false;
        }
        if (dob.compareTo(profile.dob) != 0) {
            return false;
        }
        return true;
    }

    /**
     * Compares two Profile objects based on last name, first name, and date of birth
     * @param profile the Profile to compare with
     * @return 1 if this profile is greater, -1 if less, 0 if equal
     */
    @Override
    public int compareTo(Profile profile) {
        int lastNameComparison = this.lname.compareToIgnoreCase(profile.lname);
        if (lastNameComparison != 0) {
            return (lastNameComparison > 0) ? 1 : -1;
        }
        int firstNameComparison = this.fname.compareToIgnoreCase(profile.fname);
        if (firstNameComparison != 0) {
            return (firstNameComparison > 0) ? 1 : -1;
        }
        int dobComparison = this.dob.compareTo(profile.dob);
        if (dobComparison > 0) {
            return 1;
        } else if (dobComparison < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Returns a string representation of the Profile
     * @return string in format "lastname, firstname dob"
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob;
    }

}
