package com.homesordervendor.product.productlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 3/4/18.
 */

public class ProductItem implements Serializable {

    @SerializedName("productID")
    @Expose
    private String productID;

    @SerializedName("itemID")
    @Expose
    private String itemID;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("productNameEN")
    @Expose
    private String productNameEN;

    @SerializedName("productNameAR")
    @Expose
    private String productNameAR;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("priceInSAR")
    @Expose
    private String priceInSAR;

    @SerializedName("perDayOrderLimit")
    @Expose
    private String perDayOrderLimit;

    @SerializedName("handlingTime")
    @Expose
    private String handlingTime;

    @SerializedName("descriptionEN")
    @Expose
    private String descriptionEN;

    @SerializedName("descriptionAR")
    @Expose
    private String descriptionAR;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("deliveryCost")
    @Expose
    private List<DeliveryCostItem> deliveryCost=new ArrayList<>();

    @SerializedName("review")
    @Expose
    private List<ReviewItem> review;

    @SerializedName("mainCategorynameEN")
    @Expose
    private List<String> mainCategorynameEN;

    @SerializedName("mainCategorynameAR")
    @Expose
    private List<String> mainCategorynameAR;

    @SerializedName("mainCategoryID")
    @Expose
    private List<String> mainCategoryID;

    @SerializedName("targetedGroupNameEN")
    @Expose
    private List<String> targetedGroupNameEN;

    @SerializedName("targetedGroupNameAR")
    @Expose
    private List<String> targetedGroupNameAR;

    @SerializedName("targetGroupID")
    @Expose
    private List<String> targetGroupID;

    @SerializedName("subCategoryNameEn")
    @Expose
    private List<String> subCategoryNameEn;

    @SerializedName("subCategoryNameAR")
    @Expose
    private List<String> subCategoryNameAR;

    @SerializedName("subCategoryID")
    @Expose
    private List<String> subCategoryID;

    @SerializedName("Color")
    @Expose
    private List<String> Color;

    @SerializedName("size")
    @Expose
    private List<String> size;

    @SerializedName("weight")
    @Expose
    private List<String> weight;

    @SerializedName("media")
    @Expose
    private List<String> media;

    @SerializedName("discountPrice")
    @Expose
    private String discountPrice;

    @SerializedName("discountPriceInSAR")
    @Expose
    private String discountPriceInSAR;

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("fromDate")
    @Expose
    private String fromDate;

    @SerializedName("toDate")
    @Expose
    private String toDate;

    @SerializedName("featureFromDate")
    @Expose
    private String featureFromDate;

    @SerializedName("featureToDate")
    @Expose
    private String featureToDate;

    @SerializedName("isInFeature")
    @Expose
    private boolean isInFeature=false;

    @SerializedName("isInPromotion")
    @Expose
    private boolean isInPromotion=false;


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("statuskey")
    @Expose
    private String statuskey;

    @SerializedName("statusKey")
    @Expose
    private String statusOrderItemKey;

    @SerializedName("qty_ordered")
    @Expose
    private String qty_ordered;

    @SerializedName("delivery_date")
    @Expose
    private String delivery_date;

    @SerializedName("delivery_slot")
    @Expose
    private String delivery_slot;

    @SerializedName("delivery_cost")
    @Expose
    private String delivery_cost;

    @SerializedName("delivery_costInSAR")
    @Expose
    private String delivery_costInSAR;



    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductNameEN() {
        return productNameEN;
    }

    public void setProductNameEN(String productNameEN) {
        this.productNameEN = productNameEN;
    }

    public String getProductNameAR() {
        return productNameAR;
    }

    public void setProductNameAR(String productNameAR) {
        this.productNameAR = productNameAR;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPerDayOrderLimit() {
        return perDayOrderLimit;
    }

    public void setPerDayOrderLimit(String perDayOrderLimit) {
        this.perDayOrderLimit = perDayOrderLimit;
    }

    public String getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(String handlingTime) {
        this.handlingTime = handlingTime;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionAR() {
        return descriptionAR;
    }

    public void setDescriptionAR(String descriptionAR) {
        this.descriptionAR = descriptionAR;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<DeliveryCostItem> getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(List<DeliveryCostItem> deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public List<ReviewItem> getReview() {
        return review;
    }

    public void setReview(List<ReviewItem> review) {
        this.review = review;
    }

    public List<String> getMainCategorynameEN() {
        return mainCategorynameEN;
    }

    public void setMainCategorynameEN(List<String> mainCategorynameEN) {
        this.mainCategorynameEN = mainCategorynameEN;
    }

    public List<String> getMainCategorynameAR() {
        return mainCategorynameAR;
    }

    public void setMainCategorynameAR(List<String> mainCategorynameAR) {
        this.mainCategorynameAR = mainCategorynameAR;
    }

    public List<String> getMainCategoryID() {
        return mainCategoryID;
    }

    public void setMainCategoryID(List<String> mainCategoryID) {
        this.mainCategoryID = mainCategoryID;
    }

    public List<String> getTargetedGroupNameEN() {
        return targetedGroupNameEN;
    }

    public void setTargetedGroupNameEN(List<String> targetedGroupNameEN) {
        this.targetedGroupNameEN = targetedGroupNameEN;
    }

    public List<String> getTargetedGroupNameAR() {
        return targetedGroupNameAR;
    }

    public void setTargetedGroupNameAR(List<String> targetedGroupNameAR) {
        this.targetedGroupNameAR = targetedGroupNameAR;
    }

    public List<String> getTargetGroupID() {
        return targetGroupID;
    }

    public void setTargetGroupID(List<String> targetGroupID) {
        this.targetGroupID = targetGroupID;
    }

    public List<String> getSubCategoryNameEn() {
        return subCategoryNameEn;
    }

    public void setSubCategoryNameEn(List<String> subCategoryNameEn) {
        this.subCategoryNameEn = subCategoryNameEn;
    }

    public List<String> getSubCategoryNameAR() {
        return subCategoryNameAR;
    }

    public void setSubCategoryNameAR(List<String> subCategoryNameAR) {
        this.subCategoryNameAR = subCategoryNameAR;
    }

    public List<String> getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(List<String> subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public List<String> getColor() {
        return Color;
    }

    public void setColor(List<String> color) {
        Color = color;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFeatureFromDate() {
        return featureFromDate;
    }

    public void setFeatureFromDate(String featureFromDate) {
        this.featureFromDate = featureFromDate;
    }

    public String getFeatureToDate() {
        return featureToDate;
    }

    public void setFeatureToDate(String featureToDate) {
        this.featureToDate = featureToDate;
    }

    public List<String> getWeight() {
        return weight;
    }

    public void setWeight(List<String> weight) {
        this.weight = weight;
    }

    public String getPriceInSAR() {
        return priceInSAR;
    }

    public void setPriceInSAR(String priceInSAR) {
        this.priceInSAR = priceInSAR;
    }

    public String getDiscountPriceInSAR() {
        return discountPriceInSAR;
    }

    public void setDiscountPriceInSAR(String discountPriceInSAR) {
        this.discountPriceInSAR = discountPriceInSAR;
    }

    public boolean isInFeature() {
        return isInFeature;
    }

    public void setInFeature(boolean inFeature) {
        isInFeature = inFeature;
    }

    public boolean isInPromotion() {
        return isInPromotion;
    }

    public void setInPromotion(boolean inPromotion) {
        isInPromotion = inPromotion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatuskey() {
        return statuskey;
    }

    public void setStatuskey(String statuskey) {
        this.statuskey = statuskey;
    }

    public String getStatusOrderItemKey() {
        return statusOrderItemKey;
    }

    public void setStatusOrderItemKey(String statusOrderItemKey) {
        this.statusOrderItemKey = statusOrderItemKey;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getQty_ordered() {
        return qty_ordered;
    }

    public void setQty_ordered(String qty_ordered) {
        this.qty_ordered = qty_ordered;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_slot() {
        return delivery_slot;
    }

    public void setDelivery_slot(String delivery_slot) {
        this.delivery_slot = delivery_slot;
    }

    public String getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(String delivery_cost) {
        this.delivery_cost = delivery_cost;
    }

    public String getDelivery_costInSAR() {
        return delivery_costInSAR;
    }

    public void setDelivery_costInSAR(String delivery_costInSAR) {
        this.delivery_costInSAR = delivery_costInSAR;
    }
}
