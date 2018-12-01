package com.homesordervendor.user.shippingarea;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityShippingAreaBinding;
import com.homesordervendor.user.shippingarea.adapter.StateAdapter;
import com.homesordervendor.user.shippingarea.model.Country;
import com.homesordervendor.user.shippingarea.model.State;
import com.homesordervendor.user.shippingarea.viewmodel.ShippingAreaVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class ShippingAreaActivity extends AppCompatActivity implements Observer, ShippingAreaVM.SearchFilter {

    ActivityShippingAreaBinding binding;
    ShippingAreaVM shippingAreaVM;
    Country country;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setUpObserver(shippingAreaVM);
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof ShippingAreaVM) {
            setCounty(shippingAreaVM.getCountryList(),shippingAreaVM.getCountryPosition());
        }
    }

    @Override
    public void onSearchFilter(List<State> states) {
        setupRecyclerView(states);
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_shipping_area);
        shippingAreaVM=new ShippingAreaVM(this,this);
        binding.setShippingAreaVM(shippingAreaVM);
    }

    private void setupRecyclerView(List<State> states){
        StateAdapter adapter=new StateAdapter(this,states,true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnAreaListener(() -> {
            showSaveAction();
        });
    }

    private void setCounty(List<Country> countries,int position){
        if (position==-1) {
            for (int i = 0; i < countries.size(); i++) {
                if (countries.get(i).isSelected()) {
                    country = countries.get(i);
                    binding.setCountry(countries.get(i));
                    setupRecyclerView(shippingAreaVM.getCountryList().get(i).getStates());
                    break;
                } else {
                    country = countries.get(0);
                    binding.setCountry(countries.get(0));
                    setupRecyclerView(shippingAreaVM.getCountryList().get(0).getStates());
                }
            }
        }else {
            country = countries.get(position);
            binding.setCountry(countries.get(position));
            setupRecyclerView(shippingAreaVM.getCountryList().get(position).getStates());
        }
        country.setSelected(true);
        showSaveAction();
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private void showSaveAction(){
        if (shippingAreaVM.isEmptySelection(country)){
            binding.saveBtn.setVisibility(View.INVISIBLE);
        }else {
            binding.saveBtn.setVisibility(View.VISIBLE);
        }
    }


}
