package com.gzu.system.pojo;

public class SimpleAuthorization {
    private String agencyName;
    private int authorizationTime;

    public SimpleAuthorization() {
    }

    public SimpleAuthorization(int authorizationTime, String agencyName) {
        this.authorizationTime = authorizationTime;
        this.agencyName = agencyName;
    }

    public int getAuthorizationTime() {
        return authorizationTime;
    }

    public void setAuthorizationTime(int authorizationTime) {
        this.authorizationTime = authorizationTime;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    @Override
    public String toString() {
        return "SimpleAuthorization{" +
                "authorizationTime=" + authorizationTime +
                ", agencyName='" + agencyName + '\'' +
                '}';
    }
}
