package com.gzu.system.pojo;


public class UserLogin {
    public enum type {
        PEOPLE,
        PLACE,
        AGENCY
    }

    private String username;
    private String passwordHash;
    private type type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserLogin.type getType() {
        return type;
    }

    public void setType(UserLogin.type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", type=" + type +
                '}';
    }
}
