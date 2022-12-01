package com.gzu.system.pojo;

public class Agency {
    private String userName;
    private String agencyName;
    private String agencyAddress;
    private boolean risk;// 1为风险地区，为非风险

    public Agency(String userName, String agencyName, String agencyAddress, boolean risk) {
        this.userName = userName;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
        this.risk = risk;
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

    public boolean isRisk() {
        return risk;
    }

    public void setRisk(boolean risk) {
        this.risk = risk;
    }

    @Override
    public String toString() {
        return "Agency{" +
                "userName='" + userName + '\'' +
                ", agencyName='" + agencyName + '\'' +
                ", agencyAddress='" + agencyAddress + '\'' +
                ", risk=" + risk +
                '}';
    }
}
