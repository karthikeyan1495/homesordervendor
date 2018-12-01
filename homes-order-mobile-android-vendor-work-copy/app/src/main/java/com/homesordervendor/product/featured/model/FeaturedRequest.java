package com.homesordervendor.product.featured.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesordervendor.BR;

/**
 * Created by mac on 3/6/18.
 */

public class FeaturedRequest extends BaseObservable {

    @SerializedName("productID")
    @Expose
    private String productID;

    @SerializedName("subcategoryID")
    @Expose
    private String subcategoryID;


    @SerializedName("startDate")
    @Expose
    private String startDate;


    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("refId")
    @Expose
    private String refId="";


    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getSubcategoryID() {
        return subcategoryID;
    }

    public void setSubcategoryID(String subcategoryID) {
        this.subcategoryID = subcategoryID;
    }

    @Bindable
    public String getStartDate() {
        return startDate;
    }

    @Bindable
    public void setStartDate(String startDate) {
        this.startDate = startDate;
        notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public String getEndDate() {
        return endDate;
    }

    @Bindable
    public void setEndDate(String endDate) {
        this.endDate = endDate;
        notifyPropertyChanged(BR.endDate);
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
