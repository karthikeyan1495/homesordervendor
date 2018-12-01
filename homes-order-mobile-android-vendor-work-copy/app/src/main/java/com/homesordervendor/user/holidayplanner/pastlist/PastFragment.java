package com.homesordervendor.user.holidayplanner.pastlist;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentPastBinding;
import com.homesordervendor.user.holidayplanner.activelist.adapter.ActiveAdapter;
import com.homesordervendor.user.holidayplanner.model.HolidayPlanner;
import com.homesordervendor.user.holidayplanner.pastlist.viewmodel.PastVM;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PastFragment extends Fragment {

    FragmentPastBinding binding;
    PastVM pastVM;

    ActiveAdapter adapter;

    List<HolidayPlanner> list=new ArrayList<>();

    public PastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getIntentData();
        bindView(inflater, container);
        setupRecyclerView();
        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_past, container, false);
        pastVM=new PastVM(getActivity());
        binding.setPastVM(pastVM);
    }

    private void setupRecyclerView(){
        adapter=new ActiveAdapter(getActivity(),list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
    }

    private void getIntentData(){
        Bundle bundle=getArguments();
        if (bundle!=null){
            list= (List<HolidayPlanner>) bundle.getSerializable("past");
        }
    }

}