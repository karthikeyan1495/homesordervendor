package com.homesordervendor.product.featured;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityFeaturedBinding;
import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.product.featured.model.BlockedDates;
import com.homesordervendor.product.featured.model.FeaturedRequest;
import com.homesordervendor.product.featured.viewmodel.FeaturedVM;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;

public class FeaturedActivity extends AppCompatActivity {


    ActivityFeaturedBinding binding;
    FeaturedVM featuredVM;

    ProductItem productItem=new ProductItem();
    FeaturedRequest featuredRequest=new FeaturedRequest();

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        bindView();
    }


    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_featured);
        featuredVM=new FeaturedVM(this,productItem,binding);
        binding.setFeaturedVM(featuredVM);
        binding.setProductItem(productItem);

        featuredRequest.setProductID(productItem.getProductID());
        featuredRequest.setSubcategoryID(productItem.getSubCategoryID().get(0));
        binding.setFeaturedRequest(featuredRequest);
        binding.setBlockedDates(new BlockedDates());
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            productItem=(ProductItem) bundle.getSerializable("productItem");
        }
    }
}
