package com.homesordervendor.navigationmenu.viewmodel;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.login.LoginActivity;

/**
 * Created by mac on 3/1/18.
 */

public class NavigationVM {

    Activity activity;

    public NavigationVM(Activity activity){
        this.activity=activity;
    }

    public void onClickLogout(View view){
        logout();
    }

    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(activity.getString(R.string.logout_alert));
        alertDialogBuilder.setPositiveButton(activity.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        MySession.getInstance(activity).saveLoginStatus(false);
                        activity.finish();
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    }
                });

        alertDialogBuilder.setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
