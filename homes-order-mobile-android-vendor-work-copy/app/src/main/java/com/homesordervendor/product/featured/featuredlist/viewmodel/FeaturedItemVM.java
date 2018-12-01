package com.homesordervendor.product.featured.featuredlist.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.product.productlist.model.ProductItem;

/**
 * Created by mac on 3/5/18.
 */

public class FeaturedItemVM {

    Activity activity;

    public FeaturedItemVM(Activity activity){
        this.activity=activity;
    }

    public void onClickFeaturedItem(View view, ProductItem productItem){
       /* MySnackBar.getInstance().showPositiveSnackBar(activity, activity.getString(R.string
                .already_featured));*/

    }
}
