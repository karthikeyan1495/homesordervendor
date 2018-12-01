package com.homesordervendor.orders.newlist.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.NewOrderItemBinding;
import com.homesordervendor.orders.OrderStatus;
import com.homesordervendor.orders.model.OrderItem;
import com.homesordervendor.orders.newlist.viewmodel.NewItemVM;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mac on 3/6/18.
 */

public class NewListAdapter extends RecyclerView.Adapter<NewListAdapter.ViewHolder> implements Observer {

    Activity activity;
    List<OrderItem> orderItemList=new ArrayList<>();
    OrderStatus orderStatus;

    public NewListAdapter(Activity activity,OrderStatus orderStatus){
        this.activity=activity;
        this.orderStatus=orderStatus;
    }

    @Override
    public NewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NewOrderItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.new_order_item, parent, false);
        return new NewListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NewListAdapter.ViewHolder holder, int position) {
        holder.bind(orderItemList.get(position));
        setUpObserver(holder.newItemVM);
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    @Override
    public void update(Observable observable, Object object) {
        if (observable instanceof NewItemVM) {
            OrderItem orderItem=(OrderItem)object;
            orderItemList.remove(orderItem);
            notifyDataSetChanged();
        }
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    public void setData(List<OrderItem> list){
        orderItemList.clear();
        orderItemList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private NewOrderItemBinding binding;
        NewItemVM newItemVM;
        public ViewHolder(NewOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OrderItem orderItem) {
            newItemVM=new NewItemVM(activity,orderStatus);
            binding.setNewItemVM(newItemVM);
            binding.setOrderItem(orderItem);
            binding.setOrderStatus(orderStatus);
            binding.executePendingBindings();
        }
    }
}
