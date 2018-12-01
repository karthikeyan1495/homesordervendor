package com.homesordervendor.product.featured.featuredlist;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentFeaturedListBinding;
import com.homesordervendor.product.featured.FeaturedActivity;
import com.homesordervendor.product.featured.featuredlist.adapter.FeaturedAdapter;
import com.homesordervendor.product.featured.featuredlist.viewmodel.FeaturedListVM;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.product.productlist.viewmodel.ProductListVM;
import com.homesordervendor.product.promotion.promotionlist.adapter.PromotionListAdapter;
import com.homesordervendor.product.tab.ProductTabFragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeaturedListFragment extends Fragment implements Observer {


    FragmentFeaturedListBinding binding;
    FeaturedListVM featuredListVM;
    FeaturedAdapter adapter;

    public FeaturedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        setupRecyclerView();
        setUpObserver(featuredListVM);
        return binding.getRoot();
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }
    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_featured_list, container, false);
        featuredListVM=new FeaturedListVM(getActivity());
        binding.setFeaturedListVM(featuredListVM);
    }

    private void setupRecyclerView(){
        adapter=new FeaturedAdapter(getActivity());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        if (ProductTabFragment.isFirstTime){
            ProductTabFragment.isFirstTime=false;
            refreshData();
        }
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof FeaturedListVM) {
            adapter.setData(featuredListVM.getProductItems());
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
        featuredListVM.featuredListAPICall();
    }

}
