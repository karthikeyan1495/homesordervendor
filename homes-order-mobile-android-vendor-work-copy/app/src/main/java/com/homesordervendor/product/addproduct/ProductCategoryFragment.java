package com.homesordervendor.product.addproduct;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentProductCategoryBinding;
import com.homesordervendor.product.addproduct.viewmodel.ProductCatagoryVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCategoryFragment extends Fragment {


    FragmentProductCategoryBinding binding;
    ProductCatagoryVM productCatagoryVM;

    public ProductCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container) {
        if (getActivity() instanceof AddProductActivity) {
            AddProductActivity addProductActivity = (AddProductActivity) getActivity();
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_category, container, false);
            productCatagoryVM = new ProductCatagoryVM(getActivity(), addProductActivity.productModel);
            binding.setProductCatagoryVM(productCatagoryVM);
            binding.setProductModel(addProductActivity.productModel);
        }
    }

}
