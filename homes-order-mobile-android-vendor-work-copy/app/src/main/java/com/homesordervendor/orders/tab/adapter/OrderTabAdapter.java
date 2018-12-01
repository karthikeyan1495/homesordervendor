package com.homesordervendor.orders.tab.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.homesordervendor.R;
import com.homesordervendor.orders.cancelled.OrderCancelledFragment;
import com.homesordervendor.orders.delivery.OrderDeliveryFragment;
import com.homesordervendor.orders.dispatch.OrderDispatchFragment;
import com.homesordervendor.orders.newlist.NewListFragment;
import com.homesordervendor.orders.processinglist.ProcessingFragment;

/**
 * Created by mac on 3/6/18.
 */

public class OrderTabAdapter extends FragmentPagerAdapter {

    Activity activity;
    public OrderTabAdapter(Activity activity,FragmentManager manager) {
        super(manager);
        this.activity=activity;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position==0){
            fragment=new NewListFragment();
        }else if(position==1) {
            fragment=new ProcessingFragment();
        }else if(position==2){
            fragment=new OrderDispatchFragment();
        }else if(position==3){
            fragment=new OrderDeliveryFragment();
        }else {
            fragment=new OrderCancelledFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        if (position==0){
            title=activity.getString(R.string.new_tab);
        }else if (position==1){
            title=activity.getString(R.string.processing_tab);
        }else if (position==2){
            title=activity.getString(R.string.dispatched_tab);
        }else if (position==3){
            title=activity.getString(R.string.delivery_tab);
        }else{
            title=activity.getString(R.string.cancelled_tab);
        }
        return title;
    }
}