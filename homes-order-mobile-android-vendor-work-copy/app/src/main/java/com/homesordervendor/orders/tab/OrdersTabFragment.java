package com.homesordervendor.orders.tab;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentOrdersTabBinding;
import com.homesordervendor.orders.tab.adapter.OrderTabAdapter;
import com.homesordervendor.orders.viewmodel.OrdersTabVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersTabFragment extends Fragment {

    FragmentOrdersTabBinding binding;
    OrdersTabVM ordersTabVM;
    public static boolean isFirstTime=true;
    int tabPosition=0;

    public OrdersTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getIntentData();
        isFirstTime=true;
        bindView(inflater, container);
        setupViewPager(binding.viewpager);
        return binding.getRoot();
    }

    private void getIntentData(){
        Bundle bundle=getArguments();
        if (bundle!=null){
            tabPosition=bundle.getInt("tabPosition");
        }
    }
    public void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_orders_tab, container, false);
        ordersTabVM=new OrdersTabVM(getActivity());
        binding.setOrdersTabVM(ordersTabVM);
    }

    private void setupViewPager(ViewPager viewPager) {
        OrderTabAdapter adapter = new OrderTabAdapter(getActivity(),getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(tabPosition);
        binding.ordersTabs.setupWithViewPager(viewPager);
        binding.viewpager.setOffscreenPageLimit(5);
    }
}
