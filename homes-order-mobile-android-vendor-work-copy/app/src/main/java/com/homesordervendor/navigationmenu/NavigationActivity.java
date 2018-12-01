package com.homesordervendor.navigationmenu;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.bankinformation.BankInformationFragment;
import com.homesordervendor.databinding.ActivityNavigationBinding;
import com.homesordervendor.navigationmenu.adapter.MenuAdapter;
import com.homesordervendor.navigationmenu.viewmodel.NavigationVM;
import com.homesordervendor.orders.tab.OrdersTabFragment;
import com.homesordervendor.product.tab.ProductTabFragment;
import com.homesordervendor.setting.SettingActivity;
import com.homesordervendor.user.dashboard.DashboardFragment;
import com.homesordervendor.user.holidayplanner.HolidayPlannerTabFragment;
import com.homesordervendor.user.profile.ProfileFragment;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NavigationActivity extends AppCompatActivity implements OnNavigationMenuListener,DashboardFragment.OnOrderCountListener {

    ActivityNavigationBinding binding;
    NavigationVM navigationVM;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        navigationBarSetup();
        setRecyclerView(menuList());
        //setupProductFragment(0);
        pushScreen(new DashboardFragment());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_navigation);
        navigationVM=new NavigationVM(this);
        binding.setNavigationVM(navigationVM);
        setSupportActionBar(binding.toolbar);

       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();*/
    }

    private void navigationBarSetup() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        binding.toggleAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void setRecyclerView(List<com.homesordervendor.navigationmenu.model.Menu> list){
        MenuAdapter adapter=new MenuAdapter(this,list,this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private List<com.homesordervendor.navigationmenu.model.Menu> menuList(){
        String[] menuArray = getResources().getStringArray(R.array.menu_array);
        List<com.homesordervendor.navigationmenu.model.Menu> list=new ArrayList<>();
        for (int i=0;i<menuArray.length;i++){
            com.homesordervendor.navigationmenu.model.Menu menu=new com.homesordervendor.navigationmenu.model.Menu();
            menu.setId(i);
            menu.setName(menuArray[i]);
            list.add(menu);
        }
        return list;
    }

    private void setupProductFragment(int tabPosition){
        ProductTabFragment productTabFragment=new ProductTabFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("tabPosition",tabPosition);
        productTabFragment.setArguments(bundle);
        pushScreen(productTabFragment);
    }

    private void setupOrderFragment(int tabPosition){
        OrdersTabFragment ordersTabFragment=new OrdersTabFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("tabPosition",tabPosition);
        ordersTabFragment.setArguments(bundle);
        pushScreen(ordersTabFragment);
    }


    public void pushScreen(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_layout, fragment)
                .commit();
    }

    @Override
    public void onClickNavigationItem(com.homesordervendor.navigationmenu.model.Menu menu) {
        if(menu.getId()==0){
            pushScreen(new DashboardFragment());
        }else if (menu.getId()==1){
            pushScreen(new ProfileFragment());
        }else if (menu.getId()==2){
            setupProductFragment(0);
        }else if (menu.getId()==3){
            setupProductFragment(1);
        }else if (menu.getId()==4){
            setupProductFragment(2);
        }else if (menu.getId()==5){
            setupOrderFragment(0);
           // pushScreen(new OrdersTabFragment());
        }else if(menu.getId()==6){
            pushScreen(new HolidayPlannerTabFragment());
        }else if(menu.getId()==7){
            pushScreen(new BankInformationFragment());
        }else if(menu.getId()==8){
            startActivity(new Intent(NavigationActivity.this, SettingActivity.class));
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClickOrderCountItem(int position) {
        setupOrderFragment(position);
    }
}
