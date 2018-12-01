package com.homesordervendor.user.subscribe.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.homesordervendor.R;
import com.homesordervendor.databinding.PlansItemBinding;
import com.homesordervendor.user.subscribe.model.Plan;
import com.homesordervendor.user.subscribe.viewmodel.PlanItemVM;

import java.util.List;

/**
 * Created by mac on 2/22/18.
 */

public class PlansAdapter extends PagerAdapter {

    private Activity activity;
    List<Plan> list;
    private ProgressDialog progressDialog;

    public PlansAdapter(Activity activity,List<Plan> subscriptions) {
        this.activity = activity;
        this.list=subscriptions;
    }


    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        PlansItemBinding binding= DataBindingUtil.inflate(inflater, R.layout.plans_item, collection, false);
        binding.setPlanItemVM(new PlanItemVM(activity));
        binding.setPlan(list.get(position));
        collection.addView(binding.getRoot());
        return binding.getRoot();
    }
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
