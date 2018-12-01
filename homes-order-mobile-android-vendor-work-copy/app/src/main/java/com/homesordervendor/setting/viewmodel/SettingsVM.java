package com.homesordervendor.setting.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.util.Util;


/**
 * Created by mac on 1/19/18.
 */

public class SettingsVM {

    Activity activity;

    public ObservableField<String> language = new ObservableField<>("");
    public ObservableField<String> currency = new ObservableField<>("");


    public boolean isLanguageChanged = false;

    public SettingsVM(@NonNull Activity activity, boolean isLanguageChanged) {
        this.activity = activity;
        this.isLanguageChanged = isLanguageChanged;
        setLanguageName();
        currency.set(MySession.getInstance(activity).getCurrency());
    }

    private void setLanguageName() {
        if (MySession.getInstance(activity).getLanguageKey().equals(activity.getString(R.string.ar))) {
            language.set(activity.getString(R.string.arabic));
        } else {
            language.set(activity.getString(R.string.english));
        }
    }


    public void onClickBack(View view) {
        if (isLanguageChanged) {
            activity.finishAffinity();
            activity.startActivity(new Intent(activity, NavigationActivity.class));

        } else {
            activity.finish();
        }
    }

    public void onClickLanguage(View view) {
        languagePicker();
    }

    public void onClickCurrency(View view) {
        currencyPicker();
    }

    private void currencyPicker() {
        final CharSequence[] currencyName = activity.getResources().getStringArray(R.array
                .currency_array);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(currencyName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                MySession.getInstance(activity).saveCurrency(String.valueOf(currencyName[item]));
                currency.set(String.valueOf(currencyName[item]));

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void languagePicker() {
        final CharSequence[] languageName = activity.getResources().getStringArray(R.array.language_array);
        final CharSequence[] languageKey = activity.getResources().getStringArray(R.array.language_key_array);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(languageName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                if (!language.get().equals(String.valueOf(languageName[item]))) {
                    MySession.getInstance(activity).saveLanguageKey(String.valueOf(languageKey[item]));
                    activity.onConfigurationChanged(Util.getInstance().setLanguage());
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
