package com.bcaf.bus.model.bus;

import com.google.gson.annotations.SerializedName;

public class Bus {

    @SerializedName("id")
    private Integer id;
    @SerializedName("code")
    private String code;
    @SerializedName("capacity")
    private Integer capacity;
    @SerializedName("make")
    private String make;
    @SerializedName("agency")
    private Agency agency;

    public Bus(Integer id, String code, Integer capacity, String make, Agency agency) {
        this.id = id;
        this.code = code;
        this.capacity = capacity;
        this.make = make;
        this.agency = agency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
}
