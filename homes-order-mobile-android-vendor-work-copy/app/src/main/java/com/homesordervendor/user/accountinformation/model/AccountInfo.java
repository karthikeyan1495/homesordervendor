package com.homesordervendor.user.accountinformation.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesordervendor.BR;

/**
 * Created by mac on 3/3/18.
 */

public class AccountInfo extends BaseObservable {

    @SerializedName("countryNameEN")
    @Expose
    private String countryNameEN="";

    @SerializedName("countryNameAR")
    @Expose
    private String countryNameAR="";

    @SerializedName("countryId")
    @Expose
    private String countryId="";

    int countryPosition=0;


    @SerializedName("street")
    @Expose
    private String street="";

    @SerializedName("gender")
    @Expose
    private String gender="";

    @SerializedName("city")
    @Expose
    private String city="";

    @SerializedName("dob")
    @Expose
    private String dob="";

    boolean male;
    boolean female;
    boolean notspecify;

    @Bindable
    public String getCountryNameEN() {
        return countryNameEN;
    }

    @Bindable
    public void setCountryNameEN(String countryNameEN) {
        this.countryNameEN = countryNameEN;
        notifyPropertyChanged(BR.countryNameEN);

    }

    @Bindable
    public String getCountryNameAR() {
        return countryNameAR;
    }

    @Bindable
    public void setCountryNameAR(String countryNameAR) {
        this.countryNameAR = countryNameAR;
        notifyPropertyChanged(BR.countryNameAR);

    }

    @Bindable
    public String getStreet() {
        return street;
    }

    @Bindable
    public void setStreet(String street) {
        this.street = street;
        notifyPropertyChanged(BR.street);

    }

    @Bindable
    public String getGender() {
        return gender;
    }

    @Bindable
    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);

    }

    @Bindable
    public String getCity() {
        return city;
    }

    @Bindable
    public void setCity(String city) {
        this.city = city;
        notifyPropertyChanged(BR.city);

    }

    @Bindable
    public String getDob() {
        return dob;
    }

    @Bindable
    public void setDob(String dob) {
        this.dob = dob;
        notifyPropertyChanged(BR.dob);

    }

    @Bindable
    public String getCountryId() {
        return countryId;
    }

    @Bindable
    public void setCountryId(String countryId) {
        this.countryId = countryId;
        notifyPropertyChanged(BR.countryId);
    }

    @Bindable
    public int getCountryPosition() {
        return countryPosition;
    }

    @Bindable
    public void setCountryPosition(int countryPosition) {
        this.countryPosition = countryPosition;
        notifyPropertyChanged(BR.countryPosition);
    }

    @Bindable
    public boolean isMale() {
        return male;
    }

    @Bindable
    public void setMale(boolean male) {
        this.male = male;
        if (male) {
            this.gender = "1";
        }
        notifyPropertyChanged(BR.male);

    }

    @Bindable
    public boolean isFemale() {
        return female;

    }

    @Bindable
    public void setFemale(boolean female) {
        this.female = female;
        if(female) {
            this.gender = "2";
        }
        notifyPropertyChanged(BR.female);

    }

    @Bindable
    public boolean isNotspecify() {
        return notspecify;
    }

    @Bindable
    public void setNotspecify(boolean notspecify) {
        this.notspecify = notspecify;
        if (notspecify) {
            this.gender = "3";
        }
        notifyPropertyChanged(BR.notspecify);

    }
}
