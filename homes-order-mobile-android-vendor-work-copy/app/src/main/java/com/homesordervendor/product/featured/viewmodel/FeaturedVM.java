package com.homesordervendor.product.featured.viewmodel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;

import com.homesordervendor.MyApp;
import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.databinding.ActivityFeaturedBinding;
import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.product.featured.model.BlockedDates;
import com.homesordervendor.product.featured.model.FeaturedRequest;
import com.homesordervendor.product.featured.model.SubcategoryID;
import com.homesordervendor.product.featured.payment.PaymentUtil;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/5/18.
 */

public class FeaturedVM {

    Activity activity;
    MyProgressDialog myProgressDialog;
    ProductItem productItem;
    BlockedDates blockedDates=new BlockedDates();
    ActivityFeaturedBinding binding;


    int years, dates, monthes;

    public FeaturedVM(Activity activity, ProductItem productItem, ActivityFeaturedBinding binding){
        this.activity=activity;
        this.binding=binding;
        myProgressDialog=new MyProgressDialog();
        this.productItem=productItem;
        if (productItem.getSubCategoryID().size()!=0) {
            blockedDateAPICall();
        }
    }

    public void onClickClose(View view)
    {
        activity.finish();
    }

    public void onClickFromDate(View view, FeaturedRequest featuredRequest){
        datePicker(featuredRequest);
    }

    public void onClickSetFeatured(View view, FeaturedRequest featuredRequest){
        if (featuredRequest.getStartDate()==null||featuredRequest.getStartDate().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_from_date));
        }else {
           // setFeatureDateAPICall(featuredRequest);
            MySession.getInstance(activity).saveFeaturedRequest(featuredRequest);
            if (MySession.getInstance(MyApp.getContext()).getCurrency().equals(MyApp.getContext().getString(R.string.aed))) {
                if (blockedDates.getFeaturedPrice().trim().length()==0){
                    MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
                }else {
                    PaymentUtil.telrPaymentGatewayInitialization(activity, blockedDates.getFeaturedPrice());
                }
            }else {
                if (blockedDates.getFeaturedPriceInSAR().trim().length()==0){
                    MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
                }else {
                    PaymentUtil.telrPaymentGatewayInitialization(activity, blockedDates.getFeaturedPriceInSAR());
                }
            }
        }
    }

    private void setFeatureDateAPICall(FeaturedRequest featuredRequest){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
           // SubcategoryID subcategoryID=new SubcategoryID();
            //subcategoryID.setSubcategoryID(productItem.getSubCategoryID().get(0));
            Observable<Response<GeneralResponse>> observable = api.setFeature(MySession.getInstance(activity).getUser().getToken(),featuredRequest);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
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

    private void blockedDateAPICall(){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            SubcategoryID subcategoryID=new SubcategoryID();
            subcategoryID.setSubcategoryID(productItem.getSubCategoryID().get(0));
            Observable<Response<BlockedDates>> observable = api.featureAvailable(subcategoryID);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            blockedDates=responses.body();
                            binding.setBlockedDates(blockedDates);

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

    private void datePicker(FeaturedRequest featuredRequest){

        dates = StringUtil.getDay(featuredRequest.getStartDate());
        monthes = StringUtil.getMonth(featuredRequest.getStartDate());
        years = StringUtil.getYear(featuredRequest.getStartDate());

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
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

                for (int i=0;i<blockedDates.getBlockedDates().size();i++){
                    if (blockedDates.getBlockedDates().get(i).contains(dateString)){
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_blocked_date));
                        return;
                    }
                }

                featuredRequest.setStartDate(dateString);
                featuredRequest.setEndDate(nextDate(dateString));

            }
        }, years, monthes, dates);
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.getDatePicker().setMaxDate(StringUtil.getFeaturedMaxDate());

        datePickerDialog.show();
    }

    private String nextDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            convertedDate=null;
        }

        if (convertedDate!=null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(convertedDate);
            cal.add(Calendar.DAY_OF_MONTH, 15);
            Date yourDate = cal.getTime();
            return dateFormat.format(yourDate);
        }
        return "";
    }

}
