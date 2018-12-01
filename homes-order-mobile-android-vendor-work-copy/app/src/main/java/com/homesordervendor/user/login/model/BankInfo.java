package com.homesordervendor.user.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/21/18.
 */

public class BankInfo {

    @SerializedName("mode_of_payment")
    @Expose
    private String mode_of_payment="";

    @SerializedName("account_holder_name")
    @Expose
    private String account_holder_name="";

    @SerializedName("iban")
    @Expose
    private String iban="";

    @SerializedName("bank_name")
    @Expose
    private String bank_name="";

    @SerializedName("account_number")
    @Expose
    private String account_number="";


    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }


}
