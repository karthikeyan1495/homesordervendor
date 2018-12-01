package com.homesordervendor.user.shippingarea.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.user.shippingarea.model.Area;
import com.homesordervendor.user.shippingarea.model.State;

/**
 * Created by mac on 2/27/18.
 */

public class AreaItemVM extends java.util.Observable{

    Activity activity;

    public AreaItemVM(Activity activity){
        this.activity=activity;
    }

    public void onClickCheckBox(View view, Area area,State state){
        if (!area.isSelected()){
            area.setPrice("");
        }
        enablePrice(state);

        setChanged();
        notifyObservers();

    }

    private void enablePrice(State state){
        boolean enable=false;
        for (Area area:state.getAreas()){
            if (area.isSelected()){
                enable=true;
                break;
            }
        }
        state.setSelected(enable);
    }
}
