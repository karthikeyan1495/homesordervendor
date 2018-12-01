package com.homesordervendor.product.promotion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityPromotionBinding;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.product.promotion.model.Promotion;
import com.homesordervendor.product.promotion.viewmodel.PromotionVM;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class PromotionActivity extends AppCompatActivity implements Observer {

    ActivityPromotionBinding binding;
    PromotionVM promotionVM;
    ProductItem productItem=new ProductItem();
    Promotion promotion=new Promotion();

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
        setUpObserver(promotionVM);
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            productItem=(ProductItem) bundle.getSerializable("productItem");
        }
    }
    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_promotion);
        promotionVM=new PromotionVM(this,productItem,binding);
        binding.setPromotionVM(promotionVM);
        binding.setProductItem(productItem);
        binding.setPromotion(promotion);
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof PromotionVM) {
            Promotion promotion=promotionVM.getPromotionModel();
            if (MySession.getInstance(this).getCurrency().equals(getString(R.string.sar))){
                promotion.setDiscountPrice(promotion.getDiscountPriceInSAR());
            }
            binding.setPromotion(promotion);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
        super.onBackPressed();

    }
}
