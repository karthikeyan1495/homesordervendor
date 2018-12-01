package com.homesordervendor.product.addproduct.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ImagePreviewItemBinding;
import com.homesordervendor.product.addproduct.model.ImageURI;
import com.homesordervendor.product.addproduct.viewmodel.ImagePreviewItemVM;

import java.util.List;

/**
 * Created by mac on 3/4/18.
 */

public class ImagePreviewAdapter extends PagerAdapter {

    private List<ImageURI> images;
    private LayoutInflater inflater;
    private Activity activity;

    public ImagePreviewAdapter(Activity activity,List<ImageURI> images) {
        this.activity = activity;
        this.images = images;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        ImagePreviewItemBinding binding= DataBindingUtil.inflate(inflater, R.layout.image_preview_item, view, false);
        binding.setImagePreviewItemVM(new ImagePreviewItemVM(activity));
        binding.setImageURI(images.get(position));
        view.addView(binding.getRoot(), 0);
        return binding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}