package com.homesordervendor.user.holidayplanner.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesordervendor.BR;

import java.io.Serializable;

/**
 * Created by mac on 3/8/18.
 */

public class HolidayPlanner extends BaseObservable implements Serializable {

    @SerializedName("planId")
    @Expose
    String planId;

    @SerializedName("fromDate")
    @Expose
    String fromDate;

    @SerializedName("toDate")
    @Expose
    String toDate;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("status")
    @Expose
    String status;





    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Bindable
    public String getFromDate() {
        return fromDate;
    }

    @Bindable
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
        notifyPropertyChanged(BR.fromDate);

    }

    @Bindable
    public String getToDate() {
        return toDate;
    }

    @Bindable
    public void setToDate(String toDate) {
        this.toDate = toDate;
        notifyPropertyChanged(BR.toDate);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
