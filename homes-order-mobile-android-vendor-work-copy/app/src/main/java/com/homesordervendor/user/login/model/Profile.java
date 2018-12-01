package com.homesordervendor.user.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/21/18.
 */

public class Profile {

    @SerializedName("fullname")
    @Expose
    private String fullname="";

    @SerializedName("email")
    @Expose
    private String email="";

    @SerializedName("image")
    @Expose
    private String image="";

    @SerializedName("shopname")
    @Expose
    private String shopname="";

    @SerializedName("phone_no")
    @Expose
    private String phone_no="";

    @SerializedName("street")
    @Expose
    private String street="";

    @SerializedName("business_licence_number")
    @Expose
    private String business_licence_number="";

    @SerializedName("bankinfo")
    @Expose
    private BankInfo bankinfo;

    @SerializedName("address")
    @Expose
    private Address address;


    @SerializedName("isaddress")
    @Expose
    private String isaddress;

    @SerializedName("isdeliveryslot")
    @Expose
    private String isdeliveryslot;

    @SerializedName("issubscriped")
    @Expose
    private String issubscriped;

    @SerializedName("isdeliverycost")
    @Expose
    private String isdeliverycost;

    @SerializedName("country")
    @Expose
    private String country="";


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public BankInfo getBankinfo() {
        return bankinfo;
    }

    public void setBankinfo(BankInfo bankinfo) {
        this.bankinfo = bankinfo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getIsaddress() {
        return isaddress;
    }

    public void setIsaddress(String isaddress) {
        this.isaddress = isaddress;
    }

    public String getIsdeliveryslot() {
        return isdeliveryslot;
    }

    public void setIsdeliveryslot(String isdeliveryslot) {
        this.isdeliveryslot = isdeliveryslot;
    }

    public String getIssubscriped() {
        return issubscriped;
    }

    public void setIssubscriped(String issubscriped) {
        this.issubscriped = issubscriped;
    }

    public String getIsdeliverycost() {
        return isdeliverycost;
    }

    public void setIsdeliverycost(String isdeliverycost) {
        this.isdeliverycost = isdeliverycost;
    }

    public String getBusiness_licence_number() {
        return business_licence_number;
    }

    public void setBusiness_licence_number(String business_licence_number) {
        this.business_licence_number = business_licence_number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
