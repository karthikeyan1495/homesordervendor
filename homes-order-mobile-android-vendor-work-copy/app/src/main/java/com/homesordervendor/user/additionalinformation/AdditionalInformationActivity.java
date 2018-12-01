package com.homesordervendor.user.additionalinformation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityAdditionalInformationBinding;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.additionalinformation.viewmodel.AdditionalInformationVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;

public class AdditionalInformationActivity extends AppCompatActivity  {

    ActivityAdditionalInformationBinding binding;
    AdditionalInformationVM additionalInformationVM;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.setUser(MySession.getInstance(this).getUser());
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_additional_information);
        additionalInformationVM=new AdditionalInformationVM(this);
        binding.setAdditionalInformationVM(additionalInformationVM);
    }

}
