package com.homesordervendor.user.register;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityRegisterBinding;
import com.homesordervendor.user.register.model.Register;
import com.homesordervendor.user.register.model.SelectedCountry;
import com.homesordervendor.user.register.viewmodel.RegisterVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;


public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    Register register;
    RegisterVM registerVM;

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
        binding= DataBindingUtil.setContentView(this,R.layout.activity_register);
        register=new Register();
        registerVM=new RegisterVM(this,binding,register);
        binding.setRegisterVM(registerVM);
        binding.setRegister(register);
        binding.setSelectedCountry(new SelectedCountry());
    }

}
