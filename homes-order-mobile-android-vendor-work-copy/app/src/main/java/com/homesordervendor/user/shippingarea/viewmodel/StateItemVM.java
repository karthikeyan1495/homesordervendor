package com.homesordervendor.user.shippingarea.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.user.shippingarea.model.State;

/**
 * Created by mac on 2/23/18.
 */

public class StateItemVM {

    Activity activity;

    public StateItemVM(Activity activity){
        this.activity=activity;
    }

    public void onClickExpended(View view, State state){
        if (state.isExpended()){
            state.setExpended(false);
        }else {
            state.setExpended(true);
        }
    }

}
