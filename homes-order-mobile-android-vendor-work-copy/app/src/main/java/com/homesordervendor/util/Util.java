package com.homesordervendor.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.homesordervendor.MyApp;
import com.homesordervendor.sharedpreferences.MySession;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mac on 2/1/18.
 */

public class Util {
    private static final Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }


    public Configuration setLanguage(){
        Locale locale = new Locale(MySession.getInstance(MyApp.getContext()).getLanguageKey());
        Resources resources = MyApp.getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        // 参考 https://developer.android.com/reference/android/content/res/Configuration.html
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
            resources.updateConfiguration(config, dm);
        }
        */

        config.locale = locale;
        resources.updateConfiguration(config, dm);
        return config;
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Configuration configuration = activity.getBaseContext().getResources().getConfiguration();
            LocaleList localeList = new LocaleList(locale);
            localeList.setDefault(localeList);
            configuration.setLocales(localeList);
            activity.getBaseContext().createConfigurationContext(configuration);
            return configuration;
        }else {
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
            return config;
       }*/
    }

    public static Locale getLanguageType(Context context) {
        /*Log.i("=======", "context = " + context);
//        Resources resources = context.getResources();
        Resources resources = MyApp.getContext().getResources();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return config.getLocales().get(0);
        } else {
            return config.locale;
        }*/

        return new Locale(MySession.getInstance(MyApp.getContext()).getLanguageKey());

    }

    public int getScreenWidth(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return  metrics.widthPixels;
    }

    public void hideKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void openBrowser(Activity activity,String url){
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);

    }

    public boolean isTablet(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=7){
            return true;
        }else{
            return false;
        }
    }
    public float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
    public float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static int completePercentageCalculation(int screenWidth,String from, String to) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date currentDate = Calendar.getInstance().getTime();
            Date fromDate = dateFormat.parse(from);
            Date toDate = dateFormat.parse(to);
            long diffInMillisec = currentDate.getTime() - fromDate.getTime();
            if (diffInMillisec < 0) {
                return screenWidth;
            } else {
                diffInMillisec = toDate.getTime() - currentDate.getTime();
                if (diffInMillisec < 0) {
                    return 0;
                } else {
                    long fromandtoms = fromDate.getTime() - toDate.getTime();
                    long toandcrms = toDate.getTime() - currentDate.getTime();

                    long fromandtodays = TimeUnit.MILLISECONDS.toDays(fromandtoms);
                    long toandcrmsdays = TimeUnit.MILLISECONDS.toDays(toandcrms);

                    float percentage =fromandtodays - toandcrmsdays;
                    percentage=percentage/fromandtodays;
                    percentage=percentage*100;

                }

                /*else {
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                    if (diffInDays == 0) {
                        long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillisec);
                        if (diffInHours==0){
                            long diffInMin = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec);
                            if (diffInMin==0){
                                long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
                                if (diffInSec==0){
                                    return 0;
                                }else {
                                    return String.format(MyApp.getContext().getResources().getString(R.string
                                            .expires_in_seconds), String.valueOf(diffInMin));
                                }
                            }else {
                                return String.format(MyApp.getContext().getResources().getString(R.string
                                        .expires_in_minutes), String.valueOf(diffInMin));
                            }
                        }else {
                            return String.format(MyApp.getContext().getResources().getString(R.string
                                    .expires_in_hours), String.valueOf(diffInHours));
                        }
                    } else {
                        return String.format(MyApp.getContext().getResources().getString(R.string
                                .expires_in_days), String.valueOf(diffInDays));
                    }
                }*/
            }
        } catch (Exception e) {
        }
        return screenWidth;
    }




    public boolean passwordValidator(String password)
    {
        final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=" +
                ".*?[#?!@$%^&*-_]).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
