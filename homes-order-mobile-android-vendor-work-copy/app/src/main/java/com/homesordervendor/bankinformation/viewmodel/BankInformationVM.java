package com.homesordervendor.bankinformation.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.bankinformation.model.BankInfoRequest;
import com.homesordervendor.databinding.FragmentBankInformationBinding;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.Util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/6/18.
 */

public class BankInformationVM {
    Activity activity;
    FragmentBankInformationBinding binding;
    MyProgressDialog myProgressDialog;
    public BankInformationVM(Activity activity,FragmentBankInformationBinding binding){
        this.activity=activity;
        this.binding=binding;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickRootView(View view){
        Util.getInstance().hideKeyboard(activity);
    }

    public void onClickSave(View view, BankInfoRequest bankInfo){
        Util.getInstance().hideKeyboard(activity);
        removeErrorMessage();
        validation(bankInfo);
    }

    public void onAccountHolderNameTextChanged(CharSequence s, int start, int before, int count,
                                           BankInfoRequest bankInfo) {
        if (s.toString().trim().length()!=0){
            binding.bankInfoAccountHolderNameLayout.setError(null);
        }
    }

    public void onBankNameTextChanged(CharSequence s, int start, int before, int count,
                                      BankInfoRequest bankInfo) {
        if (s.toString().trim().length()!=0){
            binding.bankNameLayout.setError(null);
        }
    }

    public void onAccountNumberTextChanged(CharSequence s, int start, int before, int count,
                                           BankInfoRequest bankInfo) {
        if (s.toString().trim().length()!=0){
            binding.bankInfoAccountNumberLayout.setError(null);
        }
    }

    public void onIBANTextChanged(CharSequence s, int start, int before, int count,
                                               BankInfoRequest bankInfo) {
        if (s.toString().trim().length()!=0){
            binding.bankInfoIbanLayout.setError(null);
        }
    }

    public void onBusinessLicenceNumberTextChanged(CharSequence s, int start, int before, int count,
                                  BankInfoRequest bankInfo) {
        binding.businessLicenceNumbersLayout.setError(null);
        if (s.toString().trim().length()!=0){
            binding.businessLicenceNumbersLayout.setError(null);
        }
    }



    private void validation(BankInfoRequest bankInfo){
        if (bankInfo.getAccount_holder_names()==null||bankInfo.getAccount_holder_names().trim().length()==0){
            binding.bankInfoAccountHolderNameLayout.setError(activity.getString(R.string.valid_account_holder_error));
        }else if (bankInfo.getBank_names()==null||bankInfo.getBank_names().trim().length()==0){
            binding.bankNameLayout.setError(activity.getString(R.string.valid_bank_name_error));
        }else if (bankInfo.getAccount_numbers()==null||bankInfo.getAccount_numbers().trim()
                .length()==0){
            binding.bankInfoAccountNumberLayout.setError(activity.getString(R.string.valid_account_number_error));
        }else if (bankInfo.getIbans()==null||bankInfo.getIbans().trim().length()<16||bankInfo
                .getIbans().trim().length()>16){
            binding.bankInfoIbanLayout.setError(activity.getString(R.string.valid_iban_error));
        }else if (bankInfo.getBusiness_licence_numbers()==null||bankInfo.getBusiness_licence_numbers().trim().length()==0){
            binding.businessLicenceNumbersLayout.setError(activity.getString(R.string.valid_business_licence_numbers_error));
        }else {
            saveBankInfoAPICall(bankInfo);
        }
    }

    private void removeErrorMessage() {
        binding.bankInfoAccountHolderNameLayout.setError(null);
        binding.bankNameLayout.setError(null);
        binding.bankInfoAccountNumberLayout.setError(null);
        binding.bankInfoIbanLayout.setError(null);
        binding.businessLicenceNumbersLayout.setError(null);
    }

    private void saveBankInfoAPICall(BankInfoRequest bankInfo){
        if (InternetChecker.getInstance().isReachable()) {
            bankInfo.setMode_of_payments("online");
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<User>> observable = api.bankInfoUpdate(MySession.getInstance(activity).getUser().getToken(),bankInfo);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            User user=MySession.getInstance(activity).getUser();
                            user.getProfile().setBusiness_licence_number(responses.body()
                                    .getProfile().getBusiness_licence_number());
                            user.getProfile().setBankinfo(responses.body().getProfile().getBankinfo());
                            MySession.getInstance(activity).saveUser(user);
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
