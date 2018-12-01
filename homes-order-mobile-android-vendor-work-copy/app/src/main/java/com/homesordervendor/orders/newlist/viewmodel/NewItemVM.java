package com.homesordervendor.orders.newlist.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.orders.CancelOrderFragment;
import com.homesordervendor.orders.NavigationFromEnum;
import com.homesordervendor.orders.OrderDetailActivity;
import com.homesordervendor.orders.OrderStatus;
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
 * Created by mac on 3/6/18.
 */

public class NewItemVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    OrderStatus orderStatus;

    public NewItemVM(Activity activity,OrderStatus orderStatus){
        this.activity=activity;
        this.orderStatus=orderStatus;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickNewItem(View view,OrderItem orderItem){
        Intent intent=new Intent(activity, OrderDetailActivity.class);
        intent.putExtra("orderItem",orderItem);
        intent.putExtra("navigationFrom", NavigationFromEnum.ORDER_LIST.getValue());
        activity.startActivity(intent);
    }

    public void onClickStatusOption(View view,OrderItem orderItem){
        statusPicker(orderItem);
    }

    private void statusPicker(OrderItem orderItem) {
        String[] statusArray=null;
        if (orderStatus==OrderStatus.NEW) {
            statusArray=activity.getResources().getStringArray(R.array
                    .new_order_status_array);
        }else if(orderStatus==OrderStatus.PROCESSING){
            statusArray=activity.getResources().getStringArray(R.array
                    .order_processing_status_array);
        }else if(orderStatus==OrderStatus.DISPATCH){
            statusArray=activity.getResources().getStringArray(R.array
                    .order_dispatch_status_array);
        }

        if (statusArray!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(statusArray, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    onStatusChange(item, orderItem);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void onStatusChange(int position, OrderItem orderItem){
        if (position==0){
            if (orderStatus==OrderStatus.NEW){
                statusChangeAPICall(orderItem,OrderStatus.PROCESSING);
            }else if(orderStatus==OrderStatus.PROCESSING){
                statusChangeAPICall(orderItem,OrderStatus.DISPATCH);
            }else if(orderStatus==OrderStatus.DISPATCH){
                completeAPICall(orderItem);
            }
        }else if (position==1){
            if (orderStatus==OrderStatus.DISPATCH){
                statusChangeAPICall(orderItem,OrderStatus.RETURN);
            }else {
                orderCancelDialog(orderItem);
                //cancelAPICall(orderItem);
            }
        }else if(position==2){
            orderCancelDialog(orderItem);
            //cancelAPICall(orderItem);
        }
    }

    private void orderCancelDialog(OrderItem orderItem){
        CancelOrderFragment cancelOrderFragment = new CancelOrderFragment();
        cancelOrderFragment.setOrderItem(orderItem);
        cancelOrderFragment.setOrderCancelListener(item -> {
            setChanged();
            notifyObservers(item);
        });
        if (activity instanceof NavigationActivity) {
            NavigationActivity navigationActivity=(NavigationActivity)activity;
            cancelOrderFragment.show(navigationActivity.getSupportFragmentManager(), "dialog");
        }
    }

    private void completeAPICall(OrderItem orderItem){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.completeOrder(orderItem
                    .getOrderID(),MySession
                    .getInstance
                            (activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code
                                    (), responses.body().getMessage());
                            setChanged();
                            notifyObservers(orderItem);
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

    private void statusChangeAPICall(OrderItem orderItem,OrderStatus orderStatus){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.changeOrderStatus(orderItem
                    .getOrderID(),orderStatus.getValue(),MySession
                    .getInstance
                    (activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                             APIErrorHandler.getInstance().errorHandler(activity, responses.code
                                     (), responses.body().getMessage());
                            setChanged();
                            notifyObservers(orderItem);
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

   /* private void cancelAPICall(OrderItem orderItem){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.cancelOrder(orderItem
                    .getOrderID(),MySession
                    .getInstance
                            (activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code
                                    (), responses.body().getMessage());
                            setChanged();
                            notifyObservers(orderItem);
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
    }*/

}


