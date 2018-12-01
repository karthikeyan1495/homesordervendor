package com.homesordervendor.user.register.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesordervendor.BR;

/**
 * Created by innoppl on 08/05/18.
 */

public class SelectedCountry extends BaseObservable {

    @SerializedName("countryNameEN")
    @Expose
    private String countryNameEN="";

    @SerializedName("countryNameAR")
    @Expose
    private String countryNameAR="";

    @SerializedName("countryId")
    @Expose
    private String countryId="";

    @SerializedName("countryCode")
    @Expose
    private String countryCode="";

    @Bindable
    public String getCountryNameEN() {
        return countryNameEN;
    }

    public void setCountryNameEN(String countryNameEN) {
        this.countryNameEN = countryNameEN;
        notifyPropertyChanged(BR.countryNameEN);

    }

    @Bindable
    public String getCountryNameAR() {
        return countryNameAR;
    }

    public void setCountryNameAR(String countryNameAR) {
        this.countryNameAR = countryNameAR;
        notifyPropertyChanged(BR.countryNameAR);

    }

    @Bindable
    public String getCountryId() {
        return countryId;
    }


    public void setCountryId(String countryId) {
        this.countryId = countryId;
        notifyPropertyChanged(BR.countryId);

    }

    @Bindable
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        notifyPropertyChanged(BR.countryCode);

    }
}
