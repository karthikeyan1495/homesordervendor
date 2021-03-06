package com.homesordervendor.user.shippingarea.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesordervendor.BR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2/27/18.
 */

public class State extends BaseObservable implements Serializable {

    @SerializedName("stateNameEN")
    @Expose
    String stateNameEN;

    @SerializedName("stateNameAR")
    @Expose
    String stateNameAR;

    @SerializedName("stateID")
    @Expose
    int stateID;

    @SerializedName("stateCode")
    @Expose
    String stateCode;

    @SerializedName("countryID")
    @Expose
    int countryID;

    @SerializedName("price")
    @Expose
    String price="";

    @SerializedName("selected")
    @Expose
    boolean selected;

    @SerializedName("areas")
    @Expose
    List<Area> areas=new ArrayList<>();

    boolean expended=false;

    public String getStateNameEN() {
        return stateNameEN;
    }

    public void setStateNameEN(String stateNameEN) {
        this.stateNameEN = stateNameEN;
    }

    public String getStateNameAR() {
        return stateNameAR;
    }

    public void setStateNameAR(String stateNameAR) {
        this.stateNameAR = stateNameAR;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    @Bindable
    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    @Bindable
    public boolean isExpended() {
        return expended;
    }

    @Bindable
    public void setExpended(boolean expended) {
        this.expended = expended;
        notifyPropertyChanged(BR.expended);
    }
}
