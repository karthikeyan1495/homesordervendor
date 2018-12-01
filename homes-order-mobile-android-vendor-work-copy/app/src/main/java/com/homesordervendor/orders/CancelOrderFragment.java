package com.homesordervendor.orders;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.homesordervendor.R;
import com.homesordervendor.databinding.FragmentCancelOrderBinding;
import com.homesordervendor.orders.model.CancelOrder;
import com.homesordervendor.orders.model.OrderItem;
import com.homesordervendor.orders.viewmodel.CancelOrderVM;


/**
 * Created by innoppl on 18/04/18.
 */

public class CancelOrderFragment extends DialogFragment {

    FragmentCancelOrderBinding binding;
    CancelOrderVM cancelOrderVM;
    OrderItem orderItem=new OrderItem();
    OrderCancelListener orderCancelListener;
    public CancelOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        bindView(inflater, container);
        return binding.getRoot();
    }

    public void setOrderItem(OrderItem orderItem){
        this.orderItem=orderItem;
    }

    public void setOrderCancelListener(OrderCancelListener orderCancelListener){
        this.orderCancelListener=orderCancelListener;
    }


    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cancel_order, container, false);
        cancelOrderVM=new CancelOrderVM(getActivity(),this,orderItem,orderCancelListener);
        binding.setCancelOrderVM(cancelOrderVM);
        binding.setCancelOrder(new CancelOrder());
    }

    public interface OrderCancelListener{
        void orderCancelled(OrderItem orderItem);
    }
}

