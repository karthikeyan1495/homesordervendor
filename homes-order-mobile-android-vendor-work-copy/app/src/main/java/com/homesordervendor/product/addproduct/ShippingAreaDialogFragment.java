package com.homesordervendor.product.addproduct;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ShippingAreaDialogBinding;
import com.homesordervendor.product.addproduct.viewmodel.ShippingAreaDialogVM;
import com.homesordervendor.user.shippingarea.adapter.StateAdapter;
import com.homesordervendor.user.shippingarea.model.Country;
import com.homesordervendor.user.shippingarea.model.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mac on 3/3/18.
 */

public class ShippingAreaDialogFragment extends DialogFragment implements Observer {

    ShippingAreaDialogBinding binding;
    ShippingAreaDialogVM shippingAreaDialogVM;
    DialogCloseListener onCloseListener;

    //List<State> states=new ArrayList<>();
    List<Country> countries=new ArrayList<>();


    public ShippingAreaDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        bindView(inflater, container);
        setUpObserver(shippingAreaDialogVM);
        return binding.getRoot();
    }
    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof ShippingAreaDialogVM) {
            int position=(int)object;
            binding.setCountry(countries.get(position));
            setupRecyclerView(countries.get(position).getStates());
        }
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }


    public void setOnCloseListener(DialogCloseListener onCloseListener){
        this.onCloseListener=onCloseListener;
    }

    /*public void setStates(List<State>states){
        this.states=states;
    }*/

    public void setCountries(List<Country> countries){
        this.countries=countries;
    }

    private void setupRecyclerView(List<State> states){
        StateAdapter adapter=new StateAdapter(getActivity(),states,false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
    }
    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.shipping_area_dialog, container, false);
        shippingAreaDialogVM = new ShippingAreaDialogVM(getActivity(),onCloseListener,
                this,countries);
        binding.setShippingAreaDialogVM(shippingAreaDialogVM);

        Country country=getSelectedCountry();
        if (country!=null) {
            binding.setCountry(country);
            setupRecyclerView(country.getStates());
        }
    }

    private Country getSelectedCountry(){
        for (Country country:countries){
            if (country.isSelected()){
                return country;
            }
        }
        return null;
    }


    public interface DialogCloseListener{
        void onClosed(List<Country> countries);
    }

}
