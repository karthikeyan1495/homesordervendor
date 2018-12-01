package com.homesordervendor.product.featured.featuredlist.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FeaturedItemBinding;
import com.homesordervendor.product.featured.featuredlist.viewmodel.FeaturedItemVM;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 3/5/18.
 */

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder>{

    Activity activity;

    List<ProductItem> productItems=new ArrayList<>();

    public FeaturedAdapter(Activity activity){
        this.activity=activity;
    }

    @Override
    public FeaturedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FeaturedItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.featured_item, parent, false);
        return new FeaturedAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FeaturedAdapter.ViewHolder holder, int position) {
        holder.bind(productItems.get(position));
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    public void setData(List<ProductItem> list){
        productItems.clear();
        productItems.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FeaturedItemBinding binding;
        public ViewHolder(FeaturedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProductItem productItem) {
            binding.setFeaturedItemVM(new FeaturedItemVM(activity));
            binding.setProductItem(productItem);
            //drawCompletedPercentage(productItem);
            binding.executePendingBindings();
        }

        public void drawCompletedPercentage(ProductItem productItem){
            int width = Util.getInstance().getScreenWidth(activity);
            float margin=(Util.getInstance().convertDpToPixel((float) 10))*2;
            width=width-(int)margin;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(width,(int)activity.getResources().getDimension(R
                            .dimen.complete_percentage_height));
            binding.completedPercentage.setLayoutParams(params);
        }
    }
}
