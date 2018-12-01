package com.homesordervendor.product.productlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mac on 3/5/18.
 */

public class DeliveryCostItem implements Serializable {

    @SerializedName("countryNameEN")
    @Expose
    String countryNameEN;

    @SerializedName("countryNameAR")
    @Expose
    String countryNameAR;

    @SerializedName("countryID")
    @Expose
    int countryID;

    @SerializedName("countryCode")
    @Expose
    String countryCode;

    @SerializedName("states")
    @Expose
    DeliveryCostState states;

    public String getCountryNameEN() {
        return countryNameEN;
    }

    public void setCountryNameEN(String countryNameEN) {
        this.countryNameEN = countryNameEN;
    }

    public String getCountryNameAR() {
        return countryNameAR;
    }

    public void setCountryNameAR(String countryNameAR) {
        this.countryNameAR = countryNameAR;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public DeliveryCostState getDeliveryState() {
        return states;
    }

    public void setDeliveryState(DeliveryCostState states) {
        this.states = states;
    }
}
