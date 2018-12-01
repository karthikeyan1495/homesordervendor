package com.homesordervendor.user.holidayplanner.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.homesordervendor.R;
import com.homesordervendor.user.holidayplanner.activelist.ActiveFragment;
import com.homesordervendor.user.holidayplanner.model.HolidayPlanner;
import com.homesordervendor.user.holidayplanner.pastlist.PastFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 3/8/18.
 */

public class HolidayPlannerTabAdapter extends FragmentStatePagerAdapter {

    Activity activity;
    List<HolidayPlanner> holidayPlannerList=new ArrayList<>();

    List<HolidayPlanner> activeList=new ArrayList<>();
    List<HolidayPlanner> pastList=new ArrayList<>();

    public HolidayPlannerTabAdapter(Activity activity,FragmentManager manager,List<HolidayPlanner> holidayPlannerList) {
        super(manager);
        this.activity=activity;
        this.holidayPlannerList=holidayPlannerList;
        parseActiveAndPastHoliday();
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            Bundle bundle=new Bundle();
            bundle.putSerializable("active",(Serializable) activeList);
            ActiveFragment activeFragment=new ActiveFragment();
            activeFragment.setArguments(bundle);
            return activeFragment;
        }else {
            Bundle bundle=new Bundle();
            bundle.putSerializable("past",(Serializable) pastList);
            PastFragment pastFragment=new PastFragment();
            pastFragment.setArguments(bundle);
            return pastFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        if (position==0){
            title=activity.getString(R.string.active);
        }else{
            title=activity.getString(R.string.past);
        }
        return title;
    }

    private void parseActiveAndPastHoliday(){
        activeList.clear();
        pastList.clear();
        for (HolidayPlanner holidayPlanner:holidayPlannerList){
            if (holidayPlanner.getStatus().toString().toLowerCase().trim().equals("active")){
                activeList.add(holidayPlanner);
            }else {
                pastList.add(holidayPlanner);
            }
        }
    }


}