package com.homesordervendor.orders.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.OrderProductItemBinding;
import com.homesordervendor.orders.viewmodel.OrderProductItemVM;
import com.homesordervendor.product.productlist.model.ProductItem;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mac on 3/6/18.
 */

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> implements Observer {

    Activity activity;
    List<ProductItem> productItems;

    OnStatusUpdateListener onStatusUpdateListener;

    public OrderProductAdapter(Activity activity, List<ProductItem> productItems){
        this.activity=activity;
        this.productItems=productItems;
    }

    @Override
    public OrderProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        OrderProductItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_product_item, parent, false);
        return new OrderProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(OrderProductAdapter.ViewHolder holder, int position) {
        holder.bind(productItems.get(position));
        setUpObserver(holder.orderProductItemVM);
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof OrderProductItemVM) {
            if(onStatusUpdateListener!=null){
                onStatusUpdateListener.onStatusChanged();
            }
        }
    }
    public void setOnStatusUpdateListener(OnStatusUpdateListener onStatusUpdateListener)
    {
        this.onStatusUpdateListener=onStatusUpdateListener;
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private OrderProductItemBinding binding;
        OrderProductItemVM orderProductItemVM;
        public ViewHolder(OrderProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProductItem productItem) {
            orderProductItemVM=new OrderProductItemVM(activity);
            binding.setOrderProductItemVM(orderProductItemVM);
            binding.setProductItem(productItem);
            binding.executePendingBindings();
        }
    }

    public interface OnStatusUpdateListener{
        void onStatusChanged();
    }
}
