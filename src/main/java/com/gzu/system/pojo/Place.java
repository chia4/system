package com.gzu.system.pojo;

public class Place {
    private String username;
    private String placeName;
    private String placeAddress;
    private Integer lowRiskAfter;

    public Place() {
    }

    public Place(String username, String placeName, String placeAddress, Integer lowRiskAfter) {
        this.username = username;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.lowRiskAfter = lowRiskAfter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
                "username='" + username + '\'' +
                ", placeName='" + placeName + '\'' +
                ", placeAddress='" + placeAddress + '\'' +
                ", lowRiskAfter=" + lowRiskAfter +
                '}';
    }
}
