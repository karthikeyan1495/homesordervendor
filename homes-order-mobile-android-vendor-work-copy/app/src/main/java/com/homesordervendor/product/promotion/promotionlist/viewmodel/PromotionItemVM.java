package com.homesordervendor.product.promotion.promotionlist.viewmodel;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.product.promotion.PromotionActivity;
import com.homesordervendor.product.promotion.promotionlist.PromotionListFragment;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/5/18.
 */

public class PromotionItemVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    PromotionListFragment promotionListFragment;

    public PromotionItemVM(Activity activity,PromotionListFragment promotionListFragment){
        this.activity=activity;
        this.promotionListFragment=promotionListFragment;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickPromotionItem(View view, ProductItem productItem){
        Intent intent=new Intent(activity, PromotionActivity.class);
        intent.putExtra("productItem",productItem);
        promotionListFragment.startActivityForResult(intent,PromotionListFragment.PROMOTION_UPDATE_CODE);
    }

    public void onClickStopPromotion(View view, ProductItem productItem){
        stopPromotionAlert(productItem);
    }

    private void stopPromotionAPICall(ProductItem productItem){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.stopPromotion(productItem.getProductID(),MySession.getInstance(activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            setChanged();
                            notifyObservers(productItem);
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

    private void stopPromotionAlert(ProductItem productItem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(activity.getString(R.string.stop_promotion_alert));
        alertDialogBuilder.setPositiveButton(activity.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        stopPromotionAPICall(productItem);
                    }
                });

        alertDialogBuilder.setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
