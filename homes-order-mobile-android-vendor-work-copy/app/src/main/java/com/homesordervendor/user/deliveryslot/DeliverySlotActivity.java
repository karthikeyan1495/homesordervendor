package com.homesordervendor.user.deliveryslot;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityDeliverySlotBinding;
import com.homesordervendor.user.deliveryslot.adapter.DeliverySlotAdapter;
import com.homesordervendor.user.deliveryslot.model.Slots;
import com.homesordervendor.user.deliveryslot.viewmodel.DeliverySlotVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class DeliverySlotActivity extends AppCompatActivity implements Observer {

    ActivityDeliverySlotBinding binding;
    DeliverySlotVM deliverySlotVM;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setUpObserver(deliverySlotVM);
    }
    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof DeliverySlotVM) {
            setupRecyclerView(deliverySlotVM.getDeliverySlotList());
            showSaveAction();
        }
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }
    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_delivery_slot);
        deliverySlotVM=new DeliverySlotVM(this);
        binding.setDeliverySlotVM(deliverySlotVM);
    }
    private void setupRecyclerView(List<Slots> slots){
        DeliverySlotAdapter adapter=new DeliverySlotAdapter(this,slots);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnDeliverySlotListener(() -> {
            showSaveAction();
        });
    }

    private void showSaveAction(){
        if (deliverySlotVM.validation()){
            binding.saveBtn.setVisibility(View.VISIBLE);
        }else {
            binding.saveBtn.setVisibility(View.INVISIBLE);
        }
    }

}
