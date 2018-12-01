package com.homesordervendor.user.login;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityLoginBinding;
import com.homesordervendor.user.login.model.Login;
import com.homesordervendor.user.login.viewmodel.LoginVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    public ActivityLoginBinding binding;
    LoginVM loginVM;
    Login login;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView();
    }

    private void bindingView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        login=new Login();
        loginVM=new LoginVM(this,binding,login);
        binding.setLoginVM(loginVM);
        binding.setLogin(login);
    }
}
