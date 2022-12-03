package com.gzu.system.pojo;

public class Agency {
    private String username;
    private String agencyName;
    private String agencyAddress;

    public Agency() {
    }

    public Agency(String username, String agencyName, String agencyAddress) {
        this.username = username;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
                "username='" + username + '\'' +
                ", agencyName='" + agencyName + '\'' +
                ", agencyAddress='" + agencyAddress + '\'' +
                '}';
    }
}
