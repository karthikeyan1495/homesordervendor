package com.homesordervendor.user.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/21/18.
 */

public class Address {

    @SerializedName("street")
    @Expose
    private String street="";

    @SerializedName("dob")
    @Expose
    private String dob="";


    @SerializedName("city")
    @Expose
    private String city="";

    @SerializedName("postcode")
    @Expose
    private String postcode="";

    @SerializedName("countryId")
    @Expose
    private String countryId="";

    @SerializedName("gender")
    @Expose
    private String gender="";


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
