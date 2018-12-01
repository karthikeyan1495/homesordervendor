package com.homesordervendor.product.promotion.viewmodel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.databinding.ActivityPromotionBinding;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.product.promotion.model.ProductID;
import com.homesordervendor.product.promotion.model.Promotion;
import com.homesordervendor.product.promotion.model.PromotionResponse;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.StringUtil;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/5/18.
 */

public class PromotionVM extends java.util.Observable {

    Activity activity;
    int years, dates, monthes;
    MyProgressDialog myProgressDialog;
    Promotion promotionModel;
    ProductItem productItem;
    ActivityPromotionBinding binding;

    public PromotionVM(Activity activity, ProductItem productItem, ActivityPromotionBinding binding) {
        this.activity = activity;
        myProgressDialog = new MyProgressDialog();
        this.productItem = productItem;
        this.binding = binding;
        getPromotionAPICall();
    }

    public void onClickClose(View view) {
        Intent intent = new Intent();
        activity.setResult(Activity.RESULT_CANCELED, intent);
        activity.finish();
    }

    public void onClickFromDate(View view, Promotion promotion) {
        datePicker(promotion, true);

    }

    public void onClickToDate(View view, Promotion promotion) {
        datePicker(promotion, false);
    }

    public void onClickToSetPromotion(View view, Promotion promotion) {

        if (promotion.getDiscountPrice() == null || promotion.getDiscountPrice().trim().length() == 0) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_discount_price));
        } else if (promotion.getFromDate() == null || promotion.getFromDate().trim().length() == 0) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_from_date));
        } else if (promotion.getToDate() == null || promotion.getToDate().trim().length() == 0) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.error_to_date));
        } else {
            setPromotionAPICall(promotion);
        }
    }

    public void onDiscountPriceTextChanged(CharSequence s, int start, int before, int
            count,
                                           Promotion
                                                   promotion) {
        if (s.toString().trim().length() != 0) {
            try {
                float discountPrice = Float.valueOf(s.toString().trim());
                float actualPrice;

                if (MySession.getInstance(activity).getCurrency().equals(activity.getString(R
                        .string.aed))) {
                    actualPrice = Float.valueOf(promotion.getPrice());
                } else {
                    actualPrice = Float.valueOf(promotion.getPriceInSAR());
                }

                if (discountPrice < actualPrice) {
                    promotion.setDiscount(StringUtil.percentageCalculation(String.valueOf(actualPrice), s.toString()));
                } else {
                    String price = s.toString().trim();
                    binding.discountPrice.setText(price.substring(0, price.length() - 1));
                    binding.discountPrice.setSelection(binding.discountPrice.getText().length());
                    MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.valid_discount_price));
                }
            } catch (Exception e) {
            }
        }
    }

    private void datePicker(Promotion promotion, boolean isFrom) {
        String date;
        if (isFrom) {
            date = promotion.getFromDate();
        } else {
            date = promotion.getToDate();
        }
        dates = StringUtil.getDay(date);
        monthes = StringUtil.getMonth(date);
        years = StringUtil.getYear(date);
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                years = year;
                monthes = month + 1;
                dates = date;
                String dateString = String.valueOf(years) + "-";
                if (monthes <= 9) {
                    dateString += "0" + String.valueOf(monthes) + "-";
                } else {
                    dateString += String.valueOf(monthes) + "-";
                }
                if (dates <= 9) {
                    dateString += "0" + String.valueOf(dates);
                } else {
                    dateString += String.valueOf(dates);
                }
                if (isFrom) {
                    promotion.setFromDate(dateString);
                } else {
                    promotion.setToDate(dateString);
                }
            }
        }, years, monthes, dates);
        if (isFrom) {
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.getDatePicker().setMaxDate(StringUtil.getPromotionMaxDate());
        } else {
            datePickerDialog.getDatePicker().setMinDate(StringUtil.getDateLong(promotion.getFromDate()));
            datePickerDialog.getDatePicker().setMaxDate(StringUtil.getPromotionMaxDate());

        }
        datePickerDialog.show();
    }

    public Promotion getPromotionModel() {
        return promotionModel;
    }


    private void getPromotionAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            ProductID productID = new ProductID();
            productID.setProductID(productItem.getProductID());
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<PromotionResponse>> observable = api.promotionDetail(MySession.getInstance(activity).getUser().getToken(), productID);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            promotionModel = responses.body().getPromotion();
                            setChanged();
                            notifyObservers();
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


    private void setPromotionAPICall(Promotion promotion) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            promotion.setProductID(productItem.getProductID());
            promotion.setFromDate(StringUtil.getDataFormat(promotion.getFromDate()) + " 00:00:00");
            promotion.setToDate(StringUtil.getDataFormat(promotion.getToDate()) + " 00:00:00");
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.setPromotion(MySession.getInstance(activity).getUser().getToken(), promotion);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {

                            Intent intent = new Intent();
                            activity.setResult(Activity.RESULT_OK, intent);
                            activity.finish();
                            Toast.makeText(activity, responses.body().getMessage(), Toast.LENGTH_LONG).show();
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            /* APIErrorHandler.getInstance().errorHandler(activity, responses.code
                                    (), responses.body().getMessage());*/

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
}
