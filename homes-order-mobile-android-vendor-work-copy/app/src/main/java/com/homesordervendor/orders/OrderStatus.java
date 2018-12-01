package com.homesordervendor.orders;

/**
 * Created by mac on 3/7/18.
 */

public enum OrderStatus {
    
    NEW("0"),
    PROCESSING("1"),
    DISPATCH("2"),
    COMPLETE("3"),
    DELIVERY("3"),
    CANCELED("4"),
    DELIVERY_RETURN("5"),
    RETURN("6");

    private final String id;
    OrderStatus(String id) { this.id = id; }
    public String getValue() { return id; }

}
