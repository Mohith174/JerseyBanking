package com.example.project3.rubank.account;
import com.example.project3.util.Date;

/**
 * Supporting class for Archive class
 * @author Mohith Kodavati and Nalita Pillay
 */
public class AccountNode {
    private Account account;
    private AccountNode next;
    private Date close;

    /**
     * Constructor for AccountNode
     * @param account
     * @param closeDate
     */
    public AccountNode(Account account, Date closeDate) {
        this.account = account;
        this.next = null;
        this.close = closeDate;
    }

    /**
     * Getter Method for Account
     * @return account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Getter Method for next AccountNode
     * @return next
     */
    public AccountNode getNext() {
        return next;
    }

    /**
     * Setter Method for setting the next AccountNode
     * @param next
     */
    public void setNext(AccountNode next) {
        this.next = next;
    }

    /**
     * Getter Method for close date
     * @return close date
     */
    public Date getCloseDate() {
        return close;
    }
}
