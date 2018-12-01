package com.homesordervendor.user.shippingarea.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2/27/18.
 */

public class Country implements Serializable {

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

    @SerializedName("selected")
    @Expose
    boolean selected;

    @SerializedName("states")
    @Expose
    List<State> states;


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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }
}
