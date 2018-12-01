package com.homesordervendor.product.productlist.viewmodel;

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
import com.homesordervendor.product.addproduct.AddProductActivity;
import com.homesordervendor.product.featured.FeaturedActivity;
import com.homesordervendor.product.featured.model.FeatureEligible;
import com.homesordervendor.product.productlist.ProductModelParser;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.product.promotion.PromotionActivity;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.StringUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/5/18.
 */

public class MyProductItemVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;

    public MyProductItemVM(Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickMyProductItem(View view,ProductItem productItem){
        if (!StringUtil.showProductStatusView(productItem.getStatuskey())) {
            Intent intent = new Intent(activity, AddProductActivity.class);
            intent.putExtra("productModel", ProductModelParser.productItemParser(productItem));
            activity.startActivity(intent);
        }
    }

    public void onClickDelete(View view, ProductItem productItem){
        productDeleteAlert(productItem);
    }

    public void onClickPromotion(View view, ProductItem productItem){
        if (!productItem.isInPromotion()) {
            Intent intent = new Intent(activity, PromotionActivity.class);
            intent.putExtra("productItem", productItem);
            activity.startActivity(intent);
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .already_promoted));
        }
    }

    public void onClickAddToFeatured(View view, ProductItem productItem){
        /* MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.featured_product_facility_not_available));*/
       if (!productItem.isInFeature()) {
            featureEligbleAPICall(productItem);
        }
    }

    private void featureEligbleAPICall(ProductItem productItem){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<FeatureEligible>> observable = api.featureEligble(productItem.getProductID(),MySession.getInstance(activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            if (responses.body().isStatus()){
                                Intent intent=new Intent(activity, FeaturedActivity.class);
                                intent.putExtra("productItem",productItem);
                                activity.startActivity(intent);
                            }else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            }

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

    private void ProductDeleteAPICall(ProductItem productItem){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.productDelete(productItem.getProductID(),MySession.getInstance(activity).getUser().getToken());
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

    private void productDeleteAlert(ProductItem productItem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(activity.getString(R.string.delete_product_alert));
        alertDialogBuilder.setPositiveButton(activity.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        ProductDeleteAPICall(productItem);
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
