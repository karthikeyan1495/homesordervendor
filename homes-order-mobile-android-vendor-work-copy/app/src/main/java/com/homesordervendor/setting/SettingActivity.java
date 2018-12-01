package com.homesordervendor.setting;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivitySettingBinding;
import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.setting.viewmodel.SettingsVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    SettingsVM settingsVM;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView(false);
    }

    private void bindView(boolean isLanguageChanged){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_setting);
        settingsVM=new SettingsVM(this,isLanguageChanged);
        binding.setSettingsVM(settingsVM);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        bindView(true);
    }

    @Override
    public void onBackPressed() {
        if (settingsVM.isLanguageChanged) {
            finishAffinity();
            startActivity(new Intent(this, NavigationActivity.class));
        }else {
            finish();
        }
        super.onBackPressed();

    }

}
