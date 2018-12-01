package com.homesordervendor.user.accountinformation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityAccountInformationBinding;
import com.homesordervendor.user.accountinformation.model.AccountInfo;
import com.homesordervendor.user.accountinformation.model.CoveredArea;
import com.homesordervendor.user.accountinformation.viewmodel.AccountInformationVM;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class AccountInformationActivity extends AppCompatActivity implements Observer {

    ActivityAccountInformationBinding binding;
    AccountInformationVM accountInformationVM;
    User user=new User();
    List<CoveredArea> coveredAreas=new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setUpObserver(accountInformationVM);
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }
    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_account_information);
        accountInformationVM=new AccountInformationVM(this);
        binding.setAccountInformationVM(accountInformationVM);
        binding.setAccountInfo(new AccountInfo());
    }
    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof AccountInformationVM) {
            user=accountInformationVM.getUser();
            coveredAreas=accountInformationVM.getCountry();
            setUserData();
        }
    }

    private void setUserData(){
        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setDob(user.getProfile().getAddress().getDob());
        accountInfo.setStreet(user.getProfile().getAddress().getStreet());
        accountInfo.setGender(user.getProfile().getAddress().getGender());
        accountInfo.setCity(user.getProfile().getAddress().getCity());
        if (user.getProfile().getAddress().getGender()!=null && user.getProfile().getAddress().getGender().equals("1")){
            accountInfo.setMale(true);
        }else if (user.getProfile().getAddress().getGender()!=null && user.getProfile().getAddress().getGender().equals("2")){
            accountInfo.setFemale(true);
        }else if (user.getProfile().getAddress().getGender()!=null && user.getProfile().getAddress().getGender().equals("3")){
            accountInfo.setNotspecify(true);
        }

        for (int i=0;i<coveredAreas.size();i++){
            if (coveredAreas.get(i).getCountryCode().equals(user.getProfile().getCountry())){
                accountInfo.setCountryPosition(i);
                accountInfo.setCountryId(user.getProfile().getCountry());
                accountInfo.setCountryNameEN(coveredAreas.get(i).getCountryNameEN());
                accountInfo.setCountryNameAR(coveredAreas.get(i).getCountryNameAR());
                break;
            }
        }
        binding.setAccountInfo(accountInfo);
    }
}
