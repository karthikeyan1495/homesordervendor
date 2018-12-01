package com.homesordervendor.initialscreen.language;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityLanguageSelectionBinding;
import com.homesordervendor.initialscreen.language.viewmodel.LanguageSelectionVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    public ActivityLanguageSelectionBinding binding;
    LanguageSelectionVM languageSelectionVM;
    LanguageModel languageModel;

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
        binding= DataBindingUtil.setContentView(this,R.layout.activity_language_selection);
        languageSelectionVM=new LanguageSelectionVM(this);
        languageModel=new LanguageModel();
        binding.setLanguageSelectionVM(languageSelectionVM);
        binding.setLanguageModel(languageModel);
    }
}
