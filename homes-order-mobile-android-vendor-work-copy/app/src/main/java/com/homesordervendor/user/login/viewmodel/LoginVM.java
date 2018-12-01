package com.homesordervendor.user.login.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.databinding.ActivityLoginBinding;
import com.homesordervendor.initialscreen.language.LanguageSelectionActivity;
import com.homesordervendor.navigationmenu.NavigationActivity;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.additionalinformation.AdditionalInformationActivity;
import com.homesordervendor.user.forgotpassword.ForgotPasswordActivity;
import com.homesordervendor.user.login.model.Login;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.user.register.RegisterActivity;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.StringUtil;
import com.homesordervendor.util.Util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/19/18.
 */

public class LoginVM {

    Activity activity;
    MyProgressDialog myProgressDialog;
    Login login;
    ActivityLoginBinding binding;

    public LoginVM(@NonNull Activity activity, ActivityLoginBinding binding,Login login) {
        this.activity = activity;
        myProgressDialog = new MyProgressDialog();
        this.binding=binding;
        this.login = login;
    }

    public void onClickRootView(View view){
        Util.getInstance().hideKeyboard(activity);
    }

    public void onClickLogin(View view) {
        Util.getInstance().hideKeyboard(activity);
        removeErrorMessage();
        validation();
    }

    public void onClickSignup(View view){
        activity.startActivity(new Intent(activity, RegisterActivity.class));
    }

    public void onClickForgotPassword(View view){
        activity.startActivity(new Intent(activity, ForgotPasswordActivity.class));
    }

    public void onClickChangeLanguage(View view){
        activity.startActivity(new Intent(activity, LanguageSelectionActivity.class));
        activity.finish();

    }

    public void onEmailTextChanged(CharSequence s, int start, int before, int count, Login login) {
        if (s.toString().trim().length()!=0){
            binding.signInUsernameLayout.setError(null);
        }
    }

    public void onPasswordTextChanged(CharSequence s, int start, int before, int count, Login
            login) {
        if (s.toString().trim().length()!=0){
            binding.signInPasswordLayout.setError(null);
        }
    }

    private void validation() {
        if (login.getUsername().trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(login.getUsername().trim()).matches()) {
            binding.signInUsernameLayout.setError(activity.getString(R.string.valid_email_address_error));
        } else if (login.getPassword().length()==0) {
            binding.signInPasswordLayout.setError(activity.getString(R.string.please_enter_valid_password));
        } else {
            loginAPICall();
        }
    }

    private void removeErrorMessage() {
        binding.signInUsernameLayout.setError(null);
        binding.signInPasswordLayout.setError(null);
    }

    private void loginAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            login.setDevice_type("0");// 0 Means Android
            login.setDevice_token(FirebaseInstanceId.getInstance().getToken());
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<User>> observable = api.login(login);
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

    private void navigateToNextScreen(User user){
        if (StringUtil.accountComplitionStatus(user)){
            activity.finish();
            activity.startActivity(new Intent(activity, NavigationActivity.class));
        }else {
            activity.finish();
            activity.startActivity(new Intent(activity, AdditionalInformationActivity.class));
        }
    }

}
