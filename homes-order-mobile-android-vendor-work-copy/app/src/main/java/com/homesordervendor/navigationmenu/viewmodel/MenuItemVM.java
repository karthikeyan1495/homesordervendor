package com.homesordervendor.navigationmenu.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.navigationmenu.OnNavigationMenuListener;
import com.homesordervendor.navigationmenu.model.Menu;

/**
 * Created by mac on 3/1/18.
 */

public class MenuItemVM {
    Activity activity;

    OnNavigationMenuListener listener;
    public MenuItemVM(Activity activity,OnNavigationMenuListener listener){
        this.activity=activity;
        this.listener=listener;
    }

    public void onClickMenuItem(View view, Menu menu){
        if (listener!=null){
            listener.onClickNavigationItem(menu);
        }
    }

}
