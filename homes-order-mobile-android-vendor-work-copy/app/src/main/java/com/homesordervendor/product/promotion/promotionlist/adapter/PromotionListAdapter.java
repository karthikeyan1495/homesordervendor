package com.homesordervendor.product.promotion.promotionlist.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.PromotionItemBinding;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.product.promotion.promotionlist.PromotionListFragment;
import com.homesordervendor.product.promotion.promotionlist.viewmodel.PromotionItemVM;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mac on 3/5/18.
 */

public class PromotionListAdapter extends RecyclerView.Adapter<PromotionListAdapter.ViewHolder> implements Observer {

  Activity activity;
    List<ProductItem> productItems=new ArrayList<>();
    PromotionListFragment promotionListFragment;

    public PromotionListAdapter(Activity activity,PromotionListFragment promotionListFragment){
        this.activity=activity;
        this.promotionListFragment=promotionListFragment;
    }

    @Override
    public PromotionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PromotionItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.promotion_item, parent, false);
        return new PromotionListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PromotionListAdapter.ViewHolder holder, int position) {
        holder.bind(productItems.get(position));
        setUpObserver(holder.promotionItemVM);

    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    @Override
    public void update(Observable observable, Object object) {
        if (observable instanceof PromotionItemVM) {
            ProductItem items=(ProductItem)object;
            productItems.remove(items);
            notifyDataSetChanged();
            if (productItems.size()==0) {
                promotionListFragment.promotionListVM.isNoData.set(true);
            }
        }
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }
    public void setData(List<ProductItem> list){
        productItems.clear();
        productItems.addAll(list);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private PromotionItemBinding binding;
        PromotionItemVM promotionItemVM;
        public ViewHolder(PromotionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProductItem productItem) {
            promotionItemVM=new PromotionItemVM(activity,promotionListFragment);
            binding.setPromotionItemVM(promotionItemVM);
            binding.setProductItem(productItem);
            binding.executePendingBindings();
        }
    }

}
