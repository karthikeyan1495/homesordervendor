package com.homesordervendor.user.profile;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentProfileBinding;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.profile.viewmodel.ProfileVM;

import java.util.Observable;
import java.util.Observer;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements Observer {

    public static final int PROFILE_IMAGE_UPDATE=100;

    FragmentProfileBinding binding;
    ProfileVM profileVM;
    String imageURL;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        setUpObserver(profileVM);
        return binding.getRoot();
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }
    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false);
        profileVM=new ProfileVM(getActivity(),this);
        binding.setProfileVM(profileVM);
        binding.setProfile(MySession.getInstance(getActivity()).getUser().getProfile());
        binding.setImageURL(MySession.getInstance(getActivity()).getUser().getProfile().getImage());
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof ProfileVM) {
            binding.setImageURL(imageURL);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                imageURL=uri.toString();
                profileVM.updateUserAPICall(uri);
            }
        }
    }
}
