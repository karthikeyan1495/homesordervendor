package com.homesordervendor.util;

import android.content.pm.PackageManager;

import com.homesordervendor.MyApp;
import com.homesordervendor.R;
import com.homesordervendor.orders.OrderStatus;
import com.homesordervendor.product.addproduct.model.ProductModel;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.login.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 2/21/18.
 */

public class StringUtil {

    public static String getFirstLatter(String text) {

        if (text.trim().length() > 1) {
            return text.substring(0, 2).toUpperCase();
        } else {
            return text.trim();
        }
    }

    public static String versionName() {
        try {
            String versionName = MyApp.getContext().getPackageManager()
                    .getPackageInfo(MyApp.getContext().getPackageName(), 0).versionName;
            String text = String.format(MyApp.getContext().getString(R.string.version), versionName);
            return text;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String welcomeWithName(String name) {
        String text = String.format(MyApp.getContext().getString(R.string.hi), name);
        return text;
    }

    public static boolean accountComplitionStatus(User user) {
        if (user.getProfile().getIsaddress().trim().toLowerCase().equals("true") &&
                user.getProfile().getIsdeliveryslot().trim().toLowerCase().equals("true") &&
                user.getProfile().getIsdeliverycost().trim().toLowerCase().equals("true") &&
                user.getProfile().getIssubscriped().trim().toLowerCase().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCheck(String string) {
        if (string.trim().toLowerCase().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean showSubscribeButton() {
        User user = MySession.getInstance(MyApp.getContext()).getUser();
        if (user.getProfile().getIssubscriped().trim().toLowerCase().equals("true")) {
            return false;
        } else {
            return true;
        }
    }

    public static String priceAndMonths(String months) {
        String text = getCurrency()+" "+String.format(MyApp.getContext().getResources().getString
                        (R.string.months),
                months);
        return text;
    }

    public static String getLanguageName(String en, String ar) {
        if (MySession.getInstance(MyApp.getContext()).getLanguageKey().equals(MyApp.getContext().getString(R.string.ar))) {
            if (ar == null || ar.trim().length() == 0) {
                return en;
            } else {
                return ar;
            }
        } else {
            return en;
        }
    }

    public static boolean showProductDetailEditView(ProductModel productModel) {
        if (productModel.isFood() && productModel.isFoodSubCategory()) {
            return true;
        } else if (productModel.isFashion() && productModel.isTargetGroup() && productModel.isTargetGroupSubCategory()) {
            return true;
        }
        return false;
    }

    public static String categoryParsing(ProductModel productModel) {
        String string = MyApp.getContext().getString(R.string.category);
        if (productModel.isFood()) {
            string += " " + productModel.getSubCategoryName();
        } else if (productModel.isFashion()) {
            string += " " + productModel.getTargetGroupName() + " - " + productModel.getTargetGroupSubCategoryName();
        }

        return string;

    }

    public static String promotionCategoryParsing(ProductItem productItem) {
        String string = "";
        if (MySession.getInstance(MyApp.getContext()).getLanguageKey().equals(MyApp.getContext().getString(R.string.ar))) {
            if (productItem.getMainCategorynameAR().size() != 0) {
                string += productItem.getMainCategorynameAR().get(0);
                if (productItem.getTargetedGroupNameAR().size() != 0) {
                    string += " - " + productItem.getTargetedGroupNameAR().get(0);
                    if (productItem.getSubCategoryNameAR().size() != 0) {
                        string += " - " + productItem.getSubCategoryNameAR().get(0);
                    }
                } else if (productItem.getSubCategoryNameAR().size() != 0) {
                    string += " - " + productItem.getSubCategoryNameAR().get(0);

                }
            }
        } else {
            if (productItem.getMainCategorynameEN().size() != 0) {
                string += productItem.getMainCategorynameEN().get(0);
                if (productItem.getTargetedGroupNameEN().size() != 0) {
                    string += " - " + productItem.getTargetedGroupNameEN().get(0);
                    if (productItem.getSubCategoryNameEn().size() != 0) {
                        string += " - " + productItem.getSubCategoryNameEn().get(0);
                    }
                } else if (productItem.getSubCategoryNameEn().size() != 0) {
                    string += " - " + productItem.getSubCategoryNameEn().get(0);
                }
            }
        }

        return string;

    }

    public static String initToString(int value) {
        return String.valueOf(value);
    }


    public static String getMonthName(String fromDate, String toDate) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(fromDate));

            SimpleDateFormat month_date = new SimpleDateFormat("MMM");
            String from_month_name = month_date.format(cal.getTime());

            cal.setTime(sdf.parse(toDate));
            String to_month_name = month_date.format(cal.getTime());

            if (from_month_name.equals(to_month_name)) {
                return from_month_name;
            } else {
                return from_month_name + " - " + to_month_name;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String getDateName(String fromDate, String toDate) {

        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(fromDate));

            SimpleDateFormat month_date = new SimpleDateFormat("EEE");
            String from_date_name = month_date.format(cal.getTime());

            cal.setTime(sdf.parse(toDate));
            String to_date_name = month_date.format(cal.getTime());

            if (fromDate.equals(toDate)) {
                return from_date_name;
            } else {
                return from_date_name + " - " + to_date_name;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String getDataFormat(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(date));
            return sdf.format(cal.getTime());
        } catch (Exception e) {
        }
        return "";
    }

    public static String percentageCalculation(String originalPrice, String discountPrice) {
        try {
            float orgPrice = Float.valueOf(originalPrice);
            float dicPrice = Float.valueOf(discountPrice);
            float discount = orgPrice - dicPrice;
            discount = discount / orgPrice;
            discount = discount * 100;
            return String.valueOf(String.format("%.2f", discount)) + "%";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDate(String fromDate, String toDate) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(fromDate));

            SimpleDateFormat month_date = new SimpleDateFormat("dd");
            String from_date_name = month_date.format(cal.getTime());

            cal.setTime(sdf.parse(toDate));
            String to_date_name = month_date.format(cal.getTime());

            if (fromDate.equals(toDate)) {
                return from_date_name;
            } else {
                return from_date_name + " - " + to_date_name;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String expireMessage(String from, String to) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date currentDate = Calendar.getInstance().getTime();
            Date fromDate = dateFormat.parse(from);
            Date toDate = dateFormat.parse(to);
            long diffInMillisec = currentDate.getTime() - fromDate.getTime();
            if (diffInMillisec < 0) {
                return String.format(MyApp.getContext().getResources().getString(R.string.start_on),
                        dateFormat.format(fromDate));
            } else {
                diffInMillisec = toDate.getTime() - currentDate.getTime();
                if (diffInMillisec < 0) {
                    return MyApp.getContext().getString(R.string.expired);
                } else {
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                    if (diffInDays == 0) {
                        long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillisec);
                        if (diffInHours == 0) {
                            long diffInMin = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec);
                            if (diffInMin == 0) {
                                long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
                                if (diffInSec == 0) {
                                    return MyApp.getContext().getString(R.string.expired);
                                } else {
                                    return String.format(MyApp.getContext().getResources().getString(R.string
                                            .expires_in_seconds), String.valueOf(diffInMin));
                                }
                            } else {
                                return String.format(MyApp.getContext().getResources().getString(R.string
                                        .expires_in_minutes), String.valueOf(diffInMin));
                            }
                        } else {
                            return String.format(MyApp.getContext().getResources().getString(R.string
                                    .expires_in_hours), String.valueOf(diffInHours));
                        }
                    } else {
                        return String.format(MyApp.getContext().getResources().getString(R.string
                                .expires_in_days), String.valueOf(diffInDays));
                    }
                }
            }


        } catch (Exception e) {

        }

        return "";
    }

    public static int getDay(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (date != null && date.trim().length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                calendar.setTime(sdf.parse(date));
                return calendar.get(Calendar.DATE);
            }
        } catch (Exception e) {
        }
        return calendar.get(Calendar.DATE);
    }

    public static int getMonth(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (date != null && date.trim().length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                calendar.setTime(sdf.parse(date));
                return calendar.get(Calendar.MONTH);
            }
        } catch (Exception e) {
        }
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (date != null && date.trim().length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                calendar.setTime(sdf.parse(date));
                return calendar.get(Calendar.YEAR);
            }
        } catch (Exception e) {
        }
        return calendar.get(Calendar.YEAR)-13;
    }

    public static long getDOBMaxDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,-13);
        return calendar.getTimeInMillis();
    }

    public static long getPromotionMaxDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,3);
        return calendar.getTimeInMillis();
    }

    public static long getFeaturedMaxDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,3);
        return calendar.getTimeInMillis();
    }



    public static long getDateLong(String date){
        Calendar calendar = Calendar.getInstance();
        try {
            if (date != null && date.trim().length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                calendar.setTime(sdf.parse(date));
                return calendar.getTime().getTime();
            }
        } catch (Exception e) {
        }
        return calendar.getTime().getTime();
    }

    public static boolean shopMoreOption(OrderStatus orderStatus) {
        if (orderStatus!=null) {
            if (orderStatus == OrderStatus.DELIVERY || orderStatus == OrderStatus.CANCELED ||
                    orderStatus == OrderStatus.COMPLETE) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public static boolean isCompleted(OrderStatus orderStatus){
        if (orderStatus!=null) {
            if (orderStatus == OrderStatus.DELIVERY ||
                    orderStatus == OrderStatus.COMPLETE) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public static boolean isCancelled(OrderStatus orderStatus){
        if (orderStatus!=null) {
            if (orderStatus == OrderStatus.CANCELED) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean isCompleted(String orderStatus){
        if (orderStatus!=null) {
            if (orderStatus.equals(OrderStatus.DELIVERY.getValue()) ||
                    orderStatus.equals(OrderStatus.COMPLETE.getValue())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean isCancelled(String orderStatus){
        if (orderStatus!=null) {
            if (orderStatus.equals(OrderStatus.CANCELED.getValue())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean shopMoreOption(String orderStatus) {
        if (orderStatus!=null) {
            if (orderStatus.equals(OrderStatus.DELIVERY.getValue()) || orderStatus.equals(OrderStatus
                    .CANCELED.getValue()) || orderStatus.equals(OrderStatus.COMPLETE.getValue())) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }



    public static boolean showHolidayPlannerDeleteButton(String status) {
        if (status.toLowerCase().trim().equals("active")) {
            return true;
        } else {
            return false;
        }
    }

    public static String holidayPlannerButtonText(String status) {
        if (status == null || status.equals("")) {
            return MyApp.getContext().getString(R.string.add_holiday);
        } else {
            return MyApp.getContext().getString(R.string.update_holiday);
        }
    }

    public static String getDataFromArray(List<String> list) {
        if (list != null && list.size() != 0) {
            return list.get(0);
        }
        return "";
    }

    public static String getProductAddPageTitle(String productID)
    {
        if (productID==null||productID.trim().length()==0){
            return MyApp.getContext().getString(R.string.add_new_product);
        }else {
            return MyApp.getContext().getString(R.string.edit_product);
        }
    }

    public static String setPrice(String aedPrice,String sarPrice){
        String currency=MySession.getInstance(MyApp.getContext()).getCurrency();
        if (currency.equals(MyApp.getContext().getString(R.string.aed))){
            return aedPrice+" "+currency;
        }else {
            return sarPrice+" "+currency;
        }
    }

    public static String setPrice(String aedPrice,String sarPrice,String qty){
        try {
            String currency=MySession.getInstance(MyApp.getContext()).getCurrency();
            if (currency.equals(MyApp.getContext().getString(R.string.aed))){
                return (Float.valueOf(aedPrice)*Float.valueOf(qty))+" "+currency;
            }else {
                return (Float.valueOf(sarPrice)*Float.valueOf(qty))+" "+currency;
            }
        }catch (Exception e){
            String currency=MySession.getInstance(MyApp.getContext()).getCurrency();
            if (currency.equals(MyApp.getContext().getString(R.string.aed))){
                return aedPrice+" "+currency;
            }else {
                return sarPrice+" "+currency;
            }
        }
    }

    public static String getCurrency(){
        return MySession.getInstance(MyApp.getContext()).getCurrency();
    }

    public static String setFeaturedPrice(){
        return "0.00 "+MySession.getInstance(MyApp.getContext()).getCurrency();
    }

    public static String setPrice(String price){
        return price+" "+MySession.getInstance(MyApp.getContext()).getCurrency();
    }

    public static boolean showProductStatusView(String statusKey){
        if (statusKey!=null&&statusKey.trim().equals("1")){
            return false;
        }else {
            return true;
        }
    }

    public static String getSlotName(String delivery_slot) {
        if (delivery_slot.trim().toLowerCase().equals("m")) {
            return MyApp.getContext().getString(R.string.morning);
        } else if (delivery_slot.trim().toLowerCase().equals("a")) {
            return MyApp.getContext().getString(R.string.afternoon);
        } else {
            return MyApp.getContext().getString(R.string.evening);
        }
    }

    public static String orderIDFormat(String orderId) {
        return "#"+orderId;
    }
}
