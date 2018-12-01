package com.homesordervendor.product.productlist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.product.addproduct.AddProductActivity;
import com.homesordervendor.product.productlist.ProductListFragment;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.product.productlist.model.ProductListResponse;
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
 * Created by mac on 3/2/18.
 */

public class ProductListVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    List<ProductItem> productItems=new ArrayList<>();
    public ObservableBoolean isNoData=new ObservableBoolean(false);
    ProductListFragment productListFragment;

    public ProductListVM(Activity activity,ProductListFragment productListFragment){
        this.activity=activity;
        myProgressDialog =new MyProgressDialog();
        this.productListFragment=productListFragment;
        //productListAPICall();
    }

    public void onClickAddProduct(View view){
        productListFragment.startActivityForResult(new Intent(activity, AddProductActivity.class)
                ,ProductListFragment.PRODUCT_UPDATE_CODE);
    }

    public List<ProductItem> getProductItems(){
        return productItems;
    }

    public void productListAPICall(){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<ProductListResponse>> observable = api.productList(MySession.getInstance(activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            isNoData.set(false);
                            productItems=responses.body().getItems();
                        } else {
                            productItems.clear();
                            isNoData.set(true);
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            } else {
                                if (APIErrorHandler.getInstance().messageParsing(responses
                                        .errorBody().string())!=4001) {
                                    APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                                }
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
