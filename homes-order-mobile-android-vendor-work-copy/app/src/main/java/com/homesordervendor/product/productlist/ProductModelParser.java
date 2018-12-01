package com.homesordervendor.product.productlist;

import com.homesordervendor.MyApp;
import com.homesordervendor.R;
import com.homesordervendor.product.addproduct.model.ImageURI;
import com.homesordervendor.product.addproduct.model.ProductModel;
import com.homesordervendor.product.productlist.model.ProductItem;
import com.homesordervendor.sharedpreferences.MySession;

/**
 * Created by innoppl on 11/03/18.
 */

public class ProductModelParser {

    public static ProductModel productItemParser(ProductItem product){

        ProductModel tempProductModel=new ProductModel();

        if (product.getMainCategorynameEN()!=null&&product.getMainCategorynameEN().size()!=0){

            tempProductModel.setProductID(product.getProductID());

            if (product.getMainCategoryID()!=null&&product.getMainCategoryID().size()!=0){
                tempProductModel.setCategoryId(Integer.valueOf(product.getMainCategoryID()
                        .get(0)));
                tempProductModel.setCategoryName(product.getMainCategorynameEN().get(0));
            }

            if (product.getMainCategorynameEN()!=null&&product.getMainCategorynameEN().get(0).trim()
                    .toLowerCase().equals("food")){
                tempProductModel.setFood(true);
                tempProductModel.setFashion(false);
            }else{
                tempProductModel.setFood(false);
                tempProductModel.setFashion(true);
            }

            if (tempProductModel.isFood()){
                if (product.getSubCategoryID()!=null&&product.getSubCategoryID().size()!=0){
                    tempProductModel.setSubCategoryId(Integer.valueOf(product.getSubCategoryID()
                            .get(0)));
                    tempProductModel.setFoodSubCategory(true);
                }
                if (product.getSubCategoryNameEn()!=null&&product.getSubCategoryNameEn().size()!=0){
                    tempProductModel.setSubCategoryName(product.getSubCategoryNameEn().get(0));
                }
            }else{

                if(product.getTargetGroupID()!=null&&product.getTargetGroupID().size()!=0){
                    tempProductModel.setTargetGroupId(Integer.valueOf(product.getTargetGroupID()
                            .get(0)));
                    tempProductModel.setTargetGroup(true);
                }

                if (product.getTargetedGroupNameEN()!=null&&product.getTargetedGroupNameEN().size()
                        !=0){
                    tempProductModel.setTargetGroupName(product.getTargetedGroupNameEN().get(0));
                }


                if (product.getSubCategoryID()!=null&&product.getSubCategoryID().size()!=0){
                    tempProductModel.setTargetGroupSubCategoryId(Integer.valueOf(product.getSubCategoryID()
                            .get(0)));
                    tempProductModel.setTargetGroupSubCategory(true);
                }
                if (product.getSubCategoryNameEn()!=null&&product.getSubCategoryNameEn().size()!=0){
                    tempProductModel.setTargetGroupSubCategoryName(product.getSubCategoryNameEn().get(0));
                }
            }

            tempProductModel.setProductNameEnglish(product.getProductNameEN());
            tempProductModel.setProductNameArabic(product.getProductNameAR());

            tempProductModel.setProductDescriptionEnglish(product.getDescriptionEN());
            tempProductModel.setProductDescriptionArabic(product.getProductNameAR());

            if (MySession.getInstance(MyApp.getContext()).getCurrency().equals(MyApp.getContext()
                    .getString(R.string.aed))) {
                tempProductModel.setPrice(product.getPrice());
            }else{
                tempProductModel.setPrice(product.getPriceInSAR());
            }

            tempProductModel.setLimit(product.getPerDayOrderLimit());
            tempProductModel.setTime(product.getHandlingTime());

            if (product.getWeight()!=null&&product.getWeight().size()!=0) {
                tempProductModel.setWeight(product.getWeight().get(0));
            }

            if (product.getSize()!=null&&product.getSize().size()!=0){
                tempProductModel.setSize(product.getSize().get(0));
            }

            if (product.getColor()!=null&&product.getColor().size()!=0){
                tempProductModel.setColor(product.getColor().get(0));
            }

            if (product.getMedia()!=null&&product.getMedia().size()!=0){
                ImageURI imageURI=new ImageURI();
                imageURI.setImage(true);
                imageURI.setType(1);
                imageURI.setUri(product.getMedia().get(0));
                tempProductModel.setImageURI(imageURI);
                for (int i=1;i<product.getMedia().size();i++){
                    imageURI=new ImageURI();
                    imageURI.setImage(true);
                    imageURI.setType(1);
                    imageURI.setUri(product.getMedia().get(i));
                    tempProductModel.getImageURIList().add(imageURI);
                }
                //New Image Add View
                imageURI=new ImageURI();
                imageURI.setType(2);
                tempProductModel.getImageURIList().add(imageURI);
            }
        }

        return tempProductModel;
    }

}
