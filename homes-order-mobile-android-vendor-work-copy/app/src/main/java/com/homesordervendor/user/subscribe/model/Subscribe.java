package com.homesordervendor.user.subscribe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/22/18.
 */

public class Subscribe {

    @SerializedName("pack")
    @Expose
    String pack;

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }
}
