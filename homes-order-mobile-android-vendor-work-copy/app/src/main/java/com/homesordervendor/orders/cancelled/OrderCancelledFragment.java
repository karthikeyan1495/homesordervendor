package com.homesordervendor.orders.cancelled;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentOrderCancelledBinding;
import com.homesordervendor.orders.OrderStatus;
import com.homesordervendor.orders.cancelled.viewmodel.OrderCancelledVM;
import com.homesordervendor.orders.newlist.adapter.NewListAdapter;
import com.homesordervendor.orders.tab.OrdersTabFragment;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderCancelledFragment extends Fragment implements Observer {


    FragmentOrderCancelledBinding binding;
    OrderCancelledVM orderCancelledVM;
    NewListAdapter adapter;

    public OrderCancelledFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        setupRecyclerView();
        setUpObserver(orderCancelledVM);
        return binding.getRoot();
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_order_cancelled, container, false);
        orderCancelledVM=new OrderCancelledVM(getActivity());
        binding.setOrderCancelledVM(orderCancelledVM);
    }

    private void setupRecyclerView(){
        adapter=new NewListAdapter(getActivity(), OrderStatus.CANCELED);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        if (OrdersTabFragment.isFirstTime){
            OrdersTabFragment.isFirstTime=false;
            refreshData();
        }

    }

    private void refreshData(){
        orderCancelledVM.orderListAPICall();
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
        if(observable instanceof OrderCancelledVM) {
            adapter.setData(orderCancelledVM.getOrderItemList());
        }
    }
}