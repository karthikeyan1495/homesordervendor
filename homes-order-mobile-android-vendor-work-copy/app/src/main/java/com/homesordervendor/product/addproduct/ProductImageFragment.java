package com.homesordervendor.product.addproduct;


import android.content.ClipData;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentProductImageBinding;
import com.homesordervendor.product.addproduct.adapter.ProductImageAdapter;
import com.homesordervendor.product.addproduct.model.ImageURI;
import com.homesordervendor.product.addproduct.viewmodel.ProductImageVM;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductImageFragment extends Fragment {

    public static final int SELECT_SINGLE_PICTURE = 100;
    public static final int SELECT_MULTIPLE_PICTURE = 200;

    public FragmentProductImageBinding binding;
    public ProductImageAdapter adapter;
    ProductImageVM productImageVM;

    public List<ImageURI> list;
    public ImageURI imageURI;

    public ProductImageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getActivity() instanceof AddProductActivity) {
            AddProductActivity addProductActivity=(AddProductActivity)getActivity();
            list=addProductActivity.productModel.getImageURIList();
            imageURI=addProductActivity.productModel.getImageURI();
            if (list.size() == 0) {
                list.add(getAddView());
            }
        }

        bindView(inflater, container);
        setRecyclerView();
        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_image, container, false);
        productImageVM = new ProductImageVM(getActivity(), this);
        binding.setProductImageVM(productImageVM);
        binding.setImageURI(imageURI);
    }

    private void setRecyclerView() {
        adapter = new ProductImageAdapter(getActivity(), this, list);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recyclerView.setAdapter(adapter);
    }

    private ImageURI getAddView() {
        ImageURI imageURI = new ImageURI();
        imageURI.setType(2);
        return imageURI;
    }

    // The following code is responsible of selecting multiple images from the storage.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri uri = item.getUri();
                        ImageURI imageURI = new ImageURI();
                        imageURI.setUri(uri.toString());
                        imageURI.setType(1);
                        imageURI.setImage(true);
                        if (requestCode == SELECT_SINGLE_PICTURE) {
                            this.imageURI = imageURI;
                            setImageURI(this.imageURI);
                            binding.setImageURI(this.imageURI);
                        } else {
                            list.remove(list.size() - 1);
                            list.add(imageURI);
                            list.add(getAddView());
                        }
                    }
                } else {
                    Uri uri = data.getData();
                    ImageURI imageURI = new ImageURI();
                    imageURI.setUri(uri.toString());
                    imageURI.setType(1);
                    imageURI.setImage(true);
                    if (requestCode == SELECT_SINGLE_PICTURE) {
                        this.imageURI = imageURI;
                        setImageURI(this.imageURI);
                        binding.setImageURI(this.imageURI);

                    } else {
                        list.remove(list.size() - 1);
                        list.add(imageURI);
                        list.add(getAddView());
                    }
                }
                setRecyclerView();
            }
        }

    }

    private void setImageURI(ImageURI imageURI){
        if(getActivity() instanceof AddProductActivity) {
            AddProductActivity addProductActivity=(AddProductActivity)getActivity();
            addProductActivity.productModel.setImageURI(imageURI);
        }
    }
}
