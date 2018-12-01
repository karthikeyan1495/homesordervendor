package com.homesordervendor.product.productlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mac on 3/4/18.
 */

public class ReviewItem implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("detail")
    @Expose
    private String detail;

    @SerializedName("ratingPercentage")
    @Expose
    private String ratingPercentage;

    @SerializedName("ratingValue")
    @Expose
    private String ratingValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRatingPercentage() {
        return ratingPercentage;
    }

    public void setRatingPercentage(String ratingPercentage) {
        this.ratingPercentage = ratingPercentage;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }
}
