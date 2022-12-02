package com.gzu.system.pojo;

public class CovidTestAuthorization {

    private String peopleUsername;
    private String agencyUsername;
    private int authorizationTime;

    public CovidTestAuthorization(String peopleUsername, String agencyUsername, int authorizationTime) {

        this.peopleUsername = peopleUsername;
        this.agencyUsername = agencyUsername;
        this.authorizationTime = authorizationTime;
    }

    public String getPeopleUsername() {
        return peopleUsername;
    }

    public void setPeopleUsername(String peopleUsername) {
        this.peopleUsername = peopleUsername;
    }

    public String getAgencyUsername() {
        return agencyUsername;
    }

    public void setAgencyUsername(String agencyUsername) {
        this.agencyUsername = agencyUsername;
    }

    public int getAuthorizationTime() {
        return authorizationTime;
    }

    public void setAuthorizationTime(int authorizationTime) {
        this.authorizationTime = authorizationTime;
    }

    @Override
    public String toString() {
        return "CovidTestAuthorization{" +
                "peopleUsername='" + peopleUsername + '\'' +
                ", agencyUsername='" + agencyUsername + '\'' +
                ", authorizationTime=" + authorizationTime +
                '}';
    }
}
