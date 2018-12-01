package com.homesordervendor.product.tab;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentProductTabBinding;
import com.homesordervendor.product.tab.adapter.ProductTabAdapter;
import com.homesordervendor.product.tab.viewmodel.ProductTabVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductTabFragment extends Fragment {

    FragmentProductTabBinding binding;
    ProductTabVM productTabVM;
    int tabPosition=0;

    public static boolean isFirstTime=true;

    public ProductTabFragment() {
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
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_product_tab, container, false);
        productTabVM=new ProductTabVM(getActivity());
        binding.setProductTabVM(productTabVM);
    }

    private void setupViewPager(ViewPager viewPager) {
        ProductTabAdapter adapter = new ProductTabAdapter(getActivity(),getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(tabPosition);
        binding.productTabs.setupWithViewPager(viewPager);
        binding.viewpager.setOffscreenPageLimit(3);
    }
}
