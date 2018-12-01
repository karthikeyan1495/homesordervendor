package com.homesordervendor.product.productlist.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.MyProductItemBinding;
import com.homesordervendor.product.productlist.ProductListFragment;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.product.productlist.viewmodel.MyProductItemVM;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mac on 3/5/18.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> implements Observer {

    Activity activity;
    List<ProductItem> productItems=new ArrayList<>();
    ProductListFragment productListFragment;

    public ProductListAdapter(Activity activity,ProductListFragment productListFragment){
        this.activity=activity;
        this.productListFragment=productListFragment;
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MyProductItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.my_product_item, parent, false);
        return new ProductListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.ViewHolder holder, int position) {
        holder.bind(productItems.get(position));
        setUpObserver(holder.myProductItemVM);
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    @Override
    public void update(Observable observable, Object object) {
        if (observable instanceof MyProductItemVM) {
            ProductItem items=(ProductItem)object;
            productItems.remove(items);
            notifyDataSetChanged();
            if (productItems.size()==0) {
                productListFragment.productListVM.isNoData.set(true);
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
        private MyProductItemBinding binding;
        MyProductItemVM myProductItemVM;
        public ViewHolder(MyProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProductItem productItem) {
            myProductItemVM=new MyProductItemVM(activity);
            binding.setMyProductItemVM(myProductItemVM);
            binding.setProductItem(productItem);
            binding.executePendingBindings();
        }
    }
}
