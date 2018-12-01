package com.homesordervendor.product.featured.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 3/6/18.
 */

public class BlockedDates {

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message="";

    @SerializedName("featuredPrice")
    @Expose
    private String featuredPrice="";

    @SerializedName("featuredPriceInSAR")
    @Expose
    private String featuredPriceInSAR="";

    @SerializedName("blockedDates")
    @Expose
    private List<String> blockedDates=new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getBlockedDates() {
        return blockedDates;
    }

    public void setBlockedDates(List<String> blockedDates) {
        this.blockedDates = blockedDates;
    }

    public String getFeaturedPrice() {
        return featuredPrice;
    }

    public void setFeaturedPrice(String featuredPrice) {
        this.featuredPrice = featuredPrice;
    }

    public String getFeaturedPriceInSAR() {
        return featuredPriceInSAR;
    }

    public void setFeaturedPriceInSAR(String featuredPriceInSAR) {
        this.featuredPriceInSAR = featuredPriceInSAR;
    }
}
