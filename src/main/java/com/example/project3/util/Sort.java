package com.example.project3.util;

import com.example.project3.rubank.account.Account;
import com.example.project3.rubank.account.AccountNumber;
import com.example.project3.rubank.banking.Branch;


/**
 * Utility class for sorting operations
 * @author Mohith Kodavati and Nalita Pillay
 */
public class Sort {

    /**
     * Sort accounts by holder profile and then account number
     * @param accounts array of accounts to sort
     */
    public static void byHolder(Account[] accounts) {
        if (accounts == null || accounts.length <= 1) return;
        // Simple bubble sort
        for (int i = 0; i < accounts.length - 1; i++) {
            for (int j = 0; j < accounts.length - i - 1; j++) {
                int comparison = accounts[j].getHolder().compareTo(accounts[j + 1].getHolder());
                if (comparison > 0 || (comparison == 0 && 
                    accounts[j].getNumber().compareTo(accounts[j + 1].getNumber()) > 0)) {
                    // Swap
                    Account temp = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Sort accounts by account type and then account number
     * @param accounts array of accounts to sort
     */
    public static void byAccountType(Account[] accounts) {
        if (accounts == null || accounts.length <= 1) return;
        // Simple bubble sort
        for (int i = 0; i < accounts.length - 1; i++) {
            for (int j = 0; j < accounts.length - i - 1; j++) {
                String type1 = accounts[j].getAccountType();
                String type2 = accounts[j + 1].getAccountType();
                int comparison = type1.compareTo(type2);
                if (comparison > 0 || (comparison == 0 && 
                        accounts[j].getNumber().compareTo(accounts[j + 1].getNumber()) > 0)) {
                    // Swap
                    Account temp = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Sort accounts by branch location (county, then city), and then account number
     * @param accounts array of accounts to sort
     */
    public static void byBranch(Account[] accounts) {
        if (accounts == null || accounts.length <= 1) return;
        // Simple bubble sort
        for (int i = 0; i < accounts.length - 1; i++) {
            for (int j = 0; j < accounts.length - i - 1; j++) {
                String county1 = accounts[j].getNumber().getBranch().getCounty();
                String county2 = accounts[j + 1].getNumber().getBranch().getCounty();
                String city1 = accounts[j].getNumber().getBranch().name();
                String city2 = accounts[j + 1].getNumber().getBranch().name();
                int countyComparison = county1.compareTo(county2);
                int cityComparison = city1.compareTo(city2);
                if ( countyComparison > 0 || 
                    (countyComparison == 0 && cityComparison > 0) ||
                    (countyComparison == 0 && cityComparison == 0 && accounts[j].getNumber().compareTo(accounts[j + 1].getNumber()) > 0)) {
                    // Swap
                    Account temp = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = temp;
                }
            }
        }
    }
}
