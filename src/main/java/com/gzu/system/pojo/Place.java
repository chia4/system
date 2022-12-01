package com.gzu.system.pojo;

public class Place {
    private String userName;
    private String placeName;
    private String placeAddress;

    public Place(String userName, String placeName, String placeAddress) {

        this.userName = userName;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
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

    @Override
    public String toString() {
        return "Place{" +
                "userName='" + userName + '\'' +
                ", placeName='" + placeName + '\'' +
                ", placeAddress='" + placeAddress + '\'' +
                '}';
    }
}
