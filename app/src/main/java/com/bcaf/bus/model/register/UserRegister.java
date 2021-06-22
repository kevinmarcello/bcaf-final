package com.bcaf.bus.model.register;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserRegister {
    @SerializedName("email")

    private String email;
    @SerializedName("firstName")

    private String firstName;
    @SerializedName("lastName")

    private String lastName;
    @SerializedName("mobileNumber")

    private String mobileNumber;
    @SerializedName("password")

    private String password;
    @SerializedName("role")

    private ArrayList<String> role = null;

    public UserRegister() {
    }

    public UserRegister(String email, String firstName, String lastName, String mobileNumber, String password, ArrayList<String> role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getRole() {
        return role;
    }

    public void setRole(ArrayList<String> role) {
        this.role = role;
    }

}

