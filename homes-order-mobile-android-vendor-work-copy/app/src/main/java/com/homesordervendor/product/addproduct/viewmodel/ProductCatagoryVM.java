package com.homesordervendor.product.addproduct.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.product.addproduct.model.CategoryResposnse;
import com.homesordervendor.product.addproduct.model.ProductModel;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/2/18.
 */

public class ProductCatagoryVM {

    Activity activity;
    MyProgressDialog myProgressDialog;
    ProductModel productModel;

    public ProductCatagoryVM(Activity activity,ProductModel productModel){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
        this.productModel=productModel;
        if (productModel.getCategories().size()==0) {
            categoryAPICall();
        }
    }

    public void onClickFood(View view, ProductModel productModel){

        if (productModel.getProductID()==null||productModel.getProductID().trim().length()==0) {
            if (productModel.getCategories().size() >= 1) {
                productModel.setCategoryId(productModel.getCategories().get(0).getId());
                productModel.setCategoryName(productModel.getCategories().get(0).getName());
            }

            productModel.setFashion(false);
            productModel.setFood(true);
        }else{
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .can_not_edit_main_category));
        }
    }

    public void onClickFashion(View view, ProductModel productModel){
        if (productModel.getProductID()==null||productModel.getProductID().trim().length()==0) {
            if (productModel.getCategories().size() >= 2) {
                productModel.setCategoryId(productModel.getCategories().get(1).getId());
                productModel.setCategoryName(productModel.getCategories().get(1).getName());
            }
            productModel.setFood(false);
            productModel.setFashion(true);
        }else{
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .can_not_edit_main_category));
        }
    }

    public void onClickFoodSubCategory(View view, ProductModel productModel){
        picker(PickerOption.FOOD_SUB_CATEGORY,productModel);

    }

    public void onClickFashionTargetGroup(View view,ProductModel productModel){
        picker(PickerOption.FASHION_TARGET_GROUP,productModel);

    }

    public void onClickFoodFashionTargetGroupSubCategory(View view,ProductModel productModel){
        picker(PickerOption.FASHION_TARGET_GROUP_SUB_CATEGORY,productModel);
    }

    private void picker(PickerOption pickerOption,ProductModel productModel){
            CharSequence[] items=null;
            if (PickerOption.FOOD_SUB_CATEGORY==pickerOption) {
                if (productModel.getCategories().size()>=1){
                    items = new CharSequence[productModel.getCategories().get(0).getChild().size()];
                    for (int i = 0; i < productModel.getCategories().get(0).getChild().size(); i++) {
                        items[i]=productModel.getCategories().get(0).getChild().get(i).getName();
                    }
                }
            }else if (PickerOption.FASHION_TARGET_GROUP==pickerOption){
                if (productModel.getCategories().size()>=2){
                    items = new CharSequence[productModel.getCategories().get(1).getChild().size()];
                    for (int i = 0; i < productModel.getCategories().get(1).getChild().size(); i++) {
                        items[i]=productModel.getCategories().get(1).getChild().get(i).getName();
                    }
                }
            }else if (PickerOption.FASHION_TARGET_GROUP_SUB_CATEGORY==pickerOption){
                if (productModel.getCategories().size()>=2){
                    items = new CharSequence[productModel.getCategories().get(1).getChild().get(productModel.getTargetGroupPosition()).getChild().size()];
                    for (int i = 0; i < productModel.getCategories().get(1).getChild().get(productModel.getTargetGroupPosition()).getChild().size(); i++) {
                        items[i]=productModel.getCategories().get(1).getChild().get(productModel.getTargetGroupPosition()).getChild().get(i).getName();
                    }
                }
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    saveCategory(pickerOption,productModel,item);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
    }

    private void saveCategory(PickerOption pickerOption,ProductModel productModel, int position){
        if (pickerOption==PickerOption.FOOD_SUB_CATEGORY){
            productModel.setFoodSubCategory(true);
            productModel.setSubCategoryId(productModel.getCategories().get(0).getChild().get(position).getId());
            productModel.setSubCategoryName(productModel.getCategories().get(0).getChild().get(position).getName());
            productModel.setSubCategoryPosition(position);
        }else if (pickerOption==PickerOption.FASHION_TARGET_GROUP){
            productModel.setTargetGroup(true);
            productModel.setTargetGroupId(productModel.getCategories().get(1).getChild().get(position).getId());
            productModel.setTargetGroupName(productModel.getCategories().get(1).getChild().get(position).getName());
            productModel.setTargetGroupPosition(position);
        }else if(pickerOption==PickerOption.FASHION_TARGET_GROUP_SUB_CATEGORY){
            productModel.setTargetGroupSubCategory(true);
            productModel.setTargetGroupSubCategoryId(productModel.getCategories().get(1).getChild().get(productModel.getTargetGroupPosition()).getChild().get(position).getId());
            productModel.setTargetGroupSubCategoryName(productModel.getCategories().get(1).getChild().get(productModel.getTargetGroupPosition()).getChild().get(position).getName());
            productModel.setTargetGroupSubCategoryPosition(position);
        }

    }


    private void categoryAPICall(){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<CategoryResposnse>> observable = api.category();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            productModel.setCategories(responses.body().getCategories());
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


    enum PickerOption{
        FOOD_SUB_CATEGORY,FASHION_TARGET_GROUP,FASHION_TARGET_GROUP_SUB_CATEGORY
    }


}
