package com.homesordervendor.user.shippingarea.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesordervendor.BR;

import java.io.Serializable;

/**
 * Created by mac on 2/27/18.
 */

public class Area extends BaseObservable implements Serializable {


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

    @SerializedName("countryID")
    @Expose
    int countryID;

    @SerializedName("selected")
    @Expose
    boolean selected;

    @SerializedName("price")
    @Expose
    String price="";

    @SerializedName("priceInSAR")
    @Expose
    String priceInSAR="";


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

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    public String getPriceInSAR() {
        return priceInSAR;
    }

    public void setPriceInSAR(String priceInSAR) {
        this.priceInSAR = priceInSAR;
    }
}
