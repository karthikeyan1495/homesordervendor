package com.homesordervendor.initialscreen.language;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.homesordervendor.BR;

/**
 * Created by innoppl on 13/03/18.
 */

public class LanguageModel extends BaseObservable {

    String name="";
    String key="";

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getKey() {
        return key;
    }

    @Bindable
    public void setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
    }
}
