package com.gzu.system.pojo;

public class CovidTestResult {
    public enum type {
        POSITIVE,
        NEGATIVE
    }

    private String peopleUsername;
    private String agencyUsername;
    private type result;
    private int resultTime;

    public CovidTestResult() {
    }

    public CovidTestResult(String peopleUsername, String agencyUsername, type result, int resultTime) {
        this.peopleUsername = peopleUsername;
        this.agencyUsername = agencyUsername;
        this.result = result;
        this.resultTime = resultTime;
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

    public type getResult() {
        return result;
    }

    public void setResult(type result) {
        this.result = result;
    }

    public int getResultTime() {
        return resultTime;
    }

    public void setResultTime(int resultTime) {
        this.resultTime = resultTime;
    }

    @Override
    public String toString() {
        return "CovidTestResult{" +
                "peopleUsername='" + peopleUsername + '\'' +
                ", agencyUsername='" + agencyUsername + '\'' +
                ", result=" + result +
                ", resultTime=" + resultTime +
                '}';
    }
}

