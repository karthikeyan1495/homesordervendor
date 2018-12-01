package com.homesordervendor.user.deliveryslot.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.api.GeneralResponse;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.deliveryslot.model.DeliverySlot;
import com.homesordervendor.user.deliveryslot.model.DeliverySlotResponse;
import com.homesordervendor.user.deliveryslot.model.Slots;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/22/18.
 */

public class DeliverySlotVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    DeliverySlot deliverySlot;
    List<Slots> list=new ArrayList<>();

    public DeliverySlotVM(Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
        deliverySlotAPICall();
    }

    public void onClickClose(View view){
        Util.getInstance().hideKeyboard(activity);
        activity.finish();
    }

    public void onClickSave(View view){
        if (validation()){
            deliverySlotUpdateAPICall(deliverySlot());
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_delivery_slot));
        }
    }


    public boolean validation(){
        DeliverySlot deliverySlot=deliverySlot();
        if (deliverySlot.getMonday().trim().length()!=0){
            return true;
        }else if(deliverySlot.getTuesday().trim().length()!=0){
            return true;
        }else if(deliverySlot.getWednesday().trim().length()!=0){
            return true;
        }else if(deliverySlot.getThursday().trim().length()!=0){
            return true;
        }else if(deliverySlot.getFriday().trim().length()!=0){
            return true;
        }else if(deliverySlot.getSaturday().trim().length()!=0){
            return true;
        }else if(deliverySlot.getSunday().trim().length()!=0){
            return true;
        }
        return false;
    }

    private DeliverySlot deliverySlot(){
        DeliverySlot deliverySlot=new DeliverySlot();

        String sunday="";
        String monday="";
        String tuesday="";
        String wednesday="";
        String thursday="";
        String friday="";
        String saturday="";

        for (Slots slot:list) {
            if (slot.getDay()==activity.getString(R.string.sunday)) {
                if (slot.isMorning()){
                    sunday=sunday+"M,";
                }
                if (slot.isNoon()){
                    sunday=sunday+"A,";
                }
                if (slot.isEvening()){
                    sunday=sunday+"E,";
                }
            }else if (slot.getDay()==activity.getString(R.string.monday)) {
                if (slot.isMorning()){
                    monday=monday+"M,";
                }
                if (slot.isNoon()){
                    monday=monday+"A,";
                }
                if (slot.isEvening()){
                    monday=monday+"E,";
                }
            }else if (slot.getDay()==activity.getString(R.string.tuesday)) {
                if (slot.isMorning()){
                    tuesday=tuesday+"M,";
                }
                if (slot.isNoon()){
                    tuesday=tuesday+"A,";
                }
                if (slot.isEvening()){
                    tuesday=tuesday+"E,";
                }
            }else if (slot.getDay()==activity.getString(R.string.wednesday)) {
                if (slot.isMorning()){
                    wednesday=wednesday+"M,";
                }
                if (slot.isNoon()){
                    wednesday=wednesday+"A,";
                }
                if (slot.isEvening()){
                    wednesday=wednesday+"E,";
                }
            }else if (slot.getDay()==activity.getString(R.string.thursday)) {
                if (slot.isMorning()){
                    thursday=thursday+"M,";
                }
                if (slot.isNoon()){
                    thursday=thursday+"A,";
                }
                if (slot.isEvening()){
                    thursday=thursday+"E,";
                }
            }else if (slot.getDay()==activity.getString(R.string.friday)) {
                if (slot.isMorning()){
                    friday=friday+"M,";
                }
                if (slot.isNoon()){
                    friday=friday+"A,";
                }
                if (slot.isEvening()){
                    friday=friday+"E,";
                }
            }else if (slot.getDay()==activity.getString(R.string.saturday)) {
                if (slot.isMorning()){
                    saturday=saturday+"M,";
                }
                if (slot.isNoon()){
                    saturday=saturday+"A,";
                }
                if (slot.isEvening()){
                    saturday=saturday+"E,";
                }
            }
        }


        if (sunday.trim().length()!=0){
            sunday=sunday.substring(0,sunday.length()-1);
        }

        if (monday.trim().length()!=0){
            monday=monday.substring(0,monday.length()-1);
        }

        if (tuesday.trim().length()!=0){
            tuesday=tuesday.substring(0,tuesday.length()-1);
        }

        if (wednesday.trim().length()!=0){
            wednesday=wednesday.substring(0,wednesday.length()-1);
        }

        if (thursday.trim().length()!=0){
            thursday=thursday.substring(0,thursday.length()-1);
        }

        if (friday.trim().length()!=0){
            friday=friday.substring(0,friday.length()-1);
        }

        if (saturday.trim().length()!=0){
            saturday=saturday.substring(0,saturday.length()-1);
        }

        deliverySlot.setSunday(sunday);
        deliverySlot.setMonday(monday);
        deliverySlot.setTuesday(tuesday);
        deliverySlot.setWednesday(wednesday);
        deliverySlot.setThursday(thursday);
        deliverySlot.setFriday(friday);
        deliverySlot.setSaturday(saturday);

        return deliverySlot;

    }

    public List<Slots> getDeliverySlotList(){

        Slots slots=new Slots();
        slots.setDay(activity.getString(R.string.sunday));
        String[] day=deliverySlot.getSunday().toLowerCase().split(",");
        for (int i=0;i<day.length;i++){
            switch (day[i])
            {
                case "m":
                    slots.setMorning(true);
                    break;
                case "a":
                    slots.setNoon(true);
                    break;
                case "e":
                    slots.setEvening(true);
                    break;
            }
        }
        list.add(slots);

        slots=new Slots();
        slots.setDay(activity.getString(R.string.monday));
        day=deliverySlot.getMonday().toLowerCase().split(",");
        for (int i=0;i<day.length;i++){
            switch (day[i])
            {
                case "m":
                    slots.setMorning(true);
                    break;
                case "a":
                    slots.setNoon(true);
                    break;
                case "e":
                    slots.setEvening(true);
                    break;
            }
        }
        list.add(slots);

        slots=new Slots();
        slots.setDay(activity.getString(R.string.tuesday));
        day=deliverySlot.getTuesday().toLowerCase().split(",");
        for (int i=0;i<day.length;i++){
            switch (day[i])
            {
                case "m":
                    slots.setMorning(true);
                    break;
                case "a":
                    slots.setNoon(true);
                    break;
                case "e":
                    slots.setEvening(true);
                    break;
            }
        }
        list.add(slots);

        slots=new Slots();
        slots.setDay(activity.getString(R.string.wednesday));
        day=deliverySlot.getWednesday().toLowerCase().split(",");
        for (int i=0;i<day.length;i++){
            switch (day[i])
            {
                case "m":
                    slots.setMorning(true);
                    break;
                case "a":
                    slots.setNoon(true);
                    break;
                case "e":
                    slots.setEvening(true);
                    break;
            }
        }
        list.add(slots);

        slots=new Slots();
        slots.setDay(activity.getString(R.string.thursday));
        day=deliverySlot.getThursday().toLowerCase().split(",");
        for (int i=0;i<day.length;i++){
            switch (day[i])
            {
                case "m":
                    slots.setMorning(true);
                    break;
                case "a":
                    slots.setNoon(true);
                    break;
                case "e":
                    slots.setEvening(true);
                    break;
            }
        }
        list.add(slots);

        slots=new Slots();
        slots.setDay(activity.getString(R.string.friday));
        day=deliverySlot.getFriday().toLowerCase().split(",");
        for (int i=0;i<day.length;i++){
            switch (day[i])
            {
                case "m":
                    slots.setMorning(true);
                    break;
                case "a":
                    slots.setNoon(true);
                    break;
                case "e":
                    slots.setEvening(true);
                    break;
            }
        }
        list.add(slots);

        slots=new Slots();
        slots.setDay(activity.getString(R.string.saturday));
        day=deliverySlot.getSaturday().toLowerCase().split(",");
        for (int i=0;i<day.length;i++){
            switch (day[i])
            {
                case "m":
                    slots.setMorning(true);
                    break;
                case "a":
                    slots.setNoon(true);
                    break;
                case "e":
                    slots.setEvening(true);
                    break;
            }
        }
        list.add(slots);

        return list;
    }

    private void deliverySlotAPICall(){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<DeliverySlotResponse>> observable = api.deliverySlotInformation(MySession.getInstance(activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            deliverySlot=responses.body().getDeliverySlots();
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

    private void deliverySlotUpdateAPICall(DeliverySlot deliverySlot){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.deliverySlotUpdate(MySession.getInstance(activity).getUser().getToken(),deliverySlot);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            User user=MySession.getInstance(activity).getUser();
                            user.getProfile().setIsdeliveryslot("true");
                            MySession.getInstance(activity).saveUser(user);
                            activity.finish();
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
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
