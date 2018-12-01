package com.homesordervendor.orders;

/**
 * Created by innoppl on 22/05/18.
 */

public enum NavigationFromEnum {
    ORDER_LIST("order_list");
    private String value;
    public String getValue() {
        return value;
    }
    NavigationFromEnum(String value) {
        this.value = value;
    }
}
