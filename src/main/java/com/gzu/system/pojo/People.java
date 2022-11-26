package com.gzu.system.pojo;

public class People {
    private String username;
    private String fullName;
    private String phoneNumber;
    private String idCardNumber;
    private gender userGender;
    private Integer greenCodeAfter;

    public enum gender{
        F, M
    }

    public People() {
    }

    public People(String username, String fullName, String phoneNumber,
                  String idCardNumber, gender userGender, Integer greenCodeAfter) {
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.idCardNumber = idCardNumber;
        this.userGender = userGender;
        this.greenCodeAfter = greenCodeAfter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public gender getUserGender() {
        return userGender;
    }

    public void setUserGender(gender userGender) {
        this.userGender = userGender;
    }

    public Integer getGreenCodeAfter() {
        return greenCodeAfter;
    }

    public void setGreenCodeAfter(Integer greenCodeAfter) {
        this.greenCodeAfter = greenCodeAfter;
    }

    @Override
    public String toString() {
        return "People{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", idCardNumber='" + idCardNumber + '\'' +
                ", userGender=" + userGender +
                ", greenCodeAfter=" + greenCodeAfter +
                '}';
    }
}
