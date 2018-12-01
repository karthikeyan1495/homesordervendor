package com.homesordervendor.user.subscribe;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivitySubscribeBinding;
import com.homesordervendor.user.subscribe.adapter.PlansAdapter;
import com.homesordervendor.user.subscribe.model.Plan;
import com.homesordervendor.user.subscribe.viewmodel.SubscribeVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class SubscribeActivity extends AppCompatActivity implements Observer {

    ActivitySubscribeBinding binding;
    SubscribeVM subscribeVM;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setUpObserver(subscribeVM);
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_subscribe);
        subscribeVM=new SubscribeVM(this);
        binding.setSubscribeVM(subscribeVM);
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private void setViewPager(List<Plan> plans){
        binding.subscribeViewPager.setAdapter(new PlansAdapter(this,plans));
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof  SubscribeVM) {
            setViewPager(subscribeVM.getPlans());
        }
    }
}
