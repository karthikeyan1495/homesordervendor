package com.homesordervendor.product.addproduct.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.homesordervendor.BR;
import com.homesordervendor.user.shippingarea.model.Country;
import com.homesordervendor.user.shippingarea.model.State;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 3/4/18.
 */

public class ProductModel extends BaseObservable implements Serializable{

    private String productID;

    boolean isFood;
    boolean isFashion;
    boolean isTargetGroup;
    boolean isTargetGroupSubCategory;
    boolean isFoodSubCategory;

    String categoryName;
    int categoryId;
    String subCategoryName;
    int subCategoryId;
    int subCategoryPosition=0;
    String targetGroupName;
    int targetGroupId;
    int targetGroupPosition=0;
    String targetGroupSubCategoryName;
    int targetGroupSubCategoryId;
    int targetGroupSubCategoryPosition=0;
    String productNameEnglish="";
    String productDescriptionEnglish="";
    boolean categoryAPICall;


    String productNameArabic="";
    String productDescriptionArabic="";
    String price="";
    String size="";
    String weight="";
    String color="";
    String limit="";
    String time="";
    boolean isDeliveryCoverage;
    boolean areaAPICall;

    ImageURI imageURI=new ImageURI();
    List<ImageURI> imageURIList=new ArrayList<>();

    List<Country> countries=new ArrayList<>();
    List<State> stateList = new ArrayList();
    List<Categories> categories=new ArrayList<>();



    @Bindable
    public boolean isFood() {
        return isFood;
    }

    @Bindable
    public void setFood(boolean food) {
        isFood = food;
        notifyPropertyChanged(BR.food);

    }

    @Bindable
    public boolean isFashion() {
        return isFashion;
    }

    @Bindable
    public void setFashion(boolean fashion) {
        isFashion = fashion;
        notifyPropertyChanged(BR.fashion);

    }

    @Bindable
    public boolean isTargetGroup() {
        return isTargetGroup;
    }

    @Bindable
    public void setTargetGroup(boolean targetGroup) {
        isTargetGroup = targetGroup;
        notifyPropertyChanged(BR.targetGroup);

    }

    @Bindable
    public boolean isTargetGroupSubCategory() {
        return isTargetGroupSubCategory;
    }

    @Bindable
    public void setTargetGroupSubCategory(boolean targetGroupSubCategory) {
        isTargetGroupSubCategory = targetGroupSubCategory;
        notifyPropertyChanged(BR.targetGroupSubCategory);

    }

    @Bindable
    public boolean isFoodSubCategory() {
        return isFoodSubCategory;
    }

    @Bindable
    public void setFoodSubCategory(boolean foodSubCategory) {
        isFoodSubCategory = foodSubCategory;
        notifyPropertyChanged(BR.foodSubCategory);
    }



    @Bindable
    public String getCategoryName() {
        return categoryName;
    }

    @Bindable
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        notifyPropertyChanged(BR.categoryName);
    }

    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    @Bindable
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId);
    }

    @Bindable
    public String getSubCategoryName() {
        return subCategoryName;
    }

    @Bindable
    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
        notifyPropertyChanged(BR.subCategoryName);
    }

    @Bindable
    public int getSubCategoryId() {
        return subCategoryId;
    }

    @Bindable
    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
        notifyPropertyChanged(BR.subCategoryId);
    }

    @Bindable
    public String getTargetGroupName() {
        return targetGroupName;
    }

    @Bindable
    public void setTargetGroupName(String targetGroupName) {
        this.targetGroupName = targetGroupName;
        notifyPropertyChanged(BR.targetGroupName);
    }

    @Bindable
    public int getTargetGroupId() {
        return targetGroupId;
    }

    @Bindable
    public void setTargetGroupId(int targetGroupId) {
        this.targetGroupId = targetGroupId;
        notifyPropertyChanged(BR.targetGroupId);
    }

    @Bindable
    public String getTargetGroupSubCategoryName() {
        return targetGroupSubCategoryName;
    }

    @Bindable
    public void setTargetGroupSubCategoryName(String targetGroupSubCategoryName) {
        this.targetGroupSubCategoryName = targetGroupSubCategoryName;
        notifyPropertyChanged(BR.targetGroupSubCategoryName);
    }

    @Bindable
    public int getTargetGroupSubCategoryId() {
        return targetGroupSubCategoryId;
    }

    @Bindable
    public void setTargetGroupSubCategoryId(int targetGroupSubCategoryId) {
        this.targetGroupSubCategoryId = targetGroupSubCategoryId;
        notifyPropertyChanged(BR.targetGroupSubCategoryId);
    }

    @Bindable
    public String getProductNameEnglish() {
        return productNameEnglish;
    }

    @Bindable
    public void setProductNameEnglish(String productNameEnglish) {
        this.productNameEnglish = productNameEnglish;
        notifyPropertyChanged(BR.productNameEnglish);
    }


    @Bindable
    public String getProductDescriptionEnglish() {
        return productDescriptionEnglish;
    }

    @Bindable
    public void setProductDescriptionEnglish(String productDescriptionEnglish) {
        this.productDescriptionEnglish = productDescriptionEnglish;
        notifyPropertyChanged(BR.productDescriptionEnglish);
    }

    @Bindable
    public int getSubCategoryPosition() {
        return subCategoryPosition;
    }

    @Bindable
    public void setSubCategoryPosition(int subCategoryPosition) {
        this.subCategoryPosition = subCategoryPosition;
        notifyPropertyChanged(BR.subCategoryPosition);
    }

    @Bindable
    public int getTargetGroupPosition() {
        return targetGroupPosition;
    }

    @Bindable
    public void setTargetGroupPosition(int targetGroupPosition) {
        this.targetGroupPosition = targetGroupPosition;
        notifyPropertyChanged(BR.targetGroupPosition);

    }

    @Bindable
    public int getTargetGroupSubCategoryPosition() {
        return targetGroupSubCategoryPosition;
    }

    @Bindable
    public void setTargetGroupSubCategoryPosition(int targetGroupSubCategoryPosition) {
        this.targetGroupSubCategoryPosition = targetGroupSubCategoryPosition;
        notifyPropertyChanged(BR.targetGroupSubCategoryPosition);
    }

    @Bindable
    public boolean isCategoryAPICall() {
        return categoryAPICall;
    }

    @Bindable
    public void setCategoryAPICall(boolean categoryAPICall) {
        this.categoryAPICall = categoryAPICall;
        notifyPropertyChanged(BR.categoryAPICall);

    }


    @Bindable
    public String getProductNameArabic() {
        return productNameArabic;
    }

    @Bindable
    public void setProductNameArabic(String productNameArabic) {
        this.productNameArabic = productNameArabic;
        notifyPropertyChanged(BR.productNameArabic);
    }

    @Bindable
    public String getProductDescriptionArabic() {
        return productDescriptionArabic;
    }

    @Bindable
    public void setProductDescriptionArabic(String productDescriptionArabic) {
        this.productDescriptionArabic = productDescriptionArabic;
        notifyPropertyChanged(BR.productDescriptionArabic);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    @Bindable
    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getSize() {
        return size;
    }

    @Bindable
    public void setSize(String size) {
        this.size = size;
        notifyPropertyChanged(BR.size);
    }

    @Bindable
    public String getWeight() {
        return weight;
    }

    @Bindable
    public void setWeight(String weight) {
        this.weight = weight;
        notifyPropertyChanged(BR.weight);
    }

    @Bindable
    public String getColor() {
        return color;
    }

    @Bindable
    public void setColor(String color) {
        this.color = color;
        notifyPropertyChanged(BR.color);
    }

    @Bindable
    public boolean isDeliveryCoverage() {
        return isDeliveryCoverage;
    }

    @Bindable
    public void setDeliveryCoverage(boolean deliveryCoverage) {
        isDeliveryCoverage = deliveryCoverage;
        notifyPropertyChanged(BR.deliveryCoverage);
    }

    @Bindable
    public String getLimit() {
        return limit;
    }

    @Bindable
    public void setLimit(String limit) {
        this.limit = limit;
        notifyPropertyChanged(BR.limit);

    }

    @Bindable
    public String getTime() {
        return time;
    }

    @Bindable
    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.limit);

    }

    @Bindable
    public boolean isAreaAPICall() {
        return areaAPICall;
    }

    @Bindable
    public void setAreaAPICall(boolean areaAPICall) {
        this.areaAPICall = areaAPICall;
        notifyPropertyChanged(BR.areaAPICall);
    }

    @Bindable
    public ImageURI getImageURI() {
        return imageURI;
    }

    @Bindable
    public void setImageURI(ImageURI imageURI) {
        this.imageURI = imageURI;
        notifyPropertyChanged(BR.imageURI);
    }

    @Bindable
    public List<ImageURI> getImageURIList() {
        return imageURIList;
    }

    @Bindable
    public void setImageURIList(List<ImageURI> imageURIList) {
        this.imageURIList = imageURIList;
        notifyPropertyChanged(BR.imageURIList);
    }

    @Bindable
    public List<State> getStateList() {
        return stateList;

    }

    @Bindable
    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
        notifyPropertyChanged(BR.stateList);
    }

    @Bindable
    public List<Country> getCountries() {
        return countries;
    }

    @Bindable
    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyPropertyChanged(BR.countries);
    }

    @Bindable
    public List<Categories> getCategories() {
        return categories;
    }

    @Bindable
    public void setCategories(List<Categories> categories) {
        this.categories = categories;
        notifyPropertyChanged(BR.categories);
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
