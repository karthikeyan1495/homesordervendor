package com.homesordervendor.product.addproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 3/4/18.
 */

public class DeliveryCost {

    @SerializedName("countryID")
    @Expose
    private String countryID;

    @SerializedName("states")
    @Expose
    private List<DeliveryStates> states;

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public List<DeliveryStates> getStates() {
        return states;
    }

    public void setStates(List<DeliveryStates> states) {
        this.states = states;
    }
}
