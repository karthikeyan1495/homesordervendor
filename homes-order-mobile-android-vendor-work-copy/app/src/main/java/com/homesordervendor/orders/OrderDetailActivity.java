package com.homesordervendor.orders;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityOrderDetailBinding;
import com.homesordervendor.orders.adapter.OrderProductAdapter;
import com.homesordervendor.orders.model.OrderItem;
import com.homesordervendor.orders.viewmodel.OrderDetailVM;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class OrderDetailActivity extends AppCompatActivity implements Observer {

    ActivityOrderDetailBinding binding;
    OrderDetailVM orderDetailVM;
    OrderItem orderItem=new OrderItem();
    public String orderId="";

    public String navigationFrom="";

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        bindView();
        setUpObserver(orderDetailVM);
        setupRecyclerView(orderItem.getItems());
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof OrderDetailVM) {
            binding.setOrderItem(orderDetailVM.getOrderItemDetail());
            setupRecyclerView(orderDetailVM.getOrderItemDetail().getItems());
        }
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            navigationFrom=bundle.getString("navigationFrom");
            if (navigationFrom!=null&&navigationFrom.equals(NavigationFromEnum.ORDER_LIST.getValue())) {
                orderItem = (OrderItem) bundle.getSerializable("orderItem");
                orderId=orderItem.getOrderID();
            }else{
                orderId=bundle.getString("orderId");
            }
        }
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_detail);
        orderDetailVM=new OrderDetailVM(this,orderId);
        binding.setOrderDetailVM(orderDetailVM);
        if (navigationFrom!=null&&navigationFrom.equals(NavigationFromEnum.ORDER_LIST.getValue())) {
            binding.setOrderItem(orderItem);
        }
    }

    private void setupRecyclerView(List<ProductItem> list){
        OrderProductAdapter adapter=new OrderProductAdapter(this,list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnStatusUpdateListener(() -> {
            orderDetailVM.orderDetailAPICall(orderId);
        });
    }
}
