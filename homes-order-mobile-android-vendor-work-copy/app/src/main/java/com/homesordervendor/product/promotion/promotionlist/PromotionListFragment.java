package com.homesordervendor.product.promotion.promotionlist;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentPromotionListBinding;
import com.homesordervendor.product.promotion.promotionlist.adapter.PromotionListAdapter;
import com.homesordervendor.product.promotion.promotionlist.viewmodel.PromotionListVM;
import com.homesordervendor.product.tab.ProductTabFragment;

import java.util.Observable;
import java.util.Observer;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromotionListFragment extends Fragment implements Observer {


    FragmentPromotionListBinding binding;
    public PromotionListVM promotionListVM;
    PromotionListAdapter adapter;

    public static int PROMOTION_UPDATE_CODE = 100;


    public PromotionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        setupRecyclerView();
        setUpObserver(promotionListVM);
        return binding.getRoot();
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }
    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_promotion_list, container, false);
        promotionListVM=new PromotionListVM(getActivity());
        binding.setPromotionListVM(promotionListVM);
    }

    private void setupRecyclerView(){
        adapter=new PromotionListAdapter(getActivity(),this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        if (ProductTabFragment.isFirstTime){
            ProductTabFragment.isFirstTime=false;
            refreshData();
        }
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof PromotionListVM) {
            adapter.setData(promotionListVM.getProductItems());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&!ProductTabFragment.isFirstTime) {
            refreshData();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PROMOTION_UPDATE_CODE&&resultCode == RESULT_OK){
            refreshData();
        }
    }

    private void refreshData(){
        promotionListVM.promotionListAPICall();
    }


}
