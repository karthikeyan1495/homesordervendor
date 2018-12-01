package com.homesordervendor.user.deliveryslot.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesordervendor.R;
import com.homesordervendor.databinding.DeliverySlotItemBinding;
import com.homesordervendor.user.deliveryslot.model.Slots;
import com.homesordervendor.user.deliveryslot.viewmodel.DeliverySlotItemVM;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mac on 2/22/18.
 */

public class DeliverySlotAdapter extends RecyclerView.Adapter<DeliverySlotAdapter.ViewHolder> implements Observer {

    Activity activity;
    List<Slots> slots;
    OnDeliverySlotListener onDeliverySlotListener;

    public DeliverySlotAdapter(Activity activity,List<Slots> slots){
        this.activity=activity;
        this.slots=slots;
    }

    @Override
    public DeliverySlotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DeliverySlotItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.delivery_slot_item, parent, false);
        return new DeliverySlotAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DeliverySlotAdapter.ViewHolder holder, int position) {
        holder.bind(slots.get(position));
        setUpObserver(holder.deliverySlotItemVM);
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof DeliverySlotItemVM) {
            if (onDeliverySlotListener!=null){
                onDeliverySlotListener.onSelected();
            }
        }
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    public  void setOnDeliverySlotListener(OnDeliverySlotListener onDeliverySlotListener){
        this.onDeliverySlotListener=onDeliverySlotListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private DeliverySlotItemBinding binding;
        public DeliverySlotItemVM deliverySlotItemVM;
        public ViewHolder(DeliverySlotItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Slots slots) {
            deliverySlotItemVM=new DeliverySlotItemVM(activity);
            binding.setDeliverySlotItemVM(deliverySlotItemVM);
            binding.setSlots(slots);
            binding.executePendingBindings();
        }
    }

    public interface OnDeliverySlotListener{
        void onSelected();
    }
}
