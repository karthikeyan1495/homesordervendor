package com.homesordervendor.product.addproduct;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentProductPreviewBinding;
import com.homesordervendor.product.addproduct.adapter.ImagePreviewAdapter;
import com.homesordervendor.product.addproduct.model.ImageURI;
import com.homesordervendor.product.addproduct.model.ProductModel;
import com.homesordervendor.product.addproduct.viewmodel.ProductPreviewVM;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductPreviewFragment extends Fragment {


    FragmentProductPreviewBinding binding;
    ProductPreviewVM productPreviewVM;

    public ProductPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_preview, container, false);
        productPreviewVM = new ProductPreviewVM(getActivity());
        binding.setProductPreviewVM(productPreviewVM);
        if (getActivity() instanceof AddProductActivity) {
            AddProductActivity productActivity = (AddProductActivity) getActivity();
            binding.setProductModel(productActivity.productModel);
            setImageViewPager(productActivity.productModel);
        }
    }

    private void setImageViewPager(ProductModel productModel) {
        List<ImageURI> list = new ArrayList<>();
        list.add(productModel.getImageURI());
        list.addAll(productModel.getImageURIList());
        list.remove(list.size()-1);
        binding.sliderPager.setAdapter(new ImagePreviewAdapter(getActivity(), list));
        binding.sliderIndicator.setViewPager(binding.sliderPager);
    }
}
