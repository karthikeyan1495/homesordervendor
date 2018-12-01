package com.homesordervendor.initialscreen.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.homesordervendor.R;
import com.homesordervendor.initialscreen.language.LanguageSelectionActivity;
import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.additionalinformation.AdditionalInformationActivity;
import com.homesordervendor.user.login.LoginActivity;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.StringUtil;
import com.homesordervendor.util.Util;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 500;
    TextView textView;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Util.getInstance().setLanguage();
        setContentView(R.layout.activity_splash);
        textView=(TextView)findViewById(R.id.vendor_app_view);
        slideToTop(textView);
    }
    private void waitingTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToNextScreen();
            }
        }, SPLASH_TIME_OUT);
    }

    public void slideToTop(View view){
        Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.slide_up_animation);
        bottomUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                waitingTime();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(bottomUp);
        view.setVisibility(View.VISIBLE);
    }

    private void moveToNextScreen() {
        if (MySession.getInstance(this).isLogin()) {
            if (StringUtil.accountComplitionStatus(MySession.getInstance(this).getUser())) {
                Intent i = new Intent(SplashActivity.this, NavigationActivity.class);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(SplashActivity.this, AdditionalInformationActivity.class);
                startActivity(i);
                finish();
            }
        } else {
            if (MySession.getInstance(this).isAppFirstTimeLoad()){
                startActivity(new Intent(SplashActivity.this, LanguageSelectionActivity.class));
                finish();
            }else {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}
