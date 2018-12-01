package com.homesordervendor.product.addproduct.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.product.addproduct.ProductImageFragment;
import com.homesordervendor.product.addproduct.model.ImageURI;

/**
 * Created by mac on 3/3/18.
 */

public class ProductImageVM {


    Activity activity;
    ProductImageFragment productImageFragment;

    public ProductImageVM(Activity activity,ProductImageFragment productImageFragment) {
        this.activity = activity;
        this.productImageFragment=productImageFragment;
    }

    public void onClickFrontImage(View view) {
        openSingleImageChooser();
    }

    public void onClickFrontImageDelete(View view, ImageURI imageURI) {
        imageURI.setImage(false);
        imageURI.setUri(null);
        productImageFragment.binding.setImageURI(imageURI);
    }



    public void openSingleImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        productImageFragment.startActivityForResult(Intent.createChooser(intent, activity.getString(R.string.select_pictures)), productImageFragment.SELECT_SINGLE_PICTURE);
    }

}
