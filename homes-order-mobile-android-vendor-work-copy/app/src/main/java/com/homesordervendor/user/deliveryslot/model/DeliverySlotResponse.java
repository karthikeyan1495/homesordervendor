package com.homesordervendor.user.deliveryslot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/20/18.
 */

public class DeliverySlotResponse {
    @SerializedName("code")
    @Expose
    int code
            ;
    @SerializedName("message")
    @Expose
    String message="";

    @SerializedName("deliverySlots")
    @Expose
    DeliverySlot deliverySlots;

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

    public DeliverySlot getDeliverySlots() {
        return deliverySlots;
    }

    public void setDeliverySlots(DeliverySlot deliverySlots) {
        this.deliverySlots = deliverySlots;
    }
}
