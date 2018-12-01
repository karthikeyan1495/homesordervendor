package com.homesordervendor.user.holidayplanner.activelist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.holidayplanner.HolidayPlannerDetailActivity;
import com.homesordervendor.user.holidayplanner.HolidayPlannerTabFragment;
import com.homesordervendor.user.holidayplanner.model.HolidayPlanner;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/8/18.
 */

public class ActiveItemVM extends java.util.Observable{

    Activity activity;

    MyProgressDialog myProgressDialog;

    public ActiveItemVM(Activity activity){
        this.activity=activity;
        this.myProgressDialog=new MyProgressDialog();
    }

    public void onClickItem(View view, HolidayPlanner holidayPlanner){
        if (holidayPlanner.getStatus().trim().toLowerCase().equals("active")){
            Intent intent=new Intent(activity, HolidayPlannerDetailActivity.class);
            intent.putExtra("holidayPlanner",holidayPlanner);
            HolidayPlannerTabFragment.holidayPlannerTabFragment.startActivityForResult(intent, HolidayPlannerTabFragment
                    .UPDATE_CODE);
        }
    }

    public void onClickDelete(View view, HolidayPlanner holidayPlanner){
        holidayPlannerDeleteAPICall(holidayPlanner);
    }

    private void holidayPlannerDeleteAPICall(HolidayPlanner holidayPlanner){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.deleteOffDayPlan
                    (holidayPlanner.getPlanId(),MySession
                    .getInstance
                            (activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses
                                    .code(), responses.body().getMessage());
                            setChanged();
                            notifyObservers(holidayPlanner);
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses
                                        .code(), responses.body().getMessage());
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
