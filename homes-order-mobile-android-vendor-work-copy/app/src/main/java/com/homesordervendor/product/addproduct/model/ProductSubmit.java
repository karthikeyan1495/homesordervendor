package com.homesordervendor.product.addproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 3/4/18.
 */

public class ProductSubmit {


    @SerializedName("productID")
    @Expose
    private String productID;

    @SerializedName("productname")
    @Expose
    private String productname;

    @SerializedName("productname_arabic")
    @Expose
    private String productname_arabic;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("description_arabic")
    @Expose
    private String description_arabic;

    @SerializedName("orderlimitperday")
    @Expose
    private String orderlimitperday;

    @SerializedName("handlingtime")
    @Expose
    private String handlingtime;

    @SerializedName("deliveryCost")
    @Expose
    private List<DeliveryCost> deliveryCost;

    @SerializedName("weight")
    @Expose
    private List<String> weight;

    @SerializedName("color")
    @Expose
    private List<String> color;


    @SerializedName("size")
    @Expose
    private List<String> size;


    @SerializedName("category")
    @Expose
    private List<String> category;

    @SerializedName("images")
    @Expose
    private List<String> images;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductname_arabic() {
        return productname_arabic;
    }

    public void setProductname_arabic(String productname_arabic) {
        this.productname_arabic = productname_arabic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_arabic() {
        return description_arabic;
    }

    public void setDescription_arabic(String description_arabic) {
        this.description_arabic = description_arabic;
    }

    public String getOrderlimitperday() {
        return orderlimitperday;
    }

    public void setOrderlimitperday(String orderlimitperday) {
        this.orderlimitperday = orderlimitperday;
    }

    public String getHandlingtime() {
        return handlingtime;
    }

    public void setHandlingtime(String handlingtime) {
        this.handlingtime = handlingtime;
    }

    public List<DeliveryCost> getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(List<DeliveryCost> deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public List<String> getWeight() {
        return weight;
    }

    public void setWeight(List<String> weight) {
        this.weight = weight;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
