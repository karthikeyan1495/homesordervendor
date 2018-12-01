package com.homesordervendor.orders.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.orders.CancelOrderFragment;
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

public class OrderDetailVM extends java.util.Observable {

    Activity activity;
    MyProgressDialog myProgressDialog;

    OrderItem orderItemDetail=new OrderItem();

    public OrderDetailVM(Activity activity, String orderId) {
        this.activity = activity;
        myProgressDialog = new MyProgressDialog();
        orderDetailAPICall(orderId);
    }

    public void onClickClose(View view) {
        activity.finish();
    }

    public void onClickStatusOption(View view,OrderItem orderItem){
        statusPicker(orderItem);
    }

    private void statusPicker(OrderItem orderItem) {
        String[] statusArray=null;
        if (orderItem.getStatusKey().equals(OrderStatus.NEW.getValue())) {
            statusArray=activity.getResources().getStringArray(R.array
                    .new_order_status_array);
        }else if(orderItem.getStatusKey().equals(OrderStatus.PROCESSING.getValue())){
            statusArray=activity.getResources().getStringArray(R.array
                    .order_processing_status_array);
        }else if(orderItem.getStatusKey().equals(OrderStatus.DISPATCH.getValue())){
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
            if (orderItem.getStatusKey().equals(OrderStatus.NEW.getValue())){
                statusChangeAPICall(orderItem,OrderStatus.PROCESSING);
            }else if(orderItem.getStatusKey().equals(OrderStatus.PROCESSING.getValue())){
                statusChangeAPICall(orderItem,OrderStatus.DISPATCH);
            }else if(orderItem.getStatusKey().equals(OrderStatus.DISPATCH.getValue())){
                completeAPICall(orderItem);
            }
        }else if (position==1){
            if (orderItem.getStatusKey().equals(OrderStatus.DISPATCH.getValue())){
                statusChangeAPICall(orderItem,OrderStatus.RETURN);
            }else {
                orderCancelDialog(orderItem);
            }
        }else if(position==2){
            orderCancelDialog(orderItem);
        }
    }

    public OrderItem getOrderItemDetail(){
        return orderItemDetail;
    }

    private void orderCancelDialog(OrderItem orderItem){
        CancelOrderFragment cancelOrderFragment = new CancelOrderFragment();
        cancelOrderFragment.setOrderItem(orderItem);
        cancelOrderFragment.setOrderCancelListener(item -> {
            orderDetailAPICall(item.getOrderID());
            //setChanged();
            //notifyObservers(item);
        });
        if (activity instanceof OrderDetailActivity) {
            OrderDetailActivity orderDetailActivity=(OrderDetailActivity)activity;
            cancelOrderFragment.show(orderDetailActivity.getSupportFragmentManager(), "dialog");
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
                            orderDetailAPICall(orderItem.getOrderID());
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
                            orderDetailAPICall(orderItem.getOrderID());
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


    public void orderDetailAPICall(String orderId) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<OrderItem>> observable = api.orderById(orderId, MySession
                    .getInstance
                            (activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            orderItemDetail=responses.body();
                            setChanged();
                            notifyObservers();
                        } else {
                            if (responses.body() != null) {
                                 APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
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
