package com.homesordervendor.product.addproduct.viewmodel;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.product.addproduct.AddProductActivity;
import com.homesordervendor.product.addproduct.ProductInformationFragment;
import com.homesordervendor.product.addproduct.ShippingAreaDialogFragment;
import com.homesordervendor.product.addproduct.model.ProductModel;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.shippingarea.model.Area;
import com.homesordervendor.user.shippingarea.model.Country;
import com.homesordervendor.user.shippingarea.model.ShippingAreaResponse;
import com.homesordervendor.user.shippingarea.model.State;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/3/18.
 */

public class ProductInformationVM {

    Activity activity;
    ProductInformationFragment productInformationFragment;
    MyProgressDialog myProgressDialog;
    ProductModel productModel;

    //List<State> originalStates = new ArrayList<>();

    List<Country> countries=new ArrayList<>();

    public ProductInformationVM(Activity activity, ProductInformationFragment productInformationFragment, ProductModel productModel) {
        this.activity = activity;
        this.productInformationFragment = productInformationFragment;
        myProgressDialog = new MyProgressDialog();
        this.productModel = productModel;
        if (productModel.getCountries().size() == 0) {
            coverdAreaAPICall();
        } else {
            countries=new ArrayList<>(productModel.getCountries());
            //originalStates.addAll(productModel.getStateList());
        }
    }

    public void onClickCoverageArea(View view) {
        if (activity instanceof AddProductActivity) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
                productModel.setCountries(countries);
            } else {
                checkBox.setChecked(true);
                AddProductActivity addProductActivity = (AddProductActivity) activity;
                ShippingAreaDialogFragment areaDialogFragment = new ShippingAreaDialogFragment();
                //areaDialogFragment.setStates(productModel.getStateList());
                areaDialogFragment.setCountries(productModel.getCountries());
                areaDialogFragment.setOnCloseListener(countryList -> {
                    productModel.setCountries(countryList);
                });
                areaDialogFragment.show(addProductActivity.getSupportFragmentManager(), "dialog");
            }
        }
    }

    private void coverdAreaAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<ShippingAreaResponse>> observable = api.coverArea(MySession.getInstance(activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            //productModel.setCountries(responses.body().getCoveredarea());
                            countryParsing(responses.body().getCoveredarea());
                            productModel.setAreaAPICall(true);
                            //setChanged();
                            //notifyObservers();
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
                        }
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));

                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }


    private void countryParsing(List<Country> apiCountryList) {

       // int countrySelectedPosition = 0;

        List<Country> tempCountries=new ArrayList<>();
        tempCountries.addAll(apiCountryList);

        for (Country country:tempCountries) {
            if (country.isSelected()) {
                List<State> tempStates=new ArrayList<>();
                for (State state:country.getStates()) {
                    if (state.isSelected()) {
                        List<Area> tempAreas=new ArrayList<>();
                        for (Area area:state.getAreas()){
                            if (area.isSelected()){
                                if (MySession.getInstance(activity).getCurrency().equals(activity.getString(R
                                        .string.sar))){
                                    area.setPrice(area.getPriceInSAR());
                                }
                                tempAreas.add(area);
                            }
                        }

                        if (tempAreas.size()!=0){
                            state.setAreas(tempAreas);
                            tempStates.add(state);
                        }

                    }
                }
                if (tempStates.size()!=0){
                    country.setStates(tempStates);
                    productModel.getCountries().add(country);
                    countries.add(country);
                }
                //countrySelectedPosition = i;
                //break;
            }
        }

        /*List<State> tempState = new ArrayList<>();
        tempState.addAll(productModel.getCountries().get(countrySelectedPosition).getStates());
        for (int i = 0; i < tempState.size(); i++) {
            State state = tempState.get(i);
            if (state.isSelected()) {
                List<Area> tempArea = new ArrayList<>();
                for (int j = 0; j < state.getAreas().size(); j++) {
                    Area area = state.getAreas().get(j);
                    if (area.isSelected()) {
                        if (MySession.getInstance(activity).getCurrency().equals(activity.getString(R
                                .string.sar))){
                            area.setPrice(area.getPriceInSAR());
                        }
                        tempArea.add(area);
                    }
                }
                if (tempArea.size() != 0) {
                    state.setAreas(tempArea);
                    productModel.getStateList().add(state);
                }
            }
        }
        originalStates=new ArrayList<>(productModel.getStateList());
        //originalStates.addAll(productModel.getStateList());*/
    }
}
