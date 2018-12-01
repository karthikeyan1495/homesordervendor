package com.homesordervendor.user.staticcontent;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityStaticContentBinding;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.staticcontent.viewmodel.StaticContentVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;


public class StaticContentActivity extends AppCompatActivity {

    ActivityStaticContentBinding binding;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        loadWebview();
    }

    @Override
    public void onBackPressed() {
        Util.getInstance().setLanguage();
        super.onBackPressed();
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this, R.layout.activity_static_content);
        binding.setStaticContentVM(new StaticContentVM(this));
    }

    private void loadWebview(){
        binding.webView.getSettings().setJavaScriptEnabled(true);
        if (MySession.getInstance(this).getLanguageKey().equals(getString(R.string
                .ar))) {
            binding.webView.postUrl(getString(R.string.terms_and_condition_url_ar),null);
        }else{
            binding.webView.postUrl(getString(R.string.terms_and_condition_url_en),null);
        }
        binding.webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}
