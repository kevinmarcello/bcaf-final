package com.bcaf.bus.model.user;

import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("password")
    private String password;

    public ChangePassword() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ChangePassword(String password) {
        this.password = password;
    }
}
