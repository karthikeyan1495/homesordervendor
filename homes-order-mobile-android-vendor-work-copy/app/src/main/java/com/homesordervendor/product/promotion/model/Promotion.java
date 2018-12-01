package com.homesordervendor.product.promotion.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesordervendor.BR;

/**
 * Created by mac on 3/5/18.
 */

public class Promotion extends BaseObservable {

    @SerializedName("productID")
    @Expose
    private String productID;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("priceInSAR")
    @Expose
    private String priceInSAR;


    @SerializedName("discountPrice")
    @Expose
    private String discountPrice="";

    @SerializedName("discountPriceInSAR")
    @Expose
    private String discountPriceInSAR="";

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("fromDate")
    @Expose
    private String fromDate="";

    @SerializedName("toDate")
    @Expose
    private String toDate="";

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Bindable
    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
        notifyPropertyChanged(BR.discountPrice);
    }

    @Bindable
    public String getDiscount() {
        return discount;
    }

    @Bindable
    public void setDiscount(String discount) {
        this.discount = discount;
        notifyPropertyChanged(BR.discount);
    }

    @Bindable
    public String getFromDate() {
        return fromDate;
    }

    @Bindable
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
        notifyPropertyChanged(BR.fromDate);
    }

    @Bindable
    public String getToDate() {
        return toDate;
    }

    @Bindable
    public void setToDate(String toDate) {
        this.toDate = toDate;
        notifyPropertyChanged(BR.toDate);

    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDiscountPriceInSAR() {
        return discountPriceInSAR;
    }

    public void setDiscountPriceInSAR(String discountPriceInSAR) {
        this.discountPriceInSAR = discountPriceInSAR;
    }

    public String getPriceInSAR() {
        return priceInSAR;
    }

    public void setPriceInSAR(String priceInSAR) {
        this.priceInSAR = priceInSAR;
    }
}
