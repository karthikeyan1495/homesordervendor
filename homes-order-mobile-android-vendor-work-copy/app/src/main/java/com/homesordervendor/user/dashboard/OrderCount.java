package com.homesordervendor.user.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 15/03/18.
 */

public class OrderCount {

    @SerializedName("code")
    @Expose
    int code
            ;
    @SerializedName("message")
    @Expose
    String message="";

    @SerializedName("new")
    @Expose
    String newCount="";

    @SerializedName("processing")
    @Expose
    String processing="";

    @SerializedName("dispatch")
    @Expose
    String dispatch="";

    @SerializedName("completed")
    @Expose
    String completed="";

    @SerializedName("cancel")
    @Expose
    String cancel="";

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

    public String getNewCount() {
        return newCount;
    }

    public void setNewCount(String newCount) {
        this.newCount = newCount;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public String getDispatch() {
        return dispatch;
    }

    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }
}
