package com.homesordervendor.user.profile.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.accountinformation.AccountInformationActivity;
import com.homesordervendor.user.deliveryslot.DeliverySlotActivity;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.user.profile.ProfileFragment;
import com.homesordervendor.user.register.model.Register;
import com.homesordervendor.user.resetpassword.ResetPasswordActivity;
import com.homesordervendor.user.shippingarea.ShippingAreaActivity;
import com.homesordervendor.user.subscribe.SubscribeActivity;
import com.homesordervendor.util.APIUtil;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/8/18.
 */

public class ProfileVM extends java.util.Observable{

    Activity activity;
    ProfileFragment profileFragment;
    MyProgressDialog myProgressDialog;

    public ProfileVM(Activity activity,ProfileFragment profileFragment){
        this.activity=activity;
        this.profileFragment=profileFragment;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickResetPassword(View view){
        activity.startActivity(new Intent(activity, ResetPasswordActivity.class));
    }

    public void onClickAccountInformation(View view){
        activity.startActivity(new Intent(activity, AccountInformationActivity.class));
    }

    public void onClickDeliverySlot(View view){
        activity.startActivity(new Intent(activity, DeliverySlotActivity.class));
    }

    public void onClickShippingMethods(View view){
        activity.startActivity(new Intent(activity, ShippingAreaActivity.class));
    }

    public void onClickSubscription(View view){
        activity.startActivity(new Intent(activity, SubscribeActivity.class));
    }

    public void onClickProfileImage(View view){
        openSingleImageChooser();
    }

    public void openSingleImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        profileFragment.startActivityForResult(Intent.createChooser(intent, activity.getString(R.string
                .select_pictures)), profileFragment.PROFILE_IMAGE_UPDATE);
    }

    public void updateUserAPICall(Uri uri) {

        if (InternetChecker.getInstance().isReachable()) {

            myProgressDialog.showDialog(activity);

            User user=MySession.getInstance(activity).getUser();
            Register register=new Register();
            register.setEmail(user.getProfile().getEmail());
            register.setFullname(user.getProfile().getFullname());
            register.setPhone(user.getProfile().getPhone_no());
            register.setProfile(APIUtil.getImageByte(activity,uri));

            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<User>> observable = api.updateUser(MySession.getInstance
                    (activity).getUser().getToken(),register);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {

                        myProgressDialog.dismissDialog();

                        if (responses.code() == 200) {

                            user.getProfile().setImage(responses.body().getProfile().getImage());
                            MySession.getInstance(activity).saveUser(user);
                            setChanged();
                            notifyObservers();
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
