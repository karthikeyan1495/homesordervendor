package com.homesordervendor.product.addproduct.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.product.addproduct.ShippingAreaDialogFragment;
import com.homesordervendor.user.shippingarea.model.Country;
import com.homesordervendor.util.StringUtil;

import java.util.List;

/**
 * Created by mac on 3/3/18.
 */

public class ShippingAreaDialogVM extends java.util.Observable{

    Activity activity;
    ShippingAreaDialogFragment.DialogCloseListener onCloseListener;
   // List<State> states;
    ShippingAreaDialogFragment shippingAreaDialogFragment;

    List<Country> countries;

    public ShippingAreaDialogVM(Activity activity,ShippingAreaDialogFragment.DialogCloseListener
            onCloseListener,ShippingAreaDialogFragment shippingAreaDialogFragment,List<Country> countries){
        this.activity=activity;
        this.onCloseListener=onCloseListener;
        //this.states=states;
        this.countries=countries;
        this.shippingAreaDialogFragment=shippingAreaDialogFragment;
    }

    public void onClickClose(View view){
        if (onCloseListener!=null){
            onCloseListener.onClosed(countries);
            shippingAreaDialogFragment.dismiss();
        }
    }

    public void onClickCountry(View view, Country country){
        countryPicker();
    }

    private void countryPicker() {
        if (countries != null&&countries.size()!=0) {
            CharSequence[] items = new CharSequence[countries.size()];
            for (int i = 0; i < countries.size(); i++) {
                items[i] = StringUtil.getLanguageName(countries.get(i).getCountryNameEN(), countries.get(i).getCountryNameAR());
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    setChanged();
                    notifyObservers(position);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
