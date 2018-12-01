package com.homesordervendor.user.subscribe.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.user.subscribe.model.Plan;
import com.homesordervendor.user.subscribe.model.PlanResponse;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/22/18.
 */

public class SubscribeVM extends java.util.Observable{

    Activity activity;

    MyProgressDialog myProgressDialog;

    List<Plan> plans=new ArrayList<>();

    public SubscribeVM(Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
        plansAPICall();
    }

    public void onClickClose(View view){
        activity.finish();
    }

    public List<Plan> getPlans(){
        return plans;
    }

    private void plansAPICall(){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<PlanResponse>> observable = api.plans();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            plans=responses.body().getPlan();
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
