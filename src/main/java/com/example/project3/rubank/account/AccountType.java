package com.example.project3.rubank.account;

/**
 *  AccountType enum class represents the RU Bank's five branch offices and identifies them by zip, branchCode, and county.
 *  @author Mohith Kodavati and Nalita Pillay
 */
public enum AccountType {
    // Define instances of AccountType (given in project description)
    CHECKING("01"),
    SAVINGS("02"),
    MONEY_MARKET("03"),
    COLLEGE_CHECKING("04"),
    CD("05");

    private final String code;

    /**
     * Constructor
     * @param code the code of the AccountType
     */
    AccountType(String code){
        this.code = code;
    }

    /**
     * Get the code of the AccountType
     * @return the code of the AccountType
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the name of the AccountType
     * @return the name of the AccountType
     */
    public String getName() {
        return this.name();
    }

    /**
     * Get enum object from const(s)
     * @param key value of const (code)
     * @return the corresponding AccountType object if found (and null if not found)
     */
    public static AccountType getAccountType(String key) {
        for (AccountType a : AccountType.values()) {
            if (a.getCode().equals(key)) {
                return a;
            }
        }
        return null;
    }
}
