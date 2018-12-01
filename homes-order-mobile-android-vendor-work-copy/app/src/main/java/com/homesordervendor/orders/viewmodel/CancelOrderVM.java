package com.homesordervendor.orders.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.orders.CancelOrderFragment;
import com.homesordervendor.orders.model.CancelOrder;
import com.homesordervendor.orders.model.OrderItem;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 18/04/18.
 */

public class CancelOrderVM {

    Activity activity;
    CancelOrderFragment cancelOrderFragment;
    MyProgressDialog myProgressDialog;
    OrderItem orderItem;
    CancelOrderFragment.OrderCancelListener orderCancelListener;
    public CancelOrderVM(@NonNull Activity activity, CancelOrderFragment cancelOrderFragment,
                         OrderItem orderItem,CancelOrderFragment.OrderCancelListener orderCancelListener){
        this.activity=activity;
        this.cancelOrderFragment=cancelOrderFragment;
        this.orderItem=orderItem;
        this.orderCancelListener=orderCancelListener;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickClose(View view){
        cancelOrderFragment.dismiss();
    }

    public void onClickSubmit(View view, CancelOrder cancelOrder){
        if (cancelOrder.getComment().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .please_enter_cancel_reason));

        }else{
            cancelAPICall(cancelOrder);
        }
    }

    private void cancelAPICall(CancelOrder cancelOrder){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.cancelOrder(orderItem
                    .getOrderID(), MySession
                    .getInstance
                            (activity).getUser().getToken(),cancelOrder);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code
                                    (), responses.body().getMessage());

                            if (orderCancelListener!=null){
                                orderCancelListener.orderCancelled(orderItem);
                            }
                            cancelOrderFragment.dismiss();

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