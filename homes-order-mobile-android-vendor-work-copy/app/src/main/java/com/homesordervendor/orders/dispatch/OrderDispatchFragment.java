package com.homesordervendor.orders.dispatch;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentOrderDispatchBinding;
import com.homesordervendor.orders.OrderStatus;
import com.homesordervendor.orders.dispatch.viewmodel.OrderDispatchVM;
import com.homesordervendor.orders.newlist.adapter.NewListAdapter;
import com.homesordervendor.orders.tab.OrdersTabFragment;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDispatchFragment extends Fragment implements Observer {


    FragmentOrderDispatchBinding binding;
    OrderDispatchVM orderDispatchVM;
    NewListAdapter adapter;

    public OrderDispatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        setupRecyclerView();
        setUpObserver(orderDispatchVM);
        return binding.getRoot();
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_order_dispatch, container, false);
        orderDispatchVM=new OrderDispatchVM(getActivity());
        binding.setOrderDispatchVM(orderDispatchVM);
    }

    private void setupRecyclerView(){
        adapter=new NewListAdapter(getActivity(), OrderStatus.DISPATCH);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        if (OrdersTabFragment.isFirstTime){
            OrdersTabFragment.isFirstTime=false;
            refreshData();
        }

    }

    private void refreshData(){
        orderDispatchVM.orderListAPICall();
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
        if(observable instanceof OrderDispatchVM) {
            adapter.setData(orderDispatchVM.getOrderItemList());
        }
    }
}