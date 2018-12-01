package com.homesordervendor.user.additionalinformation.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.user.accountinformation.AccountInformationActivity;
import com.homesordervendor.user.deliveryslot.DeliverySlotActivity;
import com.homesordervendor.user.login.LoginActivity;
import com.homesordervendor.user.shippingarea.ShippingAreaActivity;
import com.homesordervendor.user.subscribe.SubscribeActivity;

/**
 * Created by mac on 2/21/18.
 */

public class AdditionalInformationVM {

    Activity activity;
    public AdditionalInformationVM(Activity activity){
        this.activity=activity;
    }

    public void onClickAccountInformation(View view){
        activity.startActivity(new Intent(activity, AccountInformationActivity.class));

    }

    public void onClickDeliverySlot(View view){
        activity.startActivity(new Intent(activity, DeliverySlotActivity.class));
    }

    public void onClickAShippingMethod(View view){
        activity.startActivity(new Intent(activity, ShippingAreaActivity.class));
    }

    public void onClickPlan(View view){
        activity.startActivity(new Intent(activity, SubscribeActivity.class));
    }

    public void onClickGetStarted(View view){
        activity.finish();
        activity.startActivity(new Intent(activity, NavigationActivity.class));
    }
    public void onClickBack(View view){
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }
}
