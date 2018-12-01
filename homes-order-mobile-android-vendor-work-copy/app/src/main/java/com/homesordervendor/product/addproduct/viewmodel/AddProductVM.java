package com.homesordervendor.product.addproduct.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.product.addproduct.AddProductActivity;
import com.homesordervendor.product.addproduct.model.AddProductResponse;
import com.homesordervendor.product.addproduct.model.ImageURI;
import com.homesordervendor.product.addproduct.model.ProductModel;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.util.APIUtil;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/2/18.
 */

public class AddProductVM {

    Activity activity;
    MyProgressDialog myProgressDialog;
    Button nextButton=null;


    public AddProductVM(Activity  activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickClose(View view){
        if (activity instanceof AddProductActivity) {
            AddProductActivity addProductActivity = (AddProductActivity) activity;
            addProductActivity.confirmationAlert();
        }

        /*Intent intent = new Intent();
        activity.setResult(Activity.RESULT_CANCELED, intent);
        activity.finish();*/
    }


    public void onClickBack(View view){
        if (activity instanceof AddProductActivity){
            if (nextButton!=null){
                nextButton.setText(activity.getString(R.string.next));
            }
            AddProductActivity addProductActivity=(AddProductActivity) activity;
            if (addProductActivity.currentPage==1) {
                addProductActivity.confirmationAlert();
            }else if(addProductActivity.currentPage==2){
                addProductActivity.currentPage=1;
                addProductActivity.pushScreen(addProductActivity.productCategoryFragment);
            }else if(addProductActivity.currentPage==3){
                addProductActivity.currentPage=2;
                addProductActivity.pushScreen(addProductActivity.productInformationFragment);
            }else if(addProductActivity.currentPage==4){
                addProductActivity.currentPage=3;
                addProductActivity.pushScreen(addProductActivity.productImageFragment);
            }
        }
    }

    public void onClickNext(View view){
        nextButton=(Button)view;
        nextButton.setText(activity.getString(R.string.next));
        if (activity instanceof AddProductActivity){
            AddProductActivity addProductActivity=(AddProductActivity) activity;
            if (addProductActivity.currentPage==1) {
                categoryValidation(addProductActivity);
            }else if(addProductActivity.currentPage==2){
                productInfoValidation(addProductActivity);
            }else if(addProductActivity.currentPage==3){
                imageValidation(addProductActivity,view);
            }else if(addProductActivity.currentPage==4){
                nextButton.setText(activity.getString(R.string.submit));
                addProductAPICall(addProductActivity.productModel);
            }
        }
    }

    private void categoryValidation(AddProductActivity addProductActivity){
        ProductModel productModel=addProductActivity.productModel;
        if (!productModel.isFood()&&!productModel.isFashion()){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_select_category));
        }else if (productModel.isFood()&&!productModel.isFoodSubCategory()){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_select_sub_category));
        }else if(productModel.isFashion()&&!productModel.isTargetGroup()){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_select_target_group));
        }else if(productModel.isFashion()&&productModel.isTargetGroup()&&!productModel.isTargetGroupSubCategory()){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_select_sub_category));
        }else if(productModel.getProductNameEnglish().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_product_name_english));
        }else if(productModel.getProductDescriptionEnglish().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_product_description_english));
        }else{
            addProductActivity.currentPage=2;
            addProductActivity.pushScreen(addProductActivity.productInformationFragment);
        }
    }

    private void productInfoValidation(AddProductActivity addProductActivity){
        ProductModel productModel=addProductActivity.productModel;
        if(productModel.getProductNameArabic().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_product_name_arabic));
        }else if(productModel.getProductDescriptionArabic().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_product_description_arabic));
        }else if(productModel.getPrice().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .error_price));
        }else if(productModel.getLimit().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .error_per_day_capacity));
        }else if(productModel.getTime().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .error_handling_time_days));
        }else {
            addProductActivity.currentPage=3;
            addProductActivity.pushScreen(addProductActivity.productImageFragment);
        }
        /*else if(productModel.getSize().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_size));
        }else if(productModel.getWeight().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_weight));
        }else if(productModel.getColor().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_color));
        }*/
    }

    private void imageValidation(AddProductActivity addProductActivity,View view){
        ImageURI imageURI=addProductActivity.productModel.getImageURI();
        List<ImageURI> list=addProductActivity.productModel.getImageURIList();
        if (imageURI.getUri()==null){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_front_image));
        }else if(list.size()-1==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_feature_image));
        }else {
            Button button=(Button)view;
            button.setText(activity.getString(R.string.submit));
            addProductActivity.currentPage=4;
            addProductActivity.pushScreen(addProductActivity.productPreviewFragment);
        }
    }

    private void addProductAPICall(ProductModel productModel){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<AddProductResponse>> observable;
            if (productModel.getProductID()==null||productModel.getProductID().trim().length()==0) {
                observable = api.addProduct(MySession.getInstance(activity).getUser().getToken(), APIUtil.generateAddProduct(activity, productModel));
            }else{
                observable = api.updateProduct(MySession.getInstance(activity).getUser().getToken(), APIUtil
                        .generateAddProduct(activity, productModel));
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {

                            Intent intent = new Intent();
                            activity.setResult(Activity.RESULT_OK, intent);
                            activity.finish();

                            Toast.makeText(activity,responses.body().getMessage(),Toast.LENGTH_LONG).show();
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
