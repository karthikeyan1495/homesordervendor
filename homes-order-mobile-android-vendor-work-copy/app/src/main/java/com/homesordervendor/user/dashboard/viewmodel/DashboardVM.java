package com.homesordervendor.user.dashboard.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.dashboard.DashboardFragment;
import com.homesordervendor.user.dashboard.OrderCount;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 15/03/18.
 */

public class DashboardVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    OrderCount orderCount;
    DashboardFragment.OnOrderCountListener onOrderCountListener;

    public DashboardVM(Activity activity) {
        this.activity = activity;
        myProgressDialog = new MyProgressDialog();
        onOrderCountListener=(DashboardFragment.OnOrderCountListener)activity;
        orderCountAPICall();

    }

    public OrderCount getOrderCount(){
        return orderCount;
    }


    public void onClickCount(View view){
        if (onOrderCountListener!=null){
            onOrderCountListener.onClickOrderCountItem(Integer.valueOf(view.getTag().toString()));
        }
        //Log.e("Position",""+Integer.valueOf(view.getTag().toString()));

    }

    public void orderCountAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<OrderCount>> observable = api.orderCount(MySession.getInstance
                    (activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            orderCount=responses.body();
                            setChanged();
                            notifyObservers();
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            } else {
                                if (APIErrorHandler.getInstance().messageParsing(responses
                                        .errorBody().string()) != 5001) {
                                    APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                                }
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
