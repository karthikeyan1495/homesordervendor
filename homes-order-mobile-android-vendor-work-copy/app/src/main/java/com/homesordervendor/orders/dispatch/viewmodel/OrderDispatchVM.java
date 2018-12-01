package com.homesordervendor.orders.dispatch.viewmodel;

import android.app.Activity;
import android.databinding.ObservableBoolean;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.orders.OrderStatus;
import com.homesordervendor.orders.model.OrderItem;
import com.homesordervendor.sharedpreferences.MySession;
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
 * Created by innoppl on 10/03/18.
 */

public class OrderDispatchVM extends java.util.Observable{

    Activity activity;

    MyProgressDialog myProgressDialog;
    List<OrderItem> orderItemList=new ArrayList<>();
    public ObservableBoolean isNoData=new ObservableBoolean(false);

    public OrderDispatchVM(Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public List<OrderItem> getOrderItemList(){
        return orderItemList;
    }

    public void orderListAPICall(){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<List<OrderItem>>> observable = api.orderList(MySession
                    .getInstance(activity).getUser().getToken(), OrderStatus.DISPATCH.getValue());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            isNoData.set(false);
                            orderItemList=responses.body();
                        } else {
                            orderItemList.clear();
                            isNoData.set(true);
                            if (responses.body() != null) {
                                // APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            } else {
                                if (APIErrorHandler.getInstance().messageParsing(responses
                                        .errorBody().string())!=5001) {
                                    APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                                }                            }
                        }
                        setChanged();
                        notifyObservers();
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));

                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }
}
