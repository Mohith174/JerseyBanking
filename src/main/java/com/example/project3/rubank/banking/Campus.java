package com.example.project3.rubank.banking;

/**
 * Campus Enum Class
 * @author Mohith Kodavati and Nalita Pillay
 */
public enum Campus {
    // Define instances of AccountType (given in project description)
    NEW_BRUNSWICK(1),
    NEWARK(2),
    CAMDEN(3);

    private final int code;

    /**
     * Constructor for Campus enum
     * @param code the code of the campus
     */
    Campus(int code) {
        this.code = code;
    }

    /**
     * Getters for Campus code
     * @return the code of the campus
     */
    public int getCode() {
        return code;
    }

    /**
     * Getters for Campus name
     * @return the name of the campus
     */
    public String getName() {
        return this.toString();
    }
}
