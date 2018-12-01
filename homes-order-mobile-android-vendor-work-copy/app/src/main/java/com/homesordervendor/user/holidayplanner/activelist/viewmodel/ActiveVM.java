package com.homesordervendor.user.holidayplanner.activelist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.homesordervendor.user.holidayplanner.HolidayPlannerDetailActivity;
import com.homesordervendor.user.holidayplanner.HolidayPlannerTabFragment;

/**
 * Created by mac on 3/8/18.
 */

public class ActiveVM {

    Activity activity;

    public ActiveVM(Activity activity){
        this.activity=activity;
    }

    public void onClickAddHolidayPlanner(View view){
        Intent intent=new Intent(activity, HolidayPlannerDetailActivity.class);
        HolidayPlannerTabFragment.holidayPlannerTabFragment.startActivityForResult(intent, HolidayPlannerTabFragment
                .UPDATE_CODE);
    }

}
