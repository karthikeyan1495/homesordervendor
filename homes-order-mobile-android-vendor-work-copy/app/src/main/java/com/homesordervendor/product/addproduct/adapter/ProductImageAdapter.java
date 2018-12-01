package com.homesordervendor.product.addproduct.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.DeliverySlotItemBinding;
import com.homesordervendor.databinding.ImageAddItemBinding;
import com.homesordervendor.product.addproduct.ProductImageFragment;
import com.homesordervendor.product.addproduct.model.ImageURI;
import com.homesordervendor.product.addproduct.viewmodel.ImageAddItemVM;
import com.homesordervendor.user.deliveryslot.adapter.DeliverySlotAdapter;
import com.homesordervendor.user.deliveryslot.model.Slots;
import com.homesordervendor.user.deliveryslot.viewmodel.DeliverySlotItemVM;

import java.util.List;

/**
 * Created by mac on 3/3/18.
 */

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ViewHolder>{

    Activity activity;
    ProductImageFragment productImageFragment;
    List<ImageURI> list;

    public ProductImageAdapter(Activity activity,ProductImageFragment productImageFragment,List<ImageURI> list){
        this.activity=activity;
        this.productImageFragment=productImageFragment;
        this.list=list;
    }

    @Override
    public ProductImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ImageAddItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.image_add_item, parent, false);
        return new ProductImageAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ProductImageAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageAddItemBinding binding;
        public ViewHolder(ImageAddItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ImageURI imageURI) {
            binding.setImageAddItemVM(new ImageAddItemVM(activity,productImageFragment));
            binding.setImageURI(imageURI);
            binding.executePendingBindings();
        }
    }

}
