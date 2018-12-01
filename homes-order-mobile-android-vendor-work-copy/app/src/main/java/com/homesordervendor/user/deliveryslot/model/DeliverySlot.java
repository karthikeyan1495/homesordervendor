package com.homesordervendor.user.deliveryslot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/20/18.
 */

public class DeliverySlot {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("vendorId")
    @Expose
    int vendorId;

    @SerializedName("sunday")
    @Expose
    String sunday;

    @SerializedName("monday")
    @Expose
    String monday;

    @SerializedName("tuesday")
    @Expose
    String tuesday;

    @SerializedName("wednesday")
    @Expose
    String wednesday;

    @SerializedName("thursday")
    @Expose
    String thursday;

    @SerializedName("friday")
    @Expose
    String friday;

    @SerializedName("saturday")
    @Expose
    String saturday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }
}
