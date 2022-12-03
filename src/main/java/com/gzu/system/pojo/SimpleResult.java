package com.gzu.system.pojo;

public class SimpleResult {
    public enum type {
        POSITIVE,
        NEGATIVE
    }

    private String agencyName;
    private type result;
    private int resultTime;

    public SimpleResult() {
    }

    public SimpleResult(String agencyName, type result, int resultTime) {
        this.agencyName = agencyName;
        this.result = result;
        this.resultTime = resultTime;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
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
        return "SimpleResult{" +
                "agencyName='" + agencyName + '\'' +
                ", result=" + result +
                ", resultTime=" + resultTime +
                '}';
    }
}
