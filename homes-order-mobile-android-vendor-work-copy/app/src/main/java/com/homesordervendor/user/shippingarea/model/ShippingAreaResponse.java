package com.homesordervendor.user.shippingarea.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 2/27/18.
 */

public class ShippingAreaResponse {

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("code")
    @Expose
    int code;

    @SerializedName("coveredarea")
    @Expose
    List<Country> coveredarea;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Country> getCoveredarea() {
        return coveredarea;
    }

    public void setCoveredarea(List<Country> coveredarea) {
        this.coveredarea = coveredarea;
    }
}
