package com.example.hitcapp.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String emailId;
    private String password;
    private boolean active;
    private String createdTime;

    public User() {}

    public User(int id, String firstName, String lastName, String userName, String emailId, String password, boolean active, String createdTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
        this.active = active;
        this.createdTime = createdTime;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getCreatedTime() { return createdTime; }
    public void setCreatedTime(String createdTime) { this.createdTime = createdTime; }
}
