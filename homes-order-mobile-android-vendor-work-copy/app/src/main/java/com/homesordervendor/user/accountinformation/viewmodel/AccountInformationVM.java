package com.homesordervendor.user.accountinformation.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.accountinformation.model.AccountInfo;
import com.homesordervendor.user.accountinformation.model.CitiesResponse;
import com.homesordervendor.user.accountinformation.model.CoveredArea;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.StringUtil;
import com.homesordervendor.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/3/18.
 */

public class AccountInformationVM extends java.util.Observable {
    Activity activity;
    MyProgressDialog myProgressDialog;
    List<CoveredArea> coveredAreas=new ArrayList<>();
    User user=new User();
    int years, dates, monthes;

    public AccountInformationVM(Activity activity) {
        this.activity = activity;
        myProgressDialog = new MyProgressDialog();
        accountInformationAPICall();
    }

    public void onClickClose(View view){
        Util.getInstance().hideKeyboard(activity);
        activity.finish();
    }

    public void onClickDate(View view, AccountInfo accountInfo){
        datePicker(accountInfo);
    }


    private void datePicker(AccountInfo accountInfo){
        dates=StringUtil.getDay(accountInfo.getDob());
        monthes=StringUtil.getMonth(accountInfo.getDob());
        years=StringUtil.getYear(accountInfo.getDob());

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, R.style.DatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                years = year;
                monthes = month + 1;
                dates = date;
                String dateString=String.valueOf(years)+"-";
                if (monthes <= 9) {
                    dateString += "0" + String.valueOf(monthes)+"-";
                }else{
                    dateString += String.valueOf(monthes)+"-";
                }
                if (dates <= 9) {
                    dateString += "0" + String.valueOf(dates);
                }else{
                    dateString += String.valueOf(dates);
                }
                accountInfo.setDob(dateString);
            }
        }, years, monthes, dates);

        datePickerDialog.getDatePicker().setMaxDate(StringUtil.getDOBMaxDate());
        datePickerDialog.show();
    }

    public void onClickCountry(View view, AccountInfo accountInfo){
        //countryPicker(accountInfo);
    }

    public void onClickCity(View view, AccountInfo accountInfo){
        if (accountInfo.getCountryId().length()>0){
            cityPicker(accountInfo);
        }else{
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_select_country));
        }
    }

    public void onClickSave(View view, AccountInfo accountInfo){
        validation(accountInfo);
    }

    private void validation(AccountInfo accountInfo){
        if (accountInfo.getDob().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_select_dob));
        }else if (accountInfo.getStreet().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_enter_address));
        }else if (accountInfo.getCountryId().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_select_country));
        }else if (accountInfo.getCity().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_select_city));
        }else if (accountInfo.getGender()==null||accountInfo.getGender().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_select_gender));
        }else {
            updateAccountInformationAPICall(accountInfo);
        }
    }

    public User getUser(){
        return user;
    }

    public List<CoveredArea> getCountry(){
        return coveredAreas;
    }

    private void cityPicker(AccountInfo accountInfo) {
        if (coveredAreas != null) {
            CharSequence[] items =new CharSequence[coveredAreas.get(accountInfo.getCountryPosition()).getAreas().size()];
            for (int i = 0; i < coveredAreas.get(accountInfo.getCountryPosition()).getAreas().size(); i++) {
                items[i] = StringUtil.getLanguageName(coveredAreas.get(accountInfo.getCountryPosition()).getAreas().get(i).getAreaNameEN(),
                        coveredAreas.get(accountInfo.getCountryPosition()).getAreas().get(i).getAreaNameAR());
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.MyDialogTheme);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    accountInfo.setCity(String.valueOf(items[item]));
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void countryPicker(AccountInfo accountInfo) {
        if (coveredAreas != null) {
            CharSequence[] items =new CharSequence[coveredAreas.size()];
            for (int i = 0; i < coveredAreas.size(); i++) {
                items[i] = StringUtil.getLanguageName(coveredAreas.get(i).getCountryNameEN(), coveredAreas.get(i).getCountryNameAR());
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.MyDialogTheme);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    accountInfo.setCountryNameAR(coveredAreas.get(item).getCountryNameAR());
                    accountInfo.setCountryNameEN(coveredAreas.get(item).getCountryNameEN());
                    accountInfo.setCountryId(coveredAreas.get(item).getCountryCode());
                    accountInfo.setCity("");
                    accountInfo.setCountryPosition(item);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void updateAccountInformationAPICall(AccountInfo accountInfo)
    {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<User>> observable = api.addressUpdate(MySession.getInstance(activity).getUser().getToken(),accountInfo);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            User user=MySession.getInstance(activity).getUser();
                            user.getProfile().setIsaddress("true");
                            MySession.getInstance(activity).saveUser(user);
                            activity.finish();
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
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

    private void accountInformationAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<User>> observable = api.accountInformation(MySession.getInstance(activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            user=responses.body();
                            countryAPICall();
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

    private void countryAPICall(){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<CitiesResponse>> observable = api.cities();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            coveredAreas=responses.body().getCoveredarea();
                            setChanged();
                            notifyObservers();
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
