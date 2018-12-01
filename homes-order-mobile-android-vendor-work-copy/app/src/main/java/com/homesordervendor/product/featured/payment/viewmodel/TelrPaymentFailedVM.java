package com.homesordervendor.product.featured.payment.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.product.featured.FeaturedActivity;


/**
 * Created by innoppl on 14/06/18.
 */

public class TelrPaymentFailedVM {
    Activity activity;

    public TelrPaymentFailedVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickBack(View view){
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, NavigationActivity.class));
    }

    public void onClickRetry(View view){
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, NavigationActivity.class));
       /* if (MySession.getInstance(activity).getCartAmount()!=null&&MySession.getInstance
                (activity).getDeliveryAddress()!=null) {
            PaymentUtil.telrPaymentGatewayInitialization(activity, MySession.getInstance(activity).getCartAmount(),
                    MySession.getInstance
                            (activity).getDeliveryAddress());
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
        }*/
    }
}
