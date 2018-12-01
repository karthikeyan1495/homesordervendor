package com.homesordervendor.product.addproduct.viewmodel;

import android.app.Activity;

import com.homesordervendor.product.addproduct.ProductInformationFragment;

/**
 * Created by mac on 3/3/18.
 */

public class ShippingAreaBottomSheetVM {
    Activity activity;
    ProductInformationFragment productInformationFragment;
    public ShippingAreaBottomSheetVM(Activity activity,ProductInformationFragment productInformationFragment){
        this.activity=activity;
        this.productInformationFragment=productInformationFragment;
    }
}
