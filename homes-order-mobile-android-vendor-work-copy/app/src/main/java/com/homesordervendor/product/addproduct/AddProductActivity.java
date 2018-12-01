package com.homesordervendor.product.addproduct;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.homesordervendor.R;
import com.homesordervendor.databinding.ActivityAddProductBinding;
import com.homesordervendor.product.addproduct.model.ProductModel;
import com.homesordervendor.product.addproduct.viewmodel.AddProductVM;
import com.homesordervendor.util.MyContextWrapper;
import com.homesordervendor.util.Util;

import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {
    ActivityAddProductBinding binding;
    AddProductVM addProductVM;

    public ProductModel productModel=new ProductModel();

    public ProductCategoryFragment productCategoryFragment;
    public ProductInformationFragment productInformationFragment;
    public ProductImageFragment productImageFragment;
    public ProductPreviewFragment productPreviewFragment;
    public int currentPage=1;

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
        productCategoryFragment=new ProductCategoryFragment();
        productInformationFragment=new ProductInformationFragment();
        productImageFragment=new ProductImageFragment();
        productPreviewFragment=new ProductPreviewFragment();
        pushScreen(productCategoryFragment);
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            productModel=(ProductModel) bundle.getSerializable("productModel");
        }
    }
    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_product);
        addProductVM=new AddProductVM(this);
        binding.setAddProductVM(addProductVM);
        binding.setProductModel(productModel);
    }

    public void pushScreen(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_layout, fragment)
                .commit();
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        confirmationAlert();
    }

    public void confirmationAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(this.getString(R.string.add_product_close_alert));
        alertDialogBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_CANCELED, intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
