package com.homesordervendor.user.subscribe.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.user.subscribe.model.Plan;
import com.homesordervendor.user.subscribe.model.Subscribe;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/22/18.
 */

public class PlanItemVM {

    Activity activity;
    MyProgressDialog myProgressDialog;

    public PlanItemVM(Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickSubscribe(View view, Plan plan){
        Subscribe subscribe=new Subscribe();
        subscribe.setPack(plan.getTitle());
        subscribeAPICall(subscribe);

    }

    private void subscribeAPICall(Subscribe subscribe){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.subscribe(MySession.getInstance(activity).getUser().getToken(),subscribe);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            User user=MySession.getInstance(activity).getUser();
                            user.getProfile().setIssubscriped("true");
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

}
