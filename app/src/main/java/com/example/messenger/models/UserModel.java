package com.example.messenger.models;

import com.google.firebase.Timestamp;

public class UserModel {
    private String userId;
    private String phoneNumber;
    private String username;
    private Timestamp timestamp;

    // Constructor không tham số
    public UserModel() {}

    // Constructor có tham số
    public UserModel(String userId,String phoneNumber, String username, Timestamp timestamp) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter và Setter
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
