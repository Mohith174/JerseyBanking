package com.example.project3.rubank.account;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.example.project3.rubank.banking.Archive;
import com.example.project3.rubank.banking.Branch;
import com.example.project3.rubank.banking.Campus;
import com.example.project3.rubank.banking.Profile;
import com.example.project3.util.Date;
import com.example.project3.util.List;
import com.example.project3.util.Sort;

/**
 * AccountDatabase class extends List<Account> to manage accounts
 * @author Mohith Kodavati and Nalita Pillay
 */
public class AccountDatabase extends List<Account> {
    private Archive archive;

    /**
     * Constructor for AccountDatabase
     */
    public AccountDatabase() {
        super();
        archive = new Archive();
    }

    /**
     * Find account in the database
     * @param account the account to find
     * @return the index if found, -1 otherwise
     */
    private int find(Account account) {
        if (account == null) return -1;
        for (int i = 0; i < size(); i++) {
            if (get(i).getNumber().equals(account.getNumber())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Find account by account number
     * @param accountNumber the account number string
     * @return the account if found, null otherwise
     */
    public Account getAccountByNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) return null;
        for (int i = 0; i < size(); i++) {
            if (get(i).getNumber().toString().equals(accountNumber)) {
                return get(i);
            }
        }
        return null;
    }

    /**
     * Find account by account number object
     * @param accountNumber the account number object
     * @return the account if found, null otherwise
     */
    public Account getAccount(AccountNumber accountNumber) {
        if (accountNumber == null) return null;
        for (int i = 0; i < size(); i++) {
            if (get(i).getNumber().equals(accountNumber)) {
                return get(i);
            }
        }
        return null;
    }

    /**
     * Check if account exists in database
     * @param account the account to check
     * @return true if found, false otherwise
     */
    @Override
    public boolean contains(Account account) {
        return find(account) != -1;
    }

    /**
     * Remove account from database and add to archive
     * @param account the account to remove
     * @param closeDate the date of closing
     * @return true if removed, false otherwise
     */
    public boolean remove(Account account, Date closeDate) {
        int index = find(account);
        if (index == -1) return false;
        Account removedAccount = get(index);
        archive.add(removedAccount, closeDate);
        // Call parent remove
        super.remove(removedAccount);
        return true;
    }

    /**
     * Remove all accounts with the given profile
     * @param profile the profile to remove accounts for
     * @param closeDate the date of closing
     * @return true if any accounts were removed, false otherwise
     */
    public boolean removeAll(Profile profile, Date closeDate) {
        if (profile == null) return false;
        boolean removed = false;
        // Create a temporary list to store accounts to remove
        // This avoids concurrent modification issues
        List<Account> accountsToRemove = new List<>();
        for (int i = 0; i < size(); i++) {
            Account account = get(i);
            if (account.getHolder().equals(profile)) {
                accountsToRemove.add(account);
            }
        }
        // Remove each account found
        for (Account account : accountsToRemove) {
            remove(account, closeDate);
            removed = true;
        }
        return removed;
    }

    /**
     * Deposit money to an account
     * @param number the account number
     * @param amount the amount to deposit
     */
    public void deposit(AccountNumber number, double amount) {
        Account account = getAccount(number);
        if (account == null || amount <= 0) return;
        account.deposit(amount);
        // Update loyalty status for MoneyMarket
        if (account instanceof MoneyMarket) {
            MoneyMarket mm = (MoneyMarket) account;
            if (mm.getBalance() >= 5000) {
                mm.setLoyal(true);
            }
        }
    }

    /**
     * Withdraw money from an account
     * @param number the account number
     * @param amount the amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdraw(AccountNumber number, double amount) {
        Account account = getAccount(number);
        if (account == null || amount <= 0) return false;
        if (account.getBalance() < amount) {
            return false; // Insufficient funds
        }
        // Special handling for Money Market
        if (account instanceof MoneyMarket) {
            MoneyMarket mm = (MoneyMarket) account;
            mm.withdraw(amount);
            return true;
        } else {
            account.withdraw(amount);
            return true;
        }
    }

    /**
     * Print closed accounts in the archive
     */
    public String printArchive() {
        StringBuilder output = new StringBuilder();
        if (isEmpty()) {
            output.append("Account database is empty!\n");
            return output.toString();
        }
        output.append("*List of closed accounts in the archive.");
        archive.print();
        output.append("*end of list.");
        return output.toString();
    }

    /**
     * Print account statements for all accounts
     */
    public String printStatements() {
        StringBuilder output = new StringBuilder();
        if (isEmpty()) {
            output.append("Account database is empty!\n");
            return output.toString();
        }
        output.append("*Account statements by account holder.");
        // Create array of accounts for sorting
        Account[] accountArray = new Account[size()];
        int index = 0;
        for (Account account : this) {
            accountArray[index++] = account;
        }
        Sort.byHolder(accountArray); // Sort by holder
        // Group by holder
        String currentHolder = null;
        int statementNumber = 1;
        for (Account account : accountArray) {
            String holderName = account.getHolder().toString();
            if (!holderName.equalsIgnoreCase(currentHolder)) {
                if (currentHolder != null) {
                    output.append(" ");
                }
                output.append(statementNumber + "." + holderName);
                currentHolder = holderName;
                statementNumber++;
            }
            output.append("\t[Account#] " + account.getNumber());
            account.statement();
        }
        output.append("\n*end of statements.");
        return output.toString();
    }

    /**
     * Load a list of existing accounts from a text file
     * @param file
     * @throws IOException
     */
    public void loadAccounts(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("Cannot find file: " + file.getAbsolutePath());
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] tokens = line.split(",");
            if (tokens.length < 5) {
                continue;
            }
            try {
                // Parse common account data
                String accountTypeStr = tokens[0].trim().toUpperCase();
                String branchStr = tokens[1].trim().toUpperCase();
                String firstName = tokens[2].trim();
                String lastName = tokens[3].trim();
                String dobStr = tokens[4].trim();
                double balance = Double.parseDouble(tokens[5].trim());
                // Parse DOB
                String[] dateParts = dobStr.split("/");
                int month = Integer.parseInt(dateParts[0]);
                int day = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                Date dob = new Date(month, day, year);
                // Create profile
                Profile profile = new Profile(firstName, lastName, dob);
                // Find branch
                Branch branch = Branch.valueOf(branchStr);
                // Determine account type and create appropriate account
                AccountType accountType;
                Account account = null;
                switch (accountTypeStr) {
                    case "CHECKING":
                        accountType = AccountType.CHECKING;
                        AccountNumber checkingNumber = new AccountNumber(branch, accountType);
                        account = new Checking(checkingNumber, profile, balance);
                        break;
                    case "COLLEGE":
                        accountType = AccountType.COLLEGE_CHECKING;
                        int campus = Integer.parseInt(tokens[6].trim());
                        Campus campusEnum = null;
                        if (campus == 1) campusEnum = Campus.NEW_BRUNSWICK;
                        else if (campus == 2) campusEnum = Campus.NEWARK;
                        else if (campus == 3) campusEnum = Campus.CAMDEN;
                        AccountNumber collegeNumber = new AccountNumber(branch, accountType);
                        account = new CollegeChecking(collegeNumber, profile, balance, campusEnum);
                        break;
                    case "SAVINGS":
                        accountType = AccountType.SAVINGS;
                        AccountNumber savingsNumber = new AccountNumber(branch, accountType);
                        account = new Savings(savingsNumber, profile, balance);
                        if (checkLoyaltyEligibility(profile)) {
                            ((Savings) account).setLoyal(true);
                        }
                        break;
                    case "MONEYMARKET":
                        accountType = AccountType.MONEY_MARKET;
                        AccountNumber moneyMarketNumber = new AccountNumber(branch, accountType);
                        MoneyMarket mmAccount = new MoneyMarket(moneyMarketNumber, profile, balance);
                        // Set loyalty based on balance
                        mmAccount.setLoyal(balance >= 5000);
                        account = mmAccount;
                        break;
                    case "CERTIFICATE":
                        accountType = AccountType.CD;
                        int term = Integer.parseInt(tokens[6].trim());
                        String openDateStr = tokens[7].trim();
                        String[] openDateParts = openDateStr.split("/");
                        int openMonth = Integer.parseInt(openDateParts[0]);
                        int openDay = Integer.parseInt(openDateParts[1]);
                        int openYear = Integer.parseInt(openDateParts[2]);
                        Date openDate = new Date(openMonth, openDay, openYear);
                        AccountNumber cdNumber = new AccountNumber(branch, accountType);
                        account = new CertificateDeposit(cdNumber, profile, balance, term, openDate);
                        break;
                }
                if (account != null) {
                    add(account);
                }
            } catch (Exception e) {
                // Skip invalid entries
                System.out.println("Error processing account: " + e.getMessage());
            }
        }
        scanner.close();
        System.out.println("Accounts in \"accounts.txt\" loaded to the database.");
    }

    /**
     * Process account activities from a text file
     * @param file the file to process
     * @throws IOException if file not found
     */
    public void processActivities(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("Cannot find file: " + file.getAbsolutePath());
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] tokens = line.split(",");
            if (tokens.length < 5) {
                continue;
            }
            try {
                char type = tokens[0].trim().charAt(0);
                String accountNumber = tokens[1].trim();
                String dateStr = tokens[2].trim();
                String locationStr = tokens[3].trim().toUpperCase();
                double amount = Double.parseDouble(tokens[4].trim());
                // Parse date
                String[] dateParts = dateStr.split("/");
                int month = Integer.parseInt(dateParts[0]);
                int day = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                Date date = new Date(month, day, year);
                // Find branch
                Branch branch = Branch.valueOf(locationStr);
                // Find account
                Account account = getAccountByNumber(accountNumber);
                if (account == null) {
                    System.out.println(accountNumber + " does not exist.");
                    continue;
                }
                // Create activity
                Activity activity = new Activity(date, branch, type, amount, true);
                // Process activity
                if (type == 'D') {
                    account.deposit(amount);
                } else if (type == 'W') {
                    if (account.getBalance() < amount) {
                        System.out.println("Insufficient funds for withdrawal from " + accountNumber);
                        continue;
                    }
                    if (account instanceof MoneyMarket) {
                        MoneyMarket mm = (MoneyMarket) account;
                        mm.withdraw(amount);
                    } else {
                        account.withdraw(amount);
                    }
                }
                // Add activity to account
                account.addActivity(activity);
                // Print activity
                System.out.println(accountNumber + "::" + activity.toString());
            } catch (Exception e) {
                // Skip invalid entries
                System.out.println("Error processing activity: " + e.getMessage());
            }
        }
        scanner.close();
        System.out.println("Account activities in \"activities.txt\" processed.");
    }

    /**
     * Checks if a profile already has an account of the specified type
     * @param profile the profile to check
     * @param accountType the account type code to check
     * @return true if profile has this account type, false otherwise
     */
    public boolean containsSameAccountType(Profile profile, String accountType) {
        if (profile == null || accountType == null) return false;
        for (int i = 0; i < size(); i++) {
            Account account = get(i);
            if (account.getHolder().equals(profile) && account.getAccountType().equals(accountType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a profile already has an account of the specified type
     * @param profile the profile to check
     * @param accountType the account type code to check
     * @return true if profile has this account type, false otherwise
     */
    public boolean containsSameAccountType(Profile profile, String accountType, int term) {
        if (profile == null || accountType == null) return false;
        for (int i = 0; i < size(); i++) {
            Account account = get(i);
            if (account.getAccountType().equals("05")){
                CertificateDeposit cd = (CertificateDeposit) account;
                if (cd.getHolder().equals(profile) && cd.getAccountType().equals(accountType) && cd.getTerm() == term) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates loyalty status for all Savings accounts of the given profile
     * @param profile the profile to update
     * @param isLoyal the loyalty status to set
     */
    public void updateLoyaltyForSavings(Profile profile, boolean isLoyal) {
        if (profile == null) return;
        for (int i = 0; i < size(); i++) {
            Account account = get(i);
            if (account.getHolder().equals(profile) && account instanceof Savings 
                    && !(account instanceof MoneyMarket)) {
                ((Savings) account).setLoyal(isLoyal);
            }
        }
    }

    /**
     * Checks if the profile has a checking account (regular or college)
     * @param profile the profile to check
     * @return true if profile has a checking account, false otherwise
     */
    public boolean holderHasCheckingAccount(Profile profile) {
        if (profile == null) return false;
        for (int i = 0; i < size(); i++) {
            Account account = get(i);
            if (account.getHolder().equals(profile) && (account instanceof Checking)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the account holder would receive a loyalty bonus if they opened a Savings account
     * @param profile the profile to check
     * @return true if the holder would get loyalty status, false otherwise
     */
    public boolean checkLoyaltyEligibility(Profile profile) {
        return holderHasCheckingAccount(profile);
    }

    /**
     * Print all accounts in the database
     */
    public void printAll() {
        if (isEmpty()) {
            System.out.println("Account database is empty!");
            return;
        }
        System.out.println("*List of accounts in the account database.");
        for (Account account : this) {
            System.out.println(account);
        }
        System.out.println("*end of list.");
    }

    /**
     * Print accounts ordered by account holder
     */
    public String printByHolder() {
        StringBuilder output = new StringBuilder();
        if (isEmpty()) {
            output.append("Account database is empty!\n");
            return output.toString();
        }
        output.append("*List of accounts ordered by account holder and number.");
        // Create array of accounts for sorting
        Account[] accountArray = new Account[size()];
        int index = 0;
        for (Account account : this) {
            accountArray[index++] = account;
        }
        // Use Sort utility
        Sort.byHolder(accountArray);
        // Print sorted accounts
        for (Account account : accountArray) {
            output.append(account);
        }
        output.append("*end of list.");
        return output.toString();
    }

    /**
     * Print accounts ordered by account type
     */
    public String printByType() {
        StringBuilder output = new StringBuilder();
        if (isEmpty()) {
            output.append("Account database is empty!\n");
            return output.toString();
        }
        output.append("*List of accounts ordered by account type and number.");
        // Create array of accounts for sorting
        Account[] accountArray = new Account[size()];
        int index = 0;
        for (Account account : this) {
            accountArray[index++] = account;
        }
        // Use Sort utility
        Sort.byAccountType(accountArray);
        // Print accounts by type
        String currentType = null;
        for (Account account : accountArray) {
            String accountType = account.getAccountType();
            if (!accountType.equals(currentType)) {
                output.append("Account Type: " + account.getNumber().getType().getName());
                currentType = accountType;
            }
            output.append(account);
        }
        output.append("*end of list.");
        return output.toString();
    }

    /**
     * Print accounts ordered by branch location
     */
    public String printByBranch() {
        StringBuilder output = new StringBuilder();
        if (isEmpty()) {
            output.append("Account database is empty!\n");
            return output.toString();
        }
        output.append("*List of accounts ordered by branch location (county, city).");
        // Create array of accounts for sorting
        Account[] accountArray = new Account[size()];
        int index = 0;
        for (Account account : this) {
            accountArray[index++] = account;
        }
        // Use Sort utility
        Sort.byBranch(accountArray);
        // Print accounts by branch
        String currentCounty = null;
        for (Account account : accountArray) {
            String county = account.getNumber().getBranch().getCounty();
            if (!county.equals(currentCounty)) {
                output.append("County: " + county);
                currentCounty = county;
            }
            output.append(account);
        }        
        output.append("*end of list.");
        return output.toString();
    }

    /**
     * Calculate interest for account closing
     * @param account the account to calculate interest for
     * @param closeDate the date of closing
     * @return the interest amount
     */
    public double calculateClosingInterest(Account account, Date closeDate) {
        if (account instanceof CertificateDeposit) {
            CertificateDeposit cd = (CertificateDeposit) account;
            return cd.calculateEarlyWithdrawalInterest(closeDate);
        } else {
            // For regular accounts, calculate interest from beginning of month
            int dayOfMonth = closeDate.getDay();
            double annualRate = 0;
            if (account instanceof Checking) {
                annualRate = 0.015; // 1.5%
            } else if (account instanceof MoneyMarket) {
                annualRate = 0.035; // 2.5%
                if (((MoneyMarket) account).isLoyal()) {
                    annualRate += 0.0025; // Loyalty bonus 0.25%
                }
            } else if (account instanceof Savings) {
                annualRate = 0.025; // 3.5%
                if (((Savings) account).isLoyal()) {
                    annualRate += 0.0025; // Loyalty bonus 0.25%
                }
            }
            return account.getBalance() * annualRate / 365 * dayOfMonth;
        }
    }

    /**
     * Calculate CD penalty for early withdrawal
     * @param account the CD account
     * @param interest the calculated interest
     * @param closeDate the date of closing
     * @return the penalty amount, or 0 if not applicable
     */
    public double calculateCDPenalty(Account account, double interest, Date closeDate) {
        if (account instanceof CertificateDeposit) {
            CertificateDeposit cd = (CertificateDeposit) account;
            Date maturityDate = cd.getMaturityDate();
            // Check if closed before maturity
            if (closeDate.compareTo(maturityDate) < 0) {
                return cd.calculateEarlyWithdrawalPenalty(interest);
            }
        }
        return 0;
    }

    /**
     * Close an account and move to archive
     * @param accountNumber the account number
     * @param closeDate the date of closing
     * @return true if closed, false if account not found
     */
    public String closeAccount(String accountNumber, Date closeDate) {
        StringBuilder output = new StringBuilder();
        Account account = getAccountByNumber(accountNumber);
        if (account == null) {
            output.append(accountNumber + " account does not exist.");
            return output.toString();
        }
        output.append("Closing account ").append(account.getNumber()).append("\n");
        // Calculate interest
        double interest = calculateClosingInterest(account, closeDate);
        output.append(String.format("--interest earned: $%.2f\n", interest));
        // Calculate penalty for CD accounts
        if (account instanceof CertificateDeposit) {
            double penalty = calculateCDPenalty(account, interest, closeDate);
            if (penalty > 0) {
                output.append(String.format("--penalty: $%.2f\n", penalty));
            }
        }
        if (account.getAccountType().equals("01")) {
            Profile holder = account.getHolder();
            updateLoyaltyForSavings(holder, false);
        }
        // Remove account and add to archive
        remove(account, closeDate);
        return output.toString();
    }

    /**
     * Close all accounts for a profile
     * @param profile the profile
     * @param closeDate the date of closing
     * @return true if any accounts were closed, false otherwise
     */
    public String closeAllAccounts(Profile profile, Date closeDate) {
        StringBuilder output = new StringBuilder();
        if (profile == null) {
            output.append("Profile does not exist.");
            return output.toString();
        }
        boolean closed = false;
        List<Account> accountsToClose = new List<>();
        for (Account account : this) { // Find all accounts for the profile
            if (account.getHolder().equals(profile)) {
                accountsToClose.add(account);
            }
        }
        if (!accountsToClose.isEmpty()) {
            output.append("Closing accounts for ").append(profile).append("\n");
        }
        for (Account account : accountsToClose) { // Close each account
            double interest = calculateClosingInterest(account, closeDate); // Calculate interest
            output.append(String.format("--%s interest earned: $%.2f\n", account.getNumber(), interest));
            if (account instanceof CertificateDeposit) { // Calculate penalty for CD accounts
                double penalty = calculateCDPenalty(account, interest, closeDate);
                if (penalty > 0) {
                    output.append(String.format("[penalty] $%.2f\n", penalty));
                }
            }
            remove(account, closeDate); // Remove account and add to archive
            closed = true;
        }
        if (closed) {
            output.append("All accounts for " + profile + " are closed and moved to archive.");
        } else {
            output.append(profile + " does not have any accounts in the database.");
        }
        return output.toString();
    }
}
