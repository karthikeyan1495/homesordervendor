package com.homesordervendor.product.productlist;


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
import com.homesordervendor.databinding.FragmentProductListBinding;
import com.homesordervendor.product.productlist.adapter.ProductListAdapter;
import com.homesordervendor.product.productlist.viewmodel.ProductListVM;
import com.homesordervendor.product.tab.ProductTabFragment;

import java.util.Observable;
import java.util.Observer;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment implements Observer {


    FragmentProductListBinding binding;
    public ProductListVM productListVM;
    ProductListAdapter adapter;

    public static int PRODUCT_UPDATE_CODE = 100;


    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        setupRecyclerView();
        setUpObserver(productListVM);
        return binding.getRoot();
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }
    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_product_list, container, false);
        productListVM=new ProductListVM(getActivity(),this);
        binding.setProductListVM(productListVM);
    }
    private void setupRecyclerView(){
        adapter=new ProductListAdapter(getActivity(),this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        if (ProductTabFragment.isFirstTime){
            ProductTabFragment.isFirstTime=false;
            refreshData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&!ProductTabFragment.isFirstTime) {
            refreshData();
        }
    }

    private void refreshData(){
            productListVM.productListAPICall();
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof ProductListVM) {
            adapter.setData(productListVM.getProductItems());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PRODUCT_UPDATE_CODE&&resultCode == RESULT_OK){
           refreshData();
        }
    }

}
