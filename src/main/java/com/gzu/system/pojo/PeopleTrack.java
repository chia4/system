package com.gzu.system.pojo;

public class PeopleTrack {
    private String peopleUsername;
    private String placeUsername;
    private Integer passingTime;

    public PeopleTrack() {
    }

    public PeopleTrack(String peopleUsername, String placeUsername, Integer passingTime) {
        this.peopleUsername = peopleUsername;
        this.placeUsername = placeUsername;
        this.passingTime = passingTime;
    }

    public String getPeopleUsername() {
        return peopleUsername;
    }

    public void setPeopleUsername(String peopleUsername) {
        this.peopleUsername = peopleUsername;
    }

    public String getPlaceUsername() {
        return placeUsername;
    }

    public void setPlaceUsername(String placeUsername) {
        this.placeUsername = placeUsername;
    }

    public Integer getPassingTime() {
        return passingTime;
    }

    public void setPassingTime(Integer passingTime) {
        this.passingTime = passingTime;
    }

    @Override
    public String toString() {
        return "PeopleTrack{" +
                "peopleUsername='" + peopleUsername + '\'' +
                ", placeUsername='" + placeUsername + '\'' +
                ", passingTime=" + passingTime +
                '}';
    }
}
