package com.homesordervendor.user.holidayplanner;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentHolidayPlannerTabBinding;
import com.homesordervendor.user.holidayplanner.adapter.HolidayPlannerTabAdapter;
import com.homesordervendor.user.holidayplanner.model.HolidayPlanner;
import com.homesordervendor.user.holidayplanner.viewmodel.HolidayPlannerTabVM;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolidayPlannerTabFragment extends Fragment implements Observer {

    FragmentHolidayPlannerTabBinding binding;
    HolidayPlannerTabVM holidayPlannerTabVM;
    public static boolean isFirstTime = true;

    public static HolidayPlannerTabFragment holidayPlannerTabFragment;

    public static int UPDATE_CODE = 100;


    public HolidayPlannerTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        holidayPlannerTabFragment = this;
        isFirstTime = true;
        bindView(inflater, container);
        setUpObserver(holidayPlannerTabVM);
        //setupViewPager(binding.viewpager);
        return binding.getRoot();
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    public void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_holiday_planner_tab, container, false);
        holidayPlannerTabVM = new HolidayPlannerTabVM(getActivity());
        binding.setHolidayPlannerTabVM(holidayPlannerTabVM);
    }

    private void setupViewPager(List<HolidayPlanner> list) {
        HolidayPlannerTabAdapter adapter = new HolidayPlannerTabAdapter(getActivity(),
                getChildFragmentManager(), list);
        binding.viewpager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.holidayPlannerTabs.setupWithViewPager(binding.viewpager);
    }

    @Override
    public void update(Observable observable, Object object) {
        if (observable instanceof HolidayPlannerTabVM) {
            setupViewPager(holidayPlannerTabVM.getHolidayPlannerList());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==UPDATE_CODE&&resultCode == RESULT_OK){
            holidayPlannerTabVM.getHolidayPlannerAPICall();
        }
    }
}
