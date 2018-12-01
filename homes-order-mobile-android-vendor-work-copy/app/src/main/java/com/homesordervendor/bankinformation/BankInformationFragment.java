package com.homesordervendor.bankinformation;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.bankinformation.model.BankInfoRequest;
import com.homesordervendor.bankinformation.viewmodel.BankInformationVM;
import com.homesordervendor.databinding.FragmentBankInformationBinding;
import com.homesordervendor.databinding.FragmentNewListBinding;
import com.homesordervendor.orders.newlist.adapter.NewListAdapter;
import com.homesordervendor.orders.newlist.viewmodel.NewListVM;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.login.model.BankInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankInformationFragment extends Fragment {


    FragmentBankInformationBinding binding;
    BankInformationVM bankInformationVM;


    public BankInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_bank_information, container, false);
        bankInformationVM=new BankInformationVM(getActivity(),binding);
        binding.setBankInformationVM(bankInformationVM);
        BankInfo bankInfo=MySession.getInstance(getActivity()).getUser().getProfile().getBankinfo();
        BankInfoRequest bankInfoRequest=new BankInfoRequest();
        if (bankInfo!=null){
            bankInfoRequest.setAccount_holder_names(bankInfo.getAccount_holder_name());
            bankInfoRequest.setBank_names(bankInfo.getBank_name());
            bankInfoRequest.setAccount_numbers(bankInfo.getAccount_number());
            bankInfoRequest.setIbans(bankInfo.getIban());
            bankInfoRequest.setBusiness_licence_numbers(MySession.getInstance(getActivity()).getUser().getProfile().getBusiness_licence_number());
        }
        binding.setBankInfoRequest(bankInfoRequest);
    }

}
