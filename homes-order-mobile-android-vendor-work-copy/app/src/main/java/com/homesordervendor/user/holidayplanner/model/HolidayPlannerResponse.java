package com.homesordervendor.user.holidayplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by innoppl on 10/03/18.
 */

public class HolidayPlannerResponse {

    @SerializedName("code")
    @Expose
    int code;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("offDayPlans")
    @Expose
    List<HolidayPlanner> offDayPlans;

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

    public List<HolidayPlanner> getOffDayPlans() {
        return offDayPlans;
    }

    public void setOffDayPlans(List<HolidayPlanner> offDayPlans) {
        this.offDayPlans = offDayPlans;
    }
}
