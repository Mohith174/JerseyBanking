# JerseyBanking App

## Project Overview

The JerseyBanking App is a JavaFX-based desktop application designed to simulate basic banking operations. It allows users to manage customer accounts, including opening new accounts, performing transactions like deposits and withdrawals, closing accounts, and viewing account information. The application features a tabbed interface for easy navigation between different functionalities.

## Features

The application provides the following key features:

*   **Open New Account:**
    *   Collect customer details: First Name, Last Name, Date of Birth.
    *   Select account type: Checking, Savings, College Checking, Money Market, CD.
    *   Specify initial deposit amount.
    *   For College Checking accounts, select campus (New Brunswick, Newark, Camden) and indicate if the customer is loyal.
    *   For CD accounts, specify the term.
    *   Select branch for the account.
    *   Set the date the account is opened.
*   **Close Account / Deposit / Withdraw:**
    *   Identify account by Account Number, First Name, Last Name, and Date of Birth.
    *   Perform deposits to an account.
    *   Perform withdrawals from an account.
    *   Close a specific account.
    *   Option to close all accounts for a holder (functionality to be confirmed based on implementation).
*   **Account Database Management:**
    *   Load account data from an external file.
    *   Load account activity/transaction data from an external file.
    *   Print account listings sorted by:
        *   Account Holder
        *   Branch
        *   Account Type
    *   Print account statements.
    *   Print an archive of accounts (presumably closed or all accounts).
*   **User Interface:**
    *   Tabbed navigation for "Open New", "Close Account/Deposit/Withdraw", and "Account Database".
    *   Dedicated text area for displaying output messages, confirmations, and error reports.

## Technologies Used

*   **Java:** Core programming language.
*   **JavaFX:** Framework for the graphical user interface (GUI).
*   **FXML:** XML-based language for defining the JavaFX GUI structure.

## Setup and Installation

To run this project, you will need:

1.  **Java Development Kit (JDK):** Version 11 or higher recommended (as JavaFX is not bundled with JDKs post version 8).
2.  **JavaFX SDK:** Download the JavaFX SDK appropriate for your JDK version and operating system from [GluonHQ](https://gluonhq.com/products/javafx/).
3.  **IDE (Optional but Recommended):** An Integrated Development Environment like IntelliJ IDEA, Eclipse, or NetBeans.

**Configuration Steps (General for IDEs like IntelliJ IDEA):**

1.  Clone or download the project.
2.  Open the project in your IDE.
3.  **Configure JavaFX SDK:**
    *   Add the JavaFX SDK as a global library or a project-specific library. Point the IDE to the `lib` folder of your downloaded JavaFX SDK.
    *   Ensure the JavaFX modules are added to your project's module path.
4.  **VM Options:**
    *   In your run configuration for the `Main` class, add the following VM options:
        ```
        --module-path /path/to/your/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
        ```
        Replace `/path/to/your/javafx-sdk/lib` with the actual absolute path to the `lib` directory of your JavaFX SDK.

## How to Run

1.  Ensure your project is correctly configured with the JavaFX SDK and VM options as described in the Setup section.
2.  Locate the `Main.java` file (usually in `src/main/java/com/example/project3/Main.java`).
3.  Run the `main` method in `Main.java`.

## Project Structure

*   `src/main/java/com/example/project3/`: Contains the Java source code.
    *   `Main.java`: The main application class that launches JavaFX.
    *   `Controller.java`: The controller class handling UI logic and events for `view.fxml`.
    *   Other Java classes for business logic (e.g., Account, Customer, Database handling - *structure to be detailed by developer*).
*   `src/main/resources/com/example/project3/`: Contains FXML files and other resources.
    *   `view.fxml`: The FXML file defining the user interface layout.
*   `src/main/java/module-info.java`: The Java module descriptor file, specifying module dependencies.
*   `README.md`: This file.

## Future Enhancements (Suggestions)

*   Input validation for all fields.
*   Persistent storage of account data (e.g., using files, databases).
*   More detailed error handling and user feedback.
*   Unit tests for business logic.

---

*This README provides a general outline. Feel free to expand on specific sections, especially regarding the business logic classes and detailed functionality.*
