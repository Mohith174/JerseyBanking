package com.example.project3.rubank.banking;

/**
 *  Branch enum class represents the RU Bank's five branch offices and identifies them by zip, branchCode, and county.
 *  @author Mohith Kodavati and Nalita Pillay
 */
public enum Branch {
    // Define instances of Branch (given in project description)
    EDISON("08817", "100", "Middlesex"),
    BRIDGEWATER("08807", "200", "Somerset"),
    PRINCETON("08542", "300", "Mercer"),
    PISCATAWAY("08854", "400", "Middlesex"),
    WARREN("07057", "500", "Somerset");

    private final String zip;
    private final String branchCode;
    private final String county;

    /**
     * Constructor for Branch enum
     * @param zip the zip code of the branch
     * @param branchCode the branch code of the branch
     * @param county the county of the branch
     */
    Branch(String zip, String branchCode, String county){
        this.zip = zip;
        this.branchCode = branchCode;
        this.county = county;
    }

    /**
     * Getters for Branch zip
     * @return the zip code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Getters for Branch code
     * @return the branch code
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * Getters for Branch county
     * @return the county
     */
    public String getCounty() {
        return county;
    }

    /**
     * Getters for Branch name
     * @return the name of the branch
     */
    public String getName() {
        return this.toString();
    }

    /**
    *  Get enum object from const(s)
    *  @param key value of const (zip, branchCode, or county)
    *  @return the corresponding Branch object if found (and null if not found
     */
    public static Branch getBranch(String key){
        for (Branch b : Branch.values()){
            if (b.getZip().equals(key) || b.getBranchCode().equals(key) || b.getCounty().equals(key)){
                return b;
            }
        }
        return null;
    }
}
