package com.homesordervendor.orders.processinglist;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentProcessingBinding;
import com.homesordervendor.orders.OrderStatus;
import com.homesordervendor.orders.newlist.adapter.NewListAdapter;
import com.homesordervendor.orders.processinglist.viewmodel.ProcessingVM;
import com.homesordervendor.orders.tab.OrdersTabFragment;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessingFragment extends Fragment implements Observer {


    FragmentProcessingBinding binding;
    ProcessingVM processingVM;
    NewListAdapter adapter;

    public ProcessingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        setupRecyclerView();
        setUpObserver(processingVM);
        return binding.getRoot();
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_processing, container, false);
        processingVM=new ProcessingVM(getActivity());
        binding.setProcessingVM(processingVM);
    }

    private void setupRecyclerView(){
        adapter=new NewListAdapter(getActivity(),OrderStatus.PROCESSING);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        if (OrdersTabFragment.isFirstTime){
            OrdersTabFragment.isFirstTime=false;
            refreshData();
        }

    }

    private void refreshData(){
        processingVM.orderListAPICall();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&!OrdersTabFragment.isFirstTime) {
            refreshData();
        }
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof ProcessingVM) {
            adapter.setData(processingVM.getOrderItemList());
        }
    }
}