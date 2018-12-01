package com.homesordervendor.user.shippingarea.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.homesordervendor.R;
import com.homesordervendor.api.APICall;
import com.homesordervendor.api.APIConfiguration;
import com.homesordervendor.api.APIErrorHandler;
import com.homesordervendor.sharedpreferences.MySession;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.user.shippingarea.model.Area;
import com.homesordervendor.user.shippingarea.model.Country;
import com.homesordervendor.user.shippingarea.model.ShippingAreaResponse;
import com.homesordervendor.user.shippingarea.model.State;
import com.homesordervendor.util.InternetChecker;
import com.homesordervendor.util.MyProgressDialog;
import com.homesordervendor.util.MySnackBar;
import com.homesordervendor.util.StringUtil;
import com.homesordervendor.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/23/18.
 */

public class ShippingAreaVM extends java.util.Observable {

    Activity activity;
    MyProgressDialog myProgressDialog;
    List<Country> countries;
    List<State> tempState = new ArrayList();
    List<State> tempStateWithArea = new ArrayList();
    SearchFilter searchFilter;

    int countryPosition = -1;

    public ShippingAreaVM(Activity activity, SearchFilter searchFilter) {
        this.activity = activity;
        this.searchFilter = searchFilter;
        myProgressDialog = new MyProgressDialog();
        coverAreaAPICall();

    }

    public void onClickClose(View view) {
        Util.getInstance().hideKeyboard(activity);
        activity.finish();
    }

    public void onClickCountry(View view, Country country) {
        countryPicker();
    }

    public void onClickSave(View view, Country country) {
        validation(country);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count, Country country) {
        Log.w("tag", "onTextChanged " + s);
        filter(country, s.toString().toLowerCase());
    }

    void filter(Country country, String text) {
        for (int i = 0; i < tempState.size(); i++) {
            for (int j = 0; j < country.getStates().size(); j++) {
                if (MySession.getInstance(activity).getLanguageKey().equals(activity.getString(R.string.ar))) {
                    if (tempState.get(i).getStateNameAR().equals(country.getStates().get(j).getStateNameAR())) {
                        country.getStates().set(j, tempState.get(i));
                        break;
                    }
                } else {
                    if (tempState.get(i).getStateNameEN().equals(country.getStates().get(j).getStateNameEN())) {
                        country.getStates().set(j, tempState.get(i));
                        break;
                    }
                }
            }
        }
        tempState.clear();
        tempStateWithArea.clear();
        for (State state : country.getStates()) {
            boolean isAdded = false;
            if (MySession.getInstance(activity).getLanguageKey().equals(activity.getString(R.string.ar))) {
                if (state.getStateNameAR().toLowerCase().contains(text)) {
                    tempState.add(state);
                    tempStateWithArea.add(state);
                    isAdded = true;
                }
            } else {
                if (state.getStateNameEN().toLowerCase().contains(text)) {
                    tempState.add(state);
                    tempStateWithArea.add(state);
                    isAdded = true;
                }
            }

            if (!isAdded) {
                for (Area area : state.getAreas()) {
                    if (MySession.getInstance(activity).getLanguageKey().equals(activity.getString(R.string.ar))) {
                        if (area.getAreaNameAR().toLowerCase().contains(text)) {
                            tempState.add(state);

                            State localState=new State();
                            localState.getAreas().add(area);
                            localState.setStateNameEN(state.getStateNameEN());
                            localState.setStateNameAR(state.getStateNameAR());
                            localState.setStateID(state.getStateID());
                            localState.setStateCode(state.getStateCode());
                            localState.setSelected(state.isSelected());
                            localState.setPrice(state.getPrice());
                            localState.setExpended(state.isExpended());
                            localState.setCountryID(state.getCountryID());

                            //state.getAreas().clear();
                            //state.getAreas().add(area);

                            tempStateWithArea.add(localState);
                            break;
                        }
                    } else {
                        if (area.getAreaNameEN().toLowerCase().contains(text)) {
                            tempState.add(state);

                            State localState=new State();
                            localState.getAreas().add(area);
                            localState.setStateNameEN(state.getStateNameEN());
                            localState.setStateNameAR(state.getStateNameAR());
                            localState.setStateID(state.getStateID());
                            localState.setStateCode(state.getStateCode());
                            localState.setSelected(state.isSelected());
                            localState.setPrice(state.getPrice());
                            localState.setExpended(state.isExpended());
                            localState.setCountryID(state.getCountryID());

                            //state.getAreas().clear();
                            //state.getAreas().add(area);
                            tempStateWithArea.add(localState);
                            break;
                        }
                    }
                }
            }
        }
        if (searchFilter != null) {
            searchFilter.onSearchFilter(tempStateWithArea);
        }
    }


    private boolean isEmptyPrice(Country country) {
        for (State state : country.getStates()) {
            if (state.isSelected()) {
                for (Area area : state.getAreas()) {
                    if (area.isSelected() && area.getPrice().trim().length() == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isEmptySelection(Country country) {
        if (country != null) {
            for (State state : country.getStates()) {
                if (state.isSelected()) {
                    for (Area area : state.getAreas()) {
                        if (area.isSelected()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void validation(Country country) {
        if (isEmptySelection(country)) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_delivery_area));
        } else {
            if (isEmptyPrice(country)) {
                MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.please_enter_shipping_cost));
            } else {
                parsingValue();
            }
        }
    }

    private void parsingValue() {
        if (countries != null) {
            List<Country> tempCountryList = new ArrayList<>();
            for (Country country : countries) {
                if (country.isSelected()) {
                    List<State> tempStatesStateList = new ArrayList<>();
                    for (State state : country.getStates()) {
                        if (state.isSelected()) {
                            List<Area> tempAreasList = new ArrayList<>();
                            for (Area area : state.getAreas()) {
                                if (area.isSelected()) {
                            /*Area tempArea = new Area();
                            tempArea.setAreaCode(area.getAreaCode());
                            tempArea.setAreaID(area.getAreaID());
                            tempArea.setAreaNameAR(area.getAreaNameAR());
                            tempArea.setAreaNameEN(area.getAreaNameEN());
                            tempArea.setSelected(area.isSelected());
                            tempArea.setStateID(area.getStateID());
                            tempArea.setPrice(area.getPrice());*/
                                    tempAreasList.add(area);
                                }
                            }

                            if (tempAreasList.size() != 0) {
                        /*State tempState = new State();
                        tempState.setStateNameEN(state.getStateNameAR());
                        tempState.setStateNameAR(state.getStateNameAR());
                        tempState.setStateID(state.getStateID());
                        tempState.setStateCode(state.getStateCode());
                        tempState.setCountryID(state.getCountryID());
                        //tempState.setPrice(state.getPrice());
                        tempState.setSelected(state.isSelected());*/

                                state.setAreas(tempAreasList);
                                tempStatesStateList.add(state);

                            }
                        }
                    }
                    if (tempStatesStateList.size() != 0) {
                        Country tempCountry = new Country();
                        tempCountry.setCountryNameEN(country.getCountryNameEN());
                        tempCountry.setCountryNameAR(country.getCountryNameAR());
                        tempCountry.setCountryID(country.getCountryID());
                        tempCountry.setCountryCode(country.getCountryCode());
                        tempCountry.setSelected(country.isSelected());
                        tempCountry.setStates(tempStatesStateList);
                        tempCountryList.add(tempCountry);
                    }
                }
            }

            updateCoverAreaAPICall(tempCountryList);
        }
    }

    private void countryPicker() {
        if (countries != null) {
            CharSequence[] items = new CharSequence[countries.size()];
            for (int i = 0; i < countries.size(); i++) {
                items[i] = StringUtil.getLanguageName(countries.get(i).getCountryNameEN(), countries.get(i).getCountryNameAR());
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    saveCounty(item);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void saveCounty(int position) {

        if (countries != null && position < countries.size()) {
            countryPosition = position;
            /*for (int i = 0; i < countries.size(); i++) {
                if (position == i) {
                    countries.get(i).setSelected(true);
                } else {
                    countries.get(i).setSelected(false);
                }
            }*/

            countries.get(position).setSelected(true);

            setChanged();
            notifyObservers();
        }

    }

    private void updateCoverAreaAPICall(List<Country> countries) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<ShippingAreaResponse>> observable = api.coverAreaUpdate(MySession.getInstance(activity).getUser().getToken(), countries);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            User user = MySession.getInstance(activity).getUser();
                            user.getProfile().setIsdeliverycost("true");
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

    private void coverAreaAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<ShippingAreaResponse>> observable = api.coverArea(MySession.getInstance(activity).getUser().getToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            countries = responses.body().getCoveredarea();
                            parseCurrencyDisplayPrice();
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

    private void parseCurrencyDisplayPrice() {
        for (Country country : countries) {
            for (State state : country.getStates()) {
                for (Area area : state.getAreas()) {
                    if (MySession.getInstance(activity).getCurrency().equals(activity.getString(R
                            .string.sar))) {
                        area.setPrice(area.getPriceInSAR());
                    }
                }
            }
        }
    }

    public List<Country> getCountryList() {
        return countries;
    }

    public int getCountryPosition() {
        return countryPosition;
    }


    public interface SearchFilter {
        public void onSearchFilter(List<State> states);
    }
}
