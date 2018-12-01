package com.homesordervendor.orders.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesordervendor.product.productlist.model.ProductItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 3/7/18.
 */

public class OrderItem implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("orderID")
    @Expose
    private String orderID;

    @SerializedName("increment_id")
    @Expose
    private String increment_id;

    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;

    @SerializedName("orderDate")
    @Expose
    private String orderDate;

    @SerializedName("orderTotal")
    @Expose
    private String orderTotal;

    @SerializedName("payment_mode")
    @Expose
    private String payment_mode;

    @SerializedName("comments")
    @Expose
    private String comments;

    @SerializedName("currencycode")
    @Expose
    private String currencycode;

    @SerializedName("dueDate")
    @Expose
    private String dueDate;

    @SerializedName("amount_ordered")
    @Expose
    private String amount_ordered;

    @SerializedName("base_shipping_amount")
    @Expose
    private String base_shipping_amount;


    @SerializedName("base_amount_ordered")
    @Expose
    private String base_amount_ordered;

    @SerializedName("customer")
    @Expose
    private List<Customer> customer=new ArrayList<>();

    @SerializedName("items")
    @Expose
    private List<ProductItem> items=new ArrayList<>();

    @SerializedName("orderTotalInSAR")
    @Expose
    private String orderTotalInSAR;

    @SerializedName("amount_orderedInSAR")
    @Expose
    private String amount_orderedInSAR;

    @SerializedName("base_shipping_amountInSAR")
    @Expose
    private String base_shipping_amountInSAR;

    @SerializedName("base_amount_orderedInSAR")
    @Expose
    private String base_amount_orderedInSAR;

    @SerializedName("statusKey")
    @Expose
    private String statusKey;



    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAmount_ordered() {
        return amount_ordered;
    }

    public void setAmount_ordered(String amount_ordered) {
        this.amount_ordered = amount_ordered;
    }

    public String getBase_shipping_amount() {
        return base_shipping_amount;
    }

    public void setBase_shipping_amount(String base_shipping_amount) {
        this.base_shipping_amount = base_shipping_amount;
    }

    public String getBase_amount_ordered() {
        return base_amount_ordered;
    }

    public void setBase_amount_ordered(String base_amount_ordered) {
        this.base_amount_ordered = base_amount_ordered;
    }

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }

    public List<ProductItem> getItems() {
        return items;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }

    public String getOrderTotalInSAR() {
        return orderTotalInSAR;
    }

    public void setOrderTotalInSAR(String orderTotalInSAR) {
        this.orderTotalInSAR = orderTotalInSAR;
    }

    public String getAmount_orderedInSAR() {
        return amount_orderedInSAR;
    }

    public void setAmount_orderedInSAR(String amount_orderedInSAR) {
        this.amount_orderedInSAR = amount_orderedInSAR;
    }

    public String getBase_shipping_amountInSAR() {
        return base_shipping_amountInSAR;
    }

    public void setBase_shipping_amountInSAR(String base_shipping_amountInSAR) {
        this.base_shipping_amountInSAR = base_shipping_amountInSAR;
    }

    public String getBase_amount_orderedInSAR() {
        return base_amount_orderedInSAR;
    }

    public void setBase_amount_orderedInSAR(String base_amount_orderedInSAR) {
        this.base_amount_orderedInSAR = base_amount_orderedInSAR;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getIncrement_id() {
        return increment_id;
    }

    public void setIncrement_id(String increment_id) {
        this.increment_id = increment_id;
    }
}
