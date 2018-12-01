package com.homesordervendor.user.forgotpassword.viewmodel;

import android.app.Activity;
import android.util.Patterns;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.databinding.ActivityForgotPasswordBinding;
import com.homesordervendor.user.forgotpassword.model.ForgotPassword;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.Util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/21/18.
 */

public class ForgotPasswordVM {

    Activity activity;
    ForgotPassword forgotPassword;
    ActivityForgotPasswordBinding binding;
    MyProgressDialog myProgressDialog;

    public ForgotPasswordVM(Activity activity,ActivityForgotPasswordBinding binding,ForgotPassword forgotPassword){
        this.activity=activity;
        this.binding=binding;
        this.forgotPassword=forgotPassword;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickRootView(View view){
        Util.getInstance().hideKeyboard(activity);
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickReset(View view){
        Util.getInstance().hideKeyboard(activity);
        removeErrorMessage();
        validation();
    }
    public void onEmailTextChanged(CharSequence s, int start, int before, int count, ForgotPassword
            forgotPassword) {
        if (s.toString().trim().length()!=0){
            binding.emailLayout.setError(null);
        }
    }
    private void removeErrorMessage() {
        binding.emailLayout.setError(null);
    }

    private void validation() {
        if (forgotPassword.getEmail().trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(forgotPassword.getEmail().trim()).matches()) {
            binding.emailLayout.setError(activity.getString(R.string.valid_email_address_error));
        } else {
            forgotPasswordAPICall();
        }
    }

    private void forgotPasswordAPICall(){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.forgotPassword(forgotPassword);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
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

}
