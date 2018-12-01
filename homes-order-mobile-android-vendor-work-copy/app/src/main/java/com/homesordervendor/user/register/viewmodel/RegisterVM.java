package com.homesordervendor.user.register.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.databinding.ActivityRegisterBinding;
import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.accountinformation.model.CitiesResponse;
import com.homesordervendor.user.accountinformation.model.CoveredArea;
import com.homesordervendor.user.additionalinformation.AdditionalInformationActivity;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.user.register.model.Register;
import com.homesordervendor.user.register.model.SelectedCountry;
import com.homesordervendor.user.staticcontent.StaticContentActivity;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.StringUtil;
import com.homesordervendor.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/21/18.
 */

public class RegisterVM {

    List<CoveredArea> coveredAreas=new ArrayList<>();
    Activity activity;
    ActivityRegisterBinding binding;
    Register register;
    MyProgressDialog myProgressDialog;

    public RegisterVM(Activity activity,ActivityRegisterBinding binding,Register register){
        this.activity=activity;
        this.binding=binding;
        this.register=register;
        myProgressDialog=new MyProgressDialog();
        countryAPICall();
    }

    public void onClickRootView(View view){
        Util.getInstance().hideKeyboard(activity);
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickRegister(View view,SelectedCountry selectedCountry){
        Util.getInstance().hideKeyboard(activity);
        removeErrorMessage();
        validation(selectedCountry);

    }
    public void onClickCountry(View view,SelectedCountry selectedCountry){
        countryPicker(selectedCountry);
    }

    public void onClickTermsAndCondition(View view){
       activity.startActivity(new Intent(activity,StaticContentActivity.class));
    }

    public void onFullNameTextChanged(CharSequence s, int start, int before, int count, Register register) {
        if (s.toString().trim().length()!=0){
            binding.fullNameLayout.setError(null);
        }
    }

    public void onEmailTextChanged(CharSequence s, int start, int before, int count, Register register) {
        if (s.toString().trim().length()!=0){
            binding.emailLayout.setError(null);
        }
    }

    public void onPasswordTextChanged(CharSequence s, int start, int before, int count, Register register) {
        if (s.toString().trim().length()!=0){
            binding.passwordLayout.setError(null);
        }
    }

    public void onConfirmPasswordTextChanged(CharSequence s, int start, int before, int count, Register register) {
        if (s.toString().trim().length()!=0){
            binding.retypePasswordLayout.setError(null);
        }
    }

    public void onPhoneNumberTextChanged(CharSequence s, int start, int before, int count, Register register) {
        if (s.toString().trim().length()!=0){
            binding.phoneLayout.setError(null);
        }
    }

    public void onShopNameTextChanged(CharSequence s, int start, int before, int count, Register register) {
        if (s.toString().trim().length()!=0){
            binding.shopNameLayout.setError(null);
        }
    }

    private void validation(SelectedCountry selectedCountry){
        if (register.getFullname().trim().length() <4 ) {
            binding.fullNameLayout.setError(activity.getString(R.string.valid_full_name));
        } else if (register.getEmail().trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(register.getEmail().trim()).matches()) {
            binding.emailLayout.setError(activity.getString(R.string.valid_email_address_error));
        } else if(! Util.getInstance().passwordValidator(register.getPassword())) {
            binding.passwordLayout.setError(activity.getString(R.string.invalid_password_error));
        } else if(!register.getPassword().equals(register.getConfirmpassword())){
            binding.retypePasswordLayout.setError(activity.getString(R.string.passwords_match_error));
        } else if(register.getPhone().trim().length()==0){
            binding.phoneLayout.setError(activity.getString(R.string.valid_phone_number));
        } else if(register.getShopname().trim().length()==0){
            binding.shopNameLayout.setError(activity.getString(R.string.valid_shop_name));
        } else if(selectedCountry.getCountryCode().trim().length()==0) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .error_select_country));
        }else if(!binding.termsAndConditionCheckbox.isChecked()){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.terms_and_condition_accept_error));
        }
        else {
            registerAPICall(selectedCountry);
        }
    }

    private void removeErrorMessage() {
        binding.fullNameLayout.setError(null);
        binding.emailLayout.setError(null);
        binding.passwordLayout.setError(null);
        binding.retypePasswordLayout.setError(null);
        binding.shopNameLayout.setError(null);
        binding.phoneLayout.setError(null);
    }

    private void registerAPICall(SelectedCountry selectedCountry) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            register.setCountry(selectedCountry.getCountryCode());
            register.setDevice_type("0");// 0 Means Android
            register.setDevice_token(FirebaseInstanceId.getInstance().getToken());
            Observable<Response<User>> observable = api.register(register);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            MySession.getInstance(activity).saveLoginStatus(true);
                            MySession.getInstance(activity).saveUser(responses.body());
                            if (MySession.getInstance(activity).getUser().getProfile().getCountry
                                    ().equals("AE")){
                                MySession.getInstance(activity).saveCurrency(activity.getString(R
                                        .string.aed));
                            }else{
                                MySession.getInstance(activity).saveCurrency(activity.getString(R
                                        .string.sar));
                            }
                            navigateToNextScreen(responses.body());
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
                        }
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));

                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }

    private void countryAPICall(){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<CitiesResponse>> observable = api.cities();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            coveredAreas=responses.body().getCoveredarea();
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
                        }
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));

                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }
    private void navigateToNextScreen(User user){
        if (StringUtil.accountComplitionStatus(user)){
            activity.finish();
            activity.startActivity(new Intent(activity, NavigationActivity.class));
        }else {
            activity.finish();
            activity.startActivity(new Intent(activity, AdditionalInformationActivity.class));
        }
    }

    private void countryPicker(SelectedCountry selectedCountry) {
        if (coveredAreas != null&&coveredAreas.size()!=0) {
            CharSequence[] items =new CharSequence[coveredAreas.size()];
            for (int i = 0; i < coveredAreas.size(); i++) {
                items[i] = StringUtil.getLanguageName(coveredAreas.get(i).getCountryNameEN(), coveredAreas.get(i).getCountryNameAR());
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    selectedCountry.setCountryNameAR(coveredAreas.get(item).getCountryNameAR());
                    selectedCountry.setCountryNameEN(coveredAreas.get(item).getCountryNameEN());
                    selectedCountry.setCountryId(String.valueOf(coveredAreas.get(item)
                            .getCountryID()));
                    selectedCountry.setCountryCode(coveredAreas.get(item).getCountryCode());
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else{
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
        }
    }
}
