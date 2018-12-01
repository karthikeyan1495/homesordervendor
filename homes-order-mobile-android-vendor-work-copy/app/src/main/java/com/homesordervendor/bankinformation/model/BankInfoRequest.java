package com.homesordervendor.bankinformation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 3/8/18.
 */

public class BankInfoRequest {

    @SerializedName("mode_of_payments")
    @Expose
    private String mode_of_payments="";

    @SerializedName("account_holder_names")
    @Expose
    private String account_holder_names="";

    @SerializedName("ibans")
    @Expose
    private String ibans="";

    @SerializedName("bank_names")
    @Expose
    private String bank_names="";

    @SerializedName("account_numbers")
    @Expose
    private String account_numbers="";

    @SerializedName("business_licence_numbers")
    @Expose
    private String business_licence_numbers="";

    public String getMode_of_payments() {
        return mode_of_payments;
    }

    public void setMode_of_payments(String mode_of_payments) {
        this.mode_of_payments = mode_of_payments;
    }

    public String getAccount_holder_names() {
        return account_holder_names;
    }

    public void setAccount_holder_names(String account_holder_names) {
        this.account_holder_names = account_holder_names;
    }

    public String getIbans() {
        return ibans;
    }

    public void setIbans(String ibans) {
        this.ibans = ibans;
    }

    public String getBank_names() {
        return bank_names;
    }

    public void setBank_names(String bank_names) {
        this.bank_names = bank_names;
    }

    public String getAccount_numbers() {
        return account_numbers;
    }

    public void setAccount_numbers(String account_numbers) {
        this.account_numbers = account_numbers;
    }

    public String getBusiness_licence_numbers() {
        return business_licence_numbers;
    }

    public void setBusiness_licence_numbers(String business_licence_numbers) {
        this.business_licence_numbers = business_licence_numbers;
    }
}
