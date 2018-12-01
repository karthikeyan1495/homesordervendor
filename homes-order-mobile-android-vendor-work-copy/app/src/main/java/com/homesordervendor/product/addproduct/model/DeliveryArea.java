package com.homesordervendor.product.addproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 27/04/18.
 */

public class DeliveryArea {
    @SerializedName("areaID")
    @Expose
    private String areaID;

    @SerializedName("price")
    @Expose
    private String price;

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
