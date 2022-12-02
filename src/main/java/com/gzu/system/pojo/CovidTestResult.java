package com.gzu.system.pojo;

public class CovidTestResult {
    public enum type {
        POSITIVE,
        NEGATIVE
    }

    private String peopleUsername;
    private String agencyUsername;
    private type type;

    public CovidTestResult(String peopleUsername, String agencyUsername, CovidTestResult.type type) {
        this.peopleUsername = peopleUsername;
        this.agencyUsername = agencyUsername;
        this.type = type;
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

    public CovidTestResult.type getType() {
        return type;
    }

    public void setType(CovidTestResult.type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CovidTestResult{" +
                "peopleUsername='" + peopleUsername + '\'' +
                ", agencyUsername='" + agencyUsername + '\'' +
                ", type=" + type +
                '}';
    }
}

