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

public class ImageAddItemVM {
    Activity activity;
    ProductImageFragment productImageFragment;
    public  ImageAddItemVM(Activity activity,ProductImageFragment productImageFragment){
        this.activity=activity;
        this.productImageFragment=productImageFragment;
    }

    public void onClickImageAdd(View view, ImageURI imageURI){
        if (imageURI.getType()==2){
            openMultipleImageChooser();
        }
    }
    public void onClickImageRemove(View view, ImageURI imageURI){
        productImageFragment.list.remove(imageURI);
        productImageFragment.adapter.notifyDataSetChanged();
    }

    public void openMultipleImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        productImageFragment.startActivityForResult(Intent.createChooser(intent, activity.getString(R.string.select_pictures)), productImageFragment.SELECT_MULTIPLE_PICTURE);
    }
}
