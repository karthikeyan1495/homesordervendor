package com.homesordervendor.product.promotion.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 3/5/18.
 */

public class ProductID {

    @SerializedName("productID")
    @Expose
    private String productID;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
