package com.gzu.system.pojo;

public class Place {
    private String userName;
    private String placeName;
    private String placeAddress;
    private Integer lowRiskAfter;

    public Place() {
    }

    public Place(String userName, String placeName, String placeAddress, Integer lowRiskAfter) {
        this.userName = userName;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.lowRiskAfter = lowRiskAfter;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public Integer getLowRiskAfter() {
        return lowRiskAfter;
    }

    public void setLowRiskAfter(Integer lowRiskAfter) {
        this.lowRiskAfter = lowRiskAfter;
    }

    @Override
    public String toString() {
        return "Place{" +
                "userName='" + userName + '\'' +
                ", placeName='" + placeName + '\'' +
                ", placeAddress='" + placeAddress + '\'' +
                ", lowRiskAfter=" + lowRiskAfter +
                '}';
    }
}
