package com.homesordervendor.user.deliveryslot.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.user.deliveryslot.model.Slots;

/**
 * Created by mac on 2/22/18.
 */

public class DeliverySlotItemVM extends java.util.Observable{

    Activity activity;

    public DeliverySlotItemVM(Activity activity) {
        this.activity = activity;
    }

    public void onItemSelected(View view, Slots slots){
        setChanged();
        notifyObservers();
    }
}
