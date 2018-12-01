package com.homesordervendor.user.holidayplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityHolidayPlannerDetailBinding;
import com.homesordervendor.user.holidayplanner.model.HolidayPlanner;
import com.homesordervendor.user.holidayplanner.viewmodel.HolidayPlannerDetailVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;

public class HolidayPlannerDetailActivity extends AppCompatActivity {

    ActivityHolidayPlannerDetailBinding binding;
    HolidayPlannerDetailVM holidayPlannerDetailVM;
    HolidayPlanner holidayPlanner=new HolidayPlanner();

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        bindView();
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_holiday_planner_detail);
        holidayPlannerDetailVM=new HolidayPlannerDetailVM(this);
        binding.setHolidayPlannerDetailVM(holidayPlannerDetailVM);
        binding.setHolidayPlanner(holidayPlanner);
    }
    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            holidayPlanner=(HolidayPlanner) bundle.getSerializable("holidayPlanner");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        if (holidayPlannerDetailVM.isUpdated) {
            setResult(Activity.RESULT_OK, intent);
        }else{
            setResult(Activity.RESULT_CANCELED, intent);
        }
        finish();
        super.onBackPressed();

    }
}
