package com.homesordervendor.user.holidayplanner.viewmodel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.holidayplanner.model.HolidayPlanner;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.StringUtil;
import com.homesordervendor.util.Util;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/8/18.
 */

public class HolidayPlannerDetailVM {

    Activity activity;
    int years, dates, monthes;
    MyProgressDialog myProgressDialog;

    public boolean isUpdated = false;

    public HolidayPlannerDetailVM(Activity activity) {
        this.activity = activity;
        myProgressDialog = new MyProgressDialog();
    }

    public void onClickRootView(View view) {
        Util.getInstance().hideKeyboard(activity);
    }

    public void onClickClose(View view) {
        Util.getInstance().hideKeyboard(activity);
        Intent intent = new Intent();
        if (isUpdated) {
            activity.setResult(Activity.RESULT_OK, intent);
        } else {
            activity.setResult(Activity.RESULT_CANCELED, intent);
        }
        activity.finish();
    }

    public void onClickFromDate(View view, HolidayPlanner holidayPlanner) {
        Util.getInstance().hideKeyboard(activity);
        datePicker(holidayPlanner, true);
    }

    public void onClickToDate(View view, HolidayPlanner holidayPlanner) {
        Util.getInstance().hideKeyboard(activity);
        datePicker(holidayPlanner, false);
    }

    public void onClickSaveHoliday(View view, HolidayPlanner holidayPlanner) {
        Util.getInstance().hideKeyboard(activity);
        validation(holidayPlanner);
    }

    private void validation(HolidayPlanner holidayPlanner) {
        if (holidayPlanner.getFromDate() == null || holidayPlanner.getFromDate().trim().length() == 0) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_from_date));
        } else if (holidayPlanner.getToDate() == null || holidayPlanner.getToDate().trim().length() == 0) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_to_date));
        } else if (holidayPlanner.getTitle() == null || holidayPlanner.getTitle().trim().length() == 0) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_title));
        } else if (holidayPlanner.getDescription() == null || holidayPlanner.getDescription().trim().length() == 0) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_description));
        } else {
            holidayPlannerAPICall(holidayPlanner);
        }
    }

    private void datePicker(HolidayPlanner holidayPlanner, boolean isFrom) {
        String date;
        if (isFrom){
            date=holidayPlanner.getFromDate();
        }else{
            date=holidayPlanner.getToDate();
        }
        dates = StringUtil.getDay(date);
        monthes = StringUtil.getMonth(date);
        years = StringUtil.getYear(date);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                years = year;
                monthes = month + 1;
                dates = date;
                String dateString = String.valueOf(years) + "-";
                if (monthes <= 9) {
                    dateString += "0" + String.valueOf(monthes) + "-";
                } else {
                    dateString += String.valueOf(monthes) + "-";
                }
                if (dates <= 9) {
                    dateString += "0" + String.valueOf(dates);
                } else {
                    dateString += String.valueOf(dates);
                }
                if (isFrom) {
                    holidayPlanner.setFromDate(dateString);
                } else {
                    holidayPlanner.setToDate(dateString);
                }
            }
        }, years, monthes, dates);

        if (isFrom){
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        }else {
            datePickerDialog.getDatePicker().setMinDate(StringUtil.getDateLong(holidayPlanner.getFromDate()));
        }
        datePickerDialog.show();
    }

    private void holidayPlannerAPICall(HolidayPlanner holidayPlanner) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable;

            if (holidayPlanner.getPlanId() == null || holidayPlanner.getPlanId().trim().length() == 0) {
                observable = api.addOffDayPlan(MySession
                        .getInstance
                                (activity).getUser().getToken(), holidayPlanner);
            } else {
                observable = api.updateOffDayPlan(MySession
                        .getInstance
                                (activity).getUser().getToken(), holidayPlanner);
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            isUpdated = true;
                            Intent intent = new Intent();
                            activity.setResult(Activity.RESULT_OK, intent);
                            activity.finish();
                            Toast.makeText(activity,responses.body().getMessage(),Toast.LENGTH_LONG).show();
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code
                                    (), responses.body().getMessage());
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
                        }
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }
}
