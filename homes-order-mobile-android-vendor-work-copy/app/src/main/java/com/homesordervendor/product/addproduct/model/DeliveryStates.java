package com.homesordervendor.product.addproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 3/4/18.
 */

public class DeliveryStates {


    @SerializedName("stateID")
    @Expose
    private String stateID;

    @SerializedName("areas")
    @Expose
    private List<DeliveryArea> areas;

   /* @SerializedName("price")
    @Expose
    private String price;*/

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public List<DeliveryArea> getAreas() {
        return areas;
    }

    public void setAreas(List<DeliveryArea> areas) {
        this.areas = areas;
    }

    /*public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }*/
}
