package com.homesordervendor;

import android.content.Context;

import com.homesordervendor.font.CustomFontFamily;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.Util;
import com.telr.mobile.sdk.TelrApplication;

/**
 * Created by mac on 2/19/18.
 */

public class MyApp extends TelrApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        MyApp.context = getApplicationContext();
        // this.context = this;

        Util.getInstance().setLanguage();
        InternetChecker.getInstance().init();
        CustomFontFamily.getInstance().addAllFont(this);
    }

    public static Context getContext() {
        return MyApp.context;
    }

}