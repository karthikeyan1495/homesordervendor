package com.homesordervendor.user.accountinformation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 3/3/18.
 */

public class CitiesResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("coveredarea")
    @Expose
    private List<CoveredArea> coveredarea;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CoveredArea> getCoveredarea() {
        return coveredarea;
    }

    public void setCoveredarea(List<CoveredArea> coveredarea) {
        this.coveredarea = coveredarea;
    }
}
