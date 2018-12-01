package com.homesordervendor.user.accountinformation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesordervendor.user.shippingarea.model.Area;

import java.util.List;

/**
 * Created by mac on 3/3/18.
 */

public class CoveredArea {

    @SerializedName("countryNameEN")
    @Expose
    private String countryNameEN;

    @SerializedName("countryNameAR")
    @Expose
    private String countryNameAR;

    @SerializedName("countryID")
    @Expose
    private int countryID;

    @SerializedName("countryCode")
    @Expose
    private String countryCode;

    @SerializedName("areas")
    @Expose
    private List<Area> areas;

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

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
