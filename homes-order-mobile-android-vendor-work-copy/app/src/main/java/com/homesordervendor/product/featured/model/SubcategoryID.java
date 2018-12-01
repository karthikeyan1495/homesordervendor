package com.homesordervendor.product.featured.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 3/6/18.
 */

public class SubcategoryID {

    @SerializedName("subcategoryID")
    @Expose
    private String subcategoryID;

    public String getSubcategoryID() {
        return subcategoryID;
    }

    public void setSubcategoryID(String subcategoryID) {
        this.subcategoryID = subcategoryID;
    }







}
