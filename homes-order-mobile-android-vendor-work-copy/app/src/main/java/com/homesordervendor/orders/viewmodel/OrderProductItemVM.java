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
import com.homesordervendor.orders.OrderStatus;
import com.homesordervendor.product.productlist.model.ProductItem;
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

public class OrderProductItemVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    public OrderProductItemVM(Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }
    public void onClickStatusChange(View view, ProductItem productItem){
        statusPicker(productItem);
    }

    private void statusPicker(ProductItem productItem) {
        String[] statusArray=null;
        if (productItem.getStatusOrderItemKey().equals(OrderStatus.NEW.getValue())) {
            statusArray=activity.getResources().getStringArray(R.array
                    .new_order_status_array);
        }else if(productItem.getStatusOrderItemKey().equals(OrderStatus.PROCESSING.getValue())){
            statusArray=activity.getResources().getStringArray(R.array
                    .order_processing_status_array);
        }else if(productItem.getStatusOrderItemKey().equals(OrderStatus.DISPATCH.getValue())){
            statusArray=activity.getResources().getStringArray(R.array
                    .order_dispatch_status_array);
        }

        if (statusArray!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(statusArray, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                     onStatusChange(item,productItem);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void onStatusChange(int position, ProductItem productItem){
        if (position==0){
            if (productItem.getStatusOrderItemKey().equals(OrderStatus.NEW.getValue())){
                statusChangeAPICall(productItem,OrderStatus.PROCESSING);
            }else if(productItem.getStatusOrderItemKey().equals(OrderStatus.PROCESSING.getValue())){
                statusChangeAPICall(productItem,OrderStatus.DISPATCH);
            }else if(productItem.getStatusOrderItemKey().equals(OrderStatus.DISPATCH.getValue())){
                statusChangeAPICall(productItem,OrderStatus.COMPLETE);
            }
        }else if (position==1){
            if (productItem.getStatusOrderItemKey().equals(OrderStatus.DISPATCH.getValue())){
                statusChangeAPICall(productItem,OrderStatus.DELIVERY_RETURN);
            }else {
                statusChangeAPICall(productItem,OrderStatus.CANCELED);
            }
        }else if(position==2){
            statusChangeAPICall(productItem,OrderStatus.CANCELED);
        }
    }

    private void statusChangeAPICall(ProductItem productItem, OrderStatus orderStatus){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.changeOrderItemStatus
                    (productItem.getItemID(),orderStatus.getValue(),MySession.getInstance
                            (activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code
                                    (), responses.body().getMessage());
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
