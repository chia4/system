package com.gzu.system.pojo;

public class Agency {
    private String userName;
    private String agencyName;
    private String agencyAddress;


    public Agency() {
    }

    public Agency(String userName, String agencyName, String agencyAddress) {
        this.userName = userName;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyAddress() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress = agencyAddress;
    }

    @Override
    public String toString() {
        return "Agency{" +
                "userName='" + userName + '\'' +
                ", agencyName='" + agencyName + '\'' +
                ", agencyAddress='" + agencyAddress + '\'' +
                '}';
    }
}
