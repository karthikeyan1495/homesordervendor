package com.homesordervendor.product.addproduct;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentProductInformationBinding;
import com.homesordervendor.product.addproduct.viewmodel.ProductInformationVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductInformationFragment extends Fragment {


    FragmentProductInformationBinding binding;
    public ProductInformationVM productInformationVM;



    public ProductInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        if (getActivity() instanceof AddProductActivity) {
            AddProductActivity addProductActivity = (AddProductActivity) getActivity();
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_information, container, false);
            productInformationVM = new ProductInformationVM(getActivity(), this, addProductActivity.productModel);
            binding.setProductInformationVM(productInformationVM);
            binding.setProductModel(addProductActivity.productModel);

        }
    }
}
