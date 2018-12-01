package com.homesordervendor.product.productlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by innoppl on 27/04/18.
 */

public class DeliveryCostArea implements Serializable {
    @SerializedName("areaNameEN")
    @Expose
    String areaNameEN;

    @SerializedName("areaNameAR")
    @Expose
    String areaNameAR;

    @SerializedName("areaID")
    @Expose
    int areaID;

    @SerializedName("areaCode")
    @Expose
    String areaCode;

    @SerializedName("stateID")
    @Expose
    int stateID;

    @SerializedName("selected")
    @Expose
    boolean selected;

    public String getAreaNameEN() {
        return areaNameEN;
    }

    public void setAreaNameEN(String areaNameEN) {
        this.areaNameEN = areaNameEN;
    }

    public String getAreaNameAR() {
        return areaNameAR;
    }

    public void setAreaNameAR(String areaNameAR) {
        this.areaNameAR = areaNameAR;
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}