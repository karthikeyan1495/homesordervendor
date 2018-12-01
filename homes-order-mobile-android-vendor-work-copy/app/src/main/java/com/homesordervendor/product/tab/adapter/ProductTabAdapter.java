package com.homesordervendor.product.tab.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.homesordervendor.R;
import com.homesordervendor.product.featured.featuredlist.FeaturedListFragment;
import com.homesordervendor.product.productlist.ProductListFragment;
import com.homesordervendor.product.promotion.promotionlist.PromotionListFragment;

/**
 * Created by mac on 3/2/18.
 */

public class ProductTabAdapter extends FragmentPagerAdapter {

    Activity activity;
    public ProductTabAdapter(Activity activity,FragmentManager manager) {
        super(manager);
        this.activity=activity;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position==0){
            fragment=new ProductListFragment();
        }else if(position==1) {
            fragment=new FeaturedListFragment();
        }else{
            fragment=new PromotionListFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        if (position==0){
            title=activity.getString(R.string.products);
        }else if (position==1){
            title=activity.getString(R.string.featured);
        }else {
            title=activity.getString(R.string.promotion);
        }
        return title;
    }
}