package com.example.project3.rubank.banking;

import com.example.project3.rubank.account.Account;
import com.example.project3.rubank.account.AccountNode;
import com.example.project3.util.Date;

/**
 * Archive class which implements a linked list to hold a list of closed accounts.
 * @author Mohith Kodavati and Nalita Pillay
 */
public class Archive {
    private AccountNode first; //the head node of the linked list

    /**
     * Constructor
     */
    public Archive() {
        first = null;
    }

    /**
     * Add account to the front of the linked list
     * @param account the account to add
     * @param closeDate the date the account was closed
     */
    public void add(Account account, Date closeDate) {
        if (account == null) return;
        AccountNode newNode = new AccountNode(account, closeDate);
        newNode.setNext(first);
        first = newNode;
    }

    /**
     * Print all accounts in the archive
     */
    public String print() {
        StringBuilder output = new StringBuilder();
        if (first == null) {
            output.append("Archive is empty!\n");
            return output.toString();
        }
        AccountNode current = first;
        while (current != null) {
            Account account = current.getAccount();
            Date closeDate = current.getCloseDate();
            output.append(account.toString()).append(" Closed[").append(closeDate).append("]\n");
            String activities = account.printAnyActivities();
            if (activities != null && !activities.isEmpty()) {
                output.append(activities);
            }
            current = current.getNext();
        }
        return output.toString();
    }
}
