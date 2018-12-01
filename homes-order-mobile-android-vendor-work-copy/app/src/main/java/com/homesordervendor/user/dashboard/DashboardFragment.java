package com.homesordervendor.user.dashboard;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentDashboardBinding;
import com.homesordervendor.user.dashboard.viewmodel.DashboardVM;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements Observer {


    FragmentDashboardBinding binding;
    DashboardVM dashboardVM;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        setUpObserver(dashboardVM);
        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard, container, false);
        dashboardVM=new DashboardVM(getActivity());
        binding.setDashboardVM(dashboardVM);
        binding.setOrderCount(new OrderCount());
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof DashboardVM) {
            binding.setOrderCount(dashboardVM.getOrderCount());
        }
    }


    public interface OnOrderCountListener{
        public void  onClickOrderCountItem(int position);
    }

}
