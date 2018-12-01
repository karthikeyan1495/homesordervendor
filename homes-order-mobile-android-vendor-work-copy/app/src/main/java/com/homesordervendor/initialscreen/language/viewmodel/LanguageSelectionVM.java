package com.homesordervendor.initialscreen.language.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.initialscreen.language.LanguageModel;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.login.LoginActivity;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.Util;

/**
 * Created by innoppl on 13/03/18.
 */

public class LanguageSelectionVM {

    Activity activity;

    public LanguageSelectionVM(Activity activity){
        this.activity=activity;
    }

    public void onClickLanguageSelection(View view, LanguageModel languageModel){
        languagePicker(languageModel);
    }

    public void onClickSubmit(View view,LanguageModel languageModel){
        if (languageModel.getKey()==null||languageModel.getKey().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .please_choose_language));
        }else {
            MySession.getInstance(activity).saveLanguageKey(String.valueOf(languageModel.getKey()));
            Util.getInstance().setLanguage();
            MySession.getInstance(activity).saveAppFirstTimeLoad(false);
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
        }
    }

    private void languagePicker(LanguageModel languageModel) {
        final CharSequence[] languageName = activity.getResources().getStringArray(R.array.language_array);
        final CharSequence[] languageKey = activity.getResources().getStringArray(R.array.language_key_array);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.MyDialogTheme);
        builder.setItems(languageName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                languageModel.setName(String.valueOf(languageName[item]));
                languageModel.setKey(String.valueOf(languageKey[item]));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
