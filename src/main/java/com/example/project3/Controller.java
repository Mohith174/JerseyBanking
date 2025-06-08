package com.example.project3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.StringTokenizer;

import com.example.project3.rubank.account.Account;
import com.example.project3.rubank.account.AccountDatabase;
import com.example.project3.rubank.account.AccountNode;
import com.example.project3.rubank.account.AccountNumber;
import com.example.project3.rubank.account.AccountType;
import com.example.project3.rubank.account.Activity;
import com.example.project3.rubank.account.CertificateDeposit;
import com.example.project3.rubank.account.Checking;
import com.example.project3.rubank.account.CollegeChecking;
import com.example.project3.rubank.account.MoneyMarket;
import com.example.project3.rubank.account.Savings;
import com.example.project3.rubank.banking.Archive;
import com.example.project3.rubank.banking.Branch;
import com.example.project3.rubank.banking.Campus;
import com.example.project3.rubank.banking.Profile;
import com.example.project3.util.Date;

public class Controller {

    private static final int MM_INITIAL_DEPO = 2000;
    private static final int CD_INITIAL_DEPO = 1000;

    @FXML
    private Tab OpenNewTab;

    @FXML
    private AnchorPane accountDBAnchor;

    @FXML
    private GridPane accountDBGrid;

    @FXML
    private Tab accountDatabaseTab;

    @FXML
    private Label accountNumberLabel;

    @FXML
    private TextField accountNumberText;

    @FXML
    private GridPane accountTypeGrid;

    @FXML
    private Label accountTypeLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private TextField amountText;

    @FXML
    private ComboBox<?> branchComboBox;

    @FXML
    private HBox branchHbox;

    @FXML
    private RadioButton camdenRB;

    @FXML
    private HBox campusHBox;

    @FXML
    private Label campusLabel;

    @FXML
    private VBox campusVBox;

    @FXML
    private Button cancelButton;

    @FXML
    private RadioButton ccRB;

    @FXML
    private RadioButton cdRB;

    @FXML
    private RadioButton checkingRB;

    @FXML
    private Button closeAccountButton;

    @FXML
    private GridPane closeAccountGrid;

    @FXML
    private Button closeAllButton;

    @FXML
    private Tab closeDepositWithdrawTab;

    @FXML
    private DatePicker closingDOBDatePicker;

    @FXML
    private Label closingDOBLabel;

    @FXML
    private DatePicker closingDateDatePicker;

    @FXML
    private Label closingDateLabel;

    @FXML
    private Label closingFirstNLabel;

    @FXML
    private TextField closingFirstNText;

    @FXML
    private HBox closingHBox;

    @FXML
    private Label closingLastNLabel;

    @FXML
    private TextField closingLastNText;

    @FXML
    private DatePicker dateOpenedDatePicker;

    @FXML
    private HBox dateOpenedHBox;

    @FXML
    private Label dateOpenedLabel;

    @FXML
    private Button depositButton;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private Label dobLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Label initialDepositLabel;

    @FXML
    private TextField initialDepositText;

    @FXML
    private Label lastNameLabel;

    @FXML
    private TextField lastNameText;

    @FXML
    private Button loadAccountFileButton;

    @FXML
    private Button loadActivitesFileButton;

    @FXML
    private CheckBox loyalCheckBox;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private RadioButton mmRB;

    @FXML
    private RadioButton nbRB;

    @FXML
    private RadioButton newarkRB;

    @FXML
    private Button openButton;

    @FXML
    private AnchorPane openNewAnchorMain;

    @FXML
    private AnchorPane openNewAnchorwithinAnchor;

    @FXML
    private GridPane openNewMainGrid;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button printArchiveButton;

    @FXML
    private Button printBranchButton;

    @FXML
    private Button printHolderButton;

    @FXML
    private Button printStatementButton;

    @FXML
    private Button printTypeButton;

    @FXML
    private ToggleGroup rb_campus;

    @FXML
    private ToggleGroup rb_types;

    @FXML
    private BorderPane rootPanel;

    @FXML
    private RadioButton savingRB;

    @FXML
    private ComboBox<?> termComboBox;

    @FXML
    private Button withdrawButton;

    private AccountDatabase accountDatabase;

    /**
     * Initialize the controller - called automatically by FXMLLoader
     */
    @FXML
    private void initialize() {
        accountDatabase = new AccountDatabase();
        setupInitialUI();
    }

    /**
     * Set up the initial UI state
     */
    private void setupInitialUI() {
        setupBranchComboBox();
        setupComboBox();
        setupToggleGroups();
        updateUIBasedOnAccountType();
        outputTextArea.setEditable(false);
    }

    /**
     * Set up the branch ComboBox
     */
    private void setupBranchComboBox() {
        ComboBox<String> branchBox = (ComboBox<String>) branchComboBox;
        branchBox.setItems(FXCollections.observableArrayList(
                "EDISON", "BRIDGEWATER", "PRINCETON", "PISCATAWAY", "WARREN"
        ));
        branchBox.getSelectionModel().selectFirst();
    }

    /**
     * Set up the CD term ComboBox
     */
    private void setupComboBox() {
        ComboBox<String> termBox = (ComboBox<String>) termComboBox;
        termBox.setItems(FXCollections.observableArrayList(
                "3", "6", "9", "12"
        ));
        termBox.getSelectionModel().selectFirst();
    }

    /**
     * Set up toggle groups and radio button event handlers
     */
    private void setupToggleGroups() {
        if (rb_types == null) {
            rb_types = new ToggleGroup();
            checkingRB.setToggleGroup(rb_types);
            ccRB.setToggleGroup(rb_types);
            savingRB.setToggleGroup(rb_types);
            mmRB.setToggleGroup(rb_types);
            cdRB.setToggleGroup(rb_types);
        }

        if (rb_campus == null) {
            rb_campus = new ToggleGroup();
            nbRB.setToggleGroup(rb_campus);
            newarkRB.setToggleGroup(rb_campus);
            camdenRB.setToggleGroup(rb_campus);
        }

        checkingRB.setSelected(true);
        nbRB.setSelected(true);

        rb_types.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            updateUIBasedOnAccountType();
        });
    }

    /**
     * Update UI visibility based on selected account type
     */
    private void updateUIBasedOnAccountType() {
        campusVBox.setVisible(false);
        campusVBox.setManaged(false);
        dateOpenedHBox.setVisible(false);
        dateOpenedHBox.setManaged(false);
        termComboBox.setVisible(false);
        termComboBox.setManaged(false);
        loyalCheckBox.setVisible(false);

        if (ccRB.isSelected()) {
            campusVBox.setVisible(true);
            campusVBox.setManaged(true);
        } else if (cdRB.isSelected()) {
            dateOpenedHBox.setVisible(true);
            dateOpenedHBox.setManaged(true);
            termComboBox.setVisible(true);
            termComboBox.setManaged(true);
        } else if (savingRB.isSelected() || mmRB.isSelected()) {
            loyalCheckBox.setVisible(true);
        }
    }

    /**
     * Handle open account button click
     */
    @FXML
    private void handleOpenAccount() {
        try {
            outputTextArea.clear();
            AccountType accountType = getSelectedAccountType();
            String branchStr = ((ComboBox<String>)branchComboBox).getValue();
            Branch branch = Branch.valueOf(branchStr);
            if (branch == null) return;
            String firstName = firstNameTextField.getText().trim();
            String lastName = lastNameText.getText().trim();
            if (firstName.isEmpty() || lastName.isEmpty()) {
                outputTextArea.setText("Error: First name and last name are required.");
                return;
            }
            LocalDate localDob = dobDatePicker.getValue();
            if (localDob == null) {
                outputTextArea.setText("Error: Date of birth is required.");
                return;
            }
            Date dob = new Date(localDob.getMonthValue(), localDob.getDayOfMonth(), localDob.getYear());
            if (!dob.isValid()) {
                outputTextArea.setText("DOB invalid: " + dob + " not a valid calendar date!");
                return;
            }
            if (!isEighteenOrOlder(dob)) {
                outputTextArea.setText("Not eligible to open: " + dob + " under 18.");
                return;
            }
            Profile profile = new Profile(firstName, lastName, dob);
            double initialDeposit;
            try {
                initialDeposit = Double.parseDouble(initialDepositText.getText().trim());
                if (initialDeposit <= 0) {
                    outputTextArea.setText("Initial deposit cannot be 0 or negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                outputTextArea.setText("For input string: \"" + initialDepositText.getText().trim() + "\" - not a valid amount.");
                return;
            }
            Account account = createAccountByType(accountType, branch, profile, initialDeposit);
            if (account == null) return;
            accountDatabase.add(account);
            outputTextArea.setText(accountType.getName() + " account " + account.getNumber() + " has been opened.");
            if (accountType.equals(AccountType.CHECKING)) {
                accountDatabase.updateLoyaltyForSavings(profile, true);
            }
            clearInputFields();
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Get the selected account type from radio buttons
     */
    private AccountType getSelectedAccountType() {
        if (checkingRB.isSelected()) {
            return AccountType.CHECKING;
        } else if (ccRB.isSelected()) {
            return AccountType.COLLEGE_CHECKING;
        } else if (savingRB.isSelected()) {
            return AccountType.SAVINGS;
        } else if (mmRB.isSelected()) {
            return AccountType.MONEY_MARKET;
        } else if (cdRB.isSelected()) {
            return AccountType.CD;
        }
        outputTextArea.setText("Error: No account type selected.");
        return null;
    }

    /**
     * Checks if a person is 18 years or older
     * @param dob the date of birth
     * @return true if 18 or older, false otherwise
     */
    private boolean isEighteenOrOlder(Date dob) {
        // Current date (could be replaced with actual current date)
        Date currentDate = getCurrentDate();
        // Check if at least 18 years old
        if (currentDate.getYear() - dob.getYear() > 18) {
            return true;
        } else if (currentDate.getYear() - dob.getYear() == 18) {
            // Check month and day
            if (currentDate.getMonth() > dob.getMonth()) {
                return true;
            } else if (currentDate.getMonth() == dob.getMonth()) {
                return currentDate.getDay() >= dob.getDay();
            }
        }
        return false;
    }

    /**
     * Gets the current date
     * @return the current date
     */
    private Date getCurrentDate() {
        // Use Feb 14, 2025 to match the sample output
        // This is the date specified in the project specs
        // return new Date(2, 14, 2025); // February 14, 2025
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new Date(month, day, year);
    }

    /**
     * Helper function to create an account based on the account type
     * @param accountType the account type
     * @param branch the branch
     * @param profile the profile
     * @param initialDeposit the initial deposit
     * @return the created account, or null if invalid
     */
    private Account createAccountByType(
            AccountType accountType,
            Branch branch,
            Profile profile,
            double initialDeposit
    ) {
        Account account = null;
        switch (accountType) {
            case CHECKING:
                account = createCheckingAccount(accountType, branch, profile, initialDeposit);
                break;
            case COLLEGE_CHECKING:
                Campus campus = getSelectedCampus();
                account = createCollegeCheckingAccount(accountType, branch, profile, initialDeposit, campus);
                break;
            case SAVINGS:
                account = createSavingsAccount(accountType, branch, profile, initialDeposit);
                break;
            case MONEY_MARKET:
                account = createMoneyMarketAccount(accountType, branch, profile, initialDeposit);
                break;
            case CD:
                int term = getSelectedTerm();
                Date openingDate = getOpenDate();
                account = createCertificateDepositAccount(accountType, branch, profile, initialDeposit, term, openingDate);
                break;
        }
        return account;
    }

    /**
     * Creates a checking account
     * @param accountType the account type
     * @param branch the branch
     * @param profile the profile
     * @param initialDeposit the initial deposit
     * @return the created account, or null if invalid
     */
    private Account createCheckingAccount(
            AccountType accountType,
            Branch branch,
            Profile profile,
            double initialDeposit
    ) {
        if (accountDatabase.containsSameAccountType(profile, accountType.getCode())) {
            outputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
            return null;
        }
        AccountNumber number = new AccountNumber(branch, accountType);
        return new Checking(number, profile, initialDeposit);
    }

    /**
     * Selecting Campus
     */
    private Campus getSelectedCampus() {
        if(nbRB.isSelected()) {
            return Campus.NEW_BRUNSWICK;
        } else if (newarkRB.isSelected()) {
            return Campus.NEWARK;
        } else if (camdenRB.isSelected()) {
            return Campus.CAMDEN;
        }
        outputTextArea.setText("Error: No campus selected.");
        return null;
    }

    /**
     * Creates a college checking account
     * @param accountType the account type
     * @param branch the branch
     * @param profile the profile
     * @param initialDeposit the initial deposit
     * @param campus Campus name
     * @return the created account, or null if invalid
     */
    private Account createCollegeCheckingAccount(
            AccountType accountType,
            Branch branch,
            Profile profile,
            double initialDeposit,
            Campus campus
    ) {
        if (accountDatabase.containsSameAccountType(profile, accountType.getCode())) {
            outputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
            return null;
        }
        if (!isEligibleForCollegeChecking(profile.getDob())) {
            outputTextArea.setText("Not eligible to open: " + profile.getDob() + " over 24.");
            return null;
        }
        if (campus == null) {
            outputTextArea.setText("Invalid campus code.");
            return null;
        }
        AccountNumber number = new AccountNumber(branch, accountType);
        return new CollegeChecking(number, profile, initialDeposit, campus);
    }

    /**
     * Checks if a person is 24 years or younger
     * @param dob the date of birth
     * @return true if 24 or younger, false otherwise
     */
    private boolean isEligibleForCollegeChecking(Date dob) {
        // Current date (could be replaced with actual current date)
        Date currentDate = getCurrentDate();
        // Check if at most 24 years old
        if (currentDate.getYear() - dob.getYear() < 24) {
            return true;
        } else if (currentDate.getYear() - dob.getYear() == 24) {
            // Check month and day
            if (currentDate.getMonth() < dob.getMonth()) {
                return true;
            } else if (currentDate.getMonth() == dob.getMonth()) {
                return currentDate.getDay() <= dob.getDay();
            }
        }
        return false;
    }

    /**
     * Creates a savings account
     * @param accountType the account type
     * @param branch the branch
     * @param profile the profile
     * @param initialDeposit the initial deposit
     * @return the created account, or null if invalid
     */
    private Account createSavingsAccount(
            AccountType accountType,
            Branch branch,
            Profile profile,
            double initialDeposit
    ) {
        if (accountDatabase.containsSameAccountType(profile, accountType.getCode())) {
            outputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
            return null;
        }
        AccountNumber number = new AccountNumber(branch, accountType);
        Savings account = new Savings(number, profile, initialDeposit);
        if (accountDatabase.holderHasCheckingAccount(profile)) {
            account.setLoyal(true);
        }
        return account;
    }

    /**
     * Creates a money market account
     * @param accountType the account type
     * @param branch the branch
     * @param profile the profile
     * @param initialDeposit the initial deposit
     * @return the created account, or null if invalid
     */
    private Account createMoneyMarketAccount(
            AccountType accountType,
            Branch branch,
            Profile profile,
            double initialDeposit
    ) {
        if (accountDatabase.containsSameAccountType(profile, accountType.getCode())) {
            outputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
            return null;
        }
        if (initialDeposit < MM_INITIAL_DEPO) {
            outputTextArea.setText("Minimum of $2,000 to open a Money Market account.");
            return null;
        }
        AccountNumber number = new AccountNumber(branch, accountType);
        MoneyMarket account = new MoneyMarket(number, profile, initialDeposit);
        account.setLoyal(initialDeposit >= 5000);
        return account;
    }

    /**
     * Gets selected term
     */
    private int getSelectedTerm() {
        String strterm = ((ComboBox<String>)termComboBox).getValue();
        int term = Integer.parseInt(strterm);
        return term;
    }

    /**
     * Gets Open Date
     */
    private Date getOpenDate() {
        LocalDate localOpenDate = dateOpenedDatePicker.getValue();
        Date openDate = new Date(localOpenDate.getMonthValue(),
                localOpenDate.getDayOfMonth(),
                localOpenDate.getYear());
        return openDate;
    }
    /**
     * Creates a certificate deposit account
     * @param accountType the account type
     * @param branch the branch
     * @param profile the profile
     * @param initialDeposit the initial deposit
     * @param term int term
     * @return the created account, or null if invalid
     */
    private Account createCertificateDepositAccount(
            AccountType accountType,
            Branch branch,
            Profile profile,
            double initialDeposit,
            int term,
            Date openedDate
    ) {
        if (term <= 0) {
            outputTextArea.setText(term + " is not a valid term.");
            return null;
        }
        if (accountDatabase.containsSameAccountType(profile, accountType.getCode(), term)) {
            outputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
            return null;
        }
        if (initialDeposit < CD_INITIAL_DEPO) {
            outputTextArea.setText("Minimum of $1,000 to open a Certificate Deposit account.");
            return null;
        }
        if (openedDate == null || !openedDate.isValid()) {
            outputTextArea.setText("CD opening date " + openedDate.toString() + " is invalid.");
            return null;
        }
        AccountNumber number = new AccountNumber(branch, accountType);
        return new CertificateDeposit(number, profile, initialDeposit, term, openedDate);
    }

    /**
     * Handle deposit button click
     */
    @FXML
    private void handleDeposit() {
        try {
            outputTextArea.clear();
            String accountNumberStr = accountNumberText.getText().trim();
            if (accountNumberStr.isEmpty()) {
                outputTextArea.setText("Error: Account number is required.");
                return;
            }
            Account account = accountDatabase.getAccountByNumber(accountNumberStr);
            if (account == null) {
                outputTextArea.setText(accountNumberStr + " does not exist.");
                return;
            }
            if (account instanceof CertificateDeposit) {
                outputTextArea.setText("Cannot deposit to a CD account.");
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(amountText.getText().trim());
                if (amount <= 0) {
                    outputTextArea.setText(amount + " - deposit amount cannot be 0 or negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                outputTextArea.setText("For input string: \"" + amountText.getText().trim() + "\" - not a valid amount.");
                return;
            }
            Date today = getCurrentDate();
            Branch branch = account.getNumber().getBranch();
            Activity activity = new Activity(today, branch, 'D', amount, false);
            accountDatabase.deposit(account.getNumber(), amount);
            account.addActivity(activity);
            outputTextArea.setText("$" + String.format("%,.2f", amount) + " deposited to " + account.getNumber());
            amountText.clear();
            accountNumberText.clear();
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle withdraw button click
     */
    @FXML
    private void handleWithdraw() {
        try {
            outputTextArea.clear();
            String accountNumberStr = accountNumberText.getText().trim();
            if (accountNumberStr.isEmpty()) {
                outputTextArea.setText("Error: Account number is required.");
                return;
            }
            Account account = accountDatabase.getAccountByNumber(accountNumberStr);
            if (account == null) {
                outputTextArea.setText(accountNumberStr + " does not exist.");
                return;
            }
            if (account instanceof CertificateDeposit) {
                outputTextArea.setText("Cannot withdraw from a CD account.");
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(amountText.getText().trim());
                if (amount <= 0) {
                    outputTextArea.setText(amount + " withdrawal amount cannot be 0 or negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                outputTextArea.setText("For input string: \"" + amountText.getText().trim() + "\" - not a valid amount.");
                return;
            }
            if (account.getBalance() < amount && account.getAccountType().equals("03")) {
                outputTextArea.setText(accountNumberStr + " balance below $2,000 - withdrawing $" +
                        String.format("%,.2f", amount) + " - insufficient funds.");
                return;
            }
            boolean success = accountDatabase.withdraw(account.getNumber(), amount);
            if (!success) {
                outputTextArea.setText(amount + " not withdrawn from " + accountNumberStr);
                return;
            }
            Date today = getCurrentDate();
            Branch branch = account.getNumber().getBranch();
            Activity activity = new Activity(today, branch, 'W', amount, false);
            account.addActivity(activity);
            if (account.getBalance() < 2000 && account.getAccountType().equals("03")) {
                outputTextArea.setText(accountNumberStr + " balance below $2,000 - $" +
                        String.format("%,.2f", amount) + " withdrawn from " + accountNumberStr);
            } else {
                outputTextArea.setText("$" + String.format("%,.2f", amount) + " withdrawn from " + accountNumberStr);
            }
            amountText.clear();
            accountNumberText.clear();
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle close account button click
     */
    @FXML
    private void handleCloseAccount() {
        try {
            outputTextArea.clear();
            LocalDate closingLocalDate = closingDateDatePicker.getValue();
            if (closingLocalDate == null) {
                outputTextArea.setText("Error: Closing date is required.");
                return;
            }
            Date closeDate = new Date(
                    closingLocalDate.getMonthValue(),
                    closingLocalDate.getDayOfMonth(),
                    closingLocalDate.getYear()
            );
            if (!closeDate.isValid()) {
                outputTextArea.setText("Invalid closing date.");
                return;
            }
            String accountNumberStr = accountNumberText.getText().trim();
            if (accountNumberStr.isEmpty()) {
                outputTextArea.setText("Error: Account number is required.");
                return;
            }
            String result = accountDatabase.closeAccount(accountNumberStr, closeDate);
            outputTextArea.setText(result);
            accountNumberText.clear();
            closingDateDatePicker.setValue(null);
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle close all accounts button click
     */
    @FXML
    private void handleCloseAllAccounts() {
        try {
            outputTextArea.clear();
            LocalDate closingLocalDate = closingDateDatePicker.getValue();
            if (closingLocalDate == null) {
                outputTextArea.setText("Error: Closing date is required.");
                return;
            }
            Date closeDate = new Date(
                    closingLocalDate.getMonthValue(),
                    closingLocalDate.getDayOfMonth(),
                    closingLocalDate.getYear()
            );
            if (!closeDate.isValid()) {
                outputTextArea.setText("Invalid closing date.");
                return;
            }
            String firstName = closingFirstNText.getText().trim();
            String lastName = closingLastNText.getText().trim();
            if (firstName.isEmpty()) {
                outputTextArea.setText("Error: First name is required.");
                return;
            }
            if (lastName.isEmpty()) {
                outputTextArea.setText("Error: Last name is required.");
                return;
            }
            LocalDate dobLocalDate = closingDOBDatePicker.getValue();
            if (dobLocalDate == null) {
                outputTextArea.setText("Error: Date of birth is required.");
                return;
            }
            Date dob = new Date(
                    dobLocalDate.getMonthValue(),
                    dobLocalDate.getDayOfMonth(),
                    dobLocalDate.getYear()
            );
            if (!dob.isValid()) {
                outputTextArea.setText("Date of birth " + dob + " is invalid.");
                return;
            }
            Profile profile = new Profile(firstName, lastName, dob);
            String result = accountDatabase.closeAllAccounts(profile, closeDate);
            outputTextArea.setText(result);
            closingFirstNText.clear();
            closingLastNText.clear();
            closingDOBDatePicker.setValue(null);
            closingDateDatePicker.setValue(null);
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle load accounts file button click
     */
    @FXML
    private void handleLoadAccountsFile() {
        try {
            outputTextArea.clear();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Accounts File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            File initialDirectory = new File("./resources");
            if (initialDirectory.exists()) {
                fileChooser.setInitialDirectory(initialDirectory);
            }
            Stage stage = (Stage) rootPanel.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                try {
                    accountDatabase.loadAccounts(selectedFile);
                    outputTextArea.setText("Accounts in \"" + selectedFile.getName() + "\" loaded to the database.");
                } catch (IOException e) {
                    outputTextArea.setText("Error loading accounts: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle load activities file button click
     */
    @FXML
    private void handleLoadActivitiesFile() {
        try {
            outputTextArea.clear();
            outputTextArea.setText("Select activities file...");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Activities File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            File initialDirectory = new File("./resources");
            if (initialDirectory.exists()) {
                fileChooser.setInitialDirectory(initialDirectory);
            }
            Stage stage = (Stage) rootPanel.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                try {
                    outputTextArea.setText("Processing \"" + selectedFile.getName() + "\"...");
                    accountDatabase.processActivities(selectedFile);
                    outputTextArea.setText("Account activities in \"" + selectedFile.getName() + "\" processed.");
                } catch (IOException e) {
                    outputTextArea.setText("Error processing activities: " + e.getMessage());
                }
            } else {
                outputTextArea.setText("No file selected.");
            }
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle print by holder button click
     */
    @FXML
    private void handlePrintByHolder() {
        try {
            String output = accountDatabase.printByHolder();
            outputTextArea.setText(output);
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle print by type button click
     */
    @FXML
    private void handlePrintByType() {
        try {
            String output = accountDatabase.printByType();
            outputTextArea.setText(output);
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle print by branch button click
     */
    @FXML
    private void handlePrintByBranch() {
        try {
            String output = accountDatabase.printByBranch();
            outputTextArea.setText(output);
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle print statements button click
     */
    @FXML
    private void handlePrintStatements() {
        try {
            String output = accountDatabase.printStatements();
            outputTextArea.setText(output);
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Handle print archive button click
     */
    @FXML
    private void handlePrintArchive() {
        try {
            String output = accountDatabase.printArchive();
            outputTextArea.setText(output);
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Helper method to handle cancel button click - clears all inputs
     */
    @FXML
    private void handleCancel() {
        clearInputFields();
    }

    /**
     * Clear input fields after successful account creation
     */
    private void clearInputFields() {
        firstNameTextField.clear();
        lastNameText.clear();
        dobDatePicker.setValue(null);
        initialDepositText.clear();
        dateOpenedDatePicker.setValue(null);
        loyalCheckBox.setSelected(false);
    }
}