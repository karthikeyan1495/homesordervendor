package com.homesordervendor.user.forgotpassword;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityForgotPasswordBinding;
import com.homesordervendor.user.forgotpassword.model.ForgotPassword;
import com.homesordervendor.user.forgotpassword.viewmodel.ForgotPasswordVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    ForgotPasswordVM forgotPasswordVM;
    ForgotPassword forgotPassword;

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

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forgot_password);
        forgotPassword=new ForgotPassword();
        forgotPasswordVM=new ForgotPasswordVM(this,binding,forgotPassword);
        binding.setForgotPasswordVM(forgotPasswordVM);
        binding.setForgotPassword(forgotPassword);
    }
}
