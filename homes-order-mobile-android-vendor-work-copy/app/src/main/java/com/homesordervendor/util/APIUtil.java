package com.homesordervendor.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;

import com.homesordervendor.product.addproduct.model.DeliveryArea;
import com.homesordervendor.product.addproduct.model.DeliveryCost;
import com.homesordervendor.product.addproduct.model.DeliveryStates;
import com.homesordervendor.product.addproduct.model.ImageURI;
import com.homesordervendor.product.addproduct.model.ProductModel;
import com.homesordervendor.product.addproduct.model.ProductSubmit;
import com.homesordervendor.user.shippingarea.model.Area;
import com.homesordervendor.user.shippingarea.model.Country;
import com.homesordervendor.user.shippingarea.model.State;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 3/4/18.
 */

public class APIUtil {

    public static ProductSubmit generateAddProduct(Context context, ProductModel productModel) {

        ProductSubmit submit = new ProductSubmit();

        if (productModel.getProductID() != null && productModel.getProductID().trim().length() != 0) {
            submit.setProductID(productModel.getProductID());
        }

        submit.setProductname(productModel.getProductNameEnglish());
        submit.setProductname_arabic(productModel.getProductNameArabic());
        submit.setPrice(productModel.getPrice());
        submit.setDescription(productModel.getProductDescriptionEnglish());
        submit.setDescription_arabic(productModel.getProductDescriptionArabic());
        submit.setOrderlimitperday(String.valueOf(productModel.getLimit()));
        submit.setHandlingtime(String.valueOf(productModel.getTime()));

        if (productModel.getCountries().size() != 0) {
            List<DeliveryCost> deliveryCostList = new ArrayList<>();

            for (Country country : productModel.getCountries()) {

                DeliveryCost deliveryCost = new DeliveryCost();
                deliveryCost.setCountryID(String.valueOf(country.getCountryID()));

                List<DeliveryStates> deliveryStatesList = new ArrayList<>();

                for (State state : country.getStates()) {

                    List<DeliveryArea> areaList = new ArrayList<>();

                    for (Area area : state.getAreas()) {
                        DeliveryArea deliveryArea = new DeliveryArea();
                        deliveryArea.setAreaID(String.valueOf(area.getAreaID()));
                        deliveryArea.setPrice(area.getPrice());
                        areaList.add(deliveryArea);
                    }

                    DeliveryStates deliveryStates = new DeliveryStates();
                    deliveryStates.setStateID(String.valueOf(state.getStateID()));
                    deliveryStates.setAreas(areaList);
                    deliveryStatesList.add(deliveryStates);
                }

                deliveryCost.setStates(deliveryStatesList);
                deliveryCostList.add(deliveryCost);

            }

            submit.setDeliveryCost(deliveryCostList);
        }

        List<String> weight = new ArrayList<>();
        String[] weightStrings = productModel.getWeight().split(",");
        for (int i = 0; i < weightStrings.length; i++) {
            weight.add(weightStrings[i]);
        }
        submit.setWeight(weight);

        String[] colorStrings = productModel.getColor().split(",");
        List<String> color = new ArrayList<>();
        for (int i = 0; i < colorStrings.length; i++) {
            color.add(colorStrings[i]);
        }
        submit.setColor(color);

        String[] sizeStrings = productModel.getSize().split(",");
        List<String> size = new ArrayList<>();
        for (int i = 0; i < sizeStrings.length; i++) {
            size.add(sizeStrings[i]);
        }
        submit.setSize(size);

        List<String> category = new ArrayList<>();
        category.add(String.valueOf(productModel.getCategoryId()));
        if (productModel.isFood()) {
            category.add(String.valueOf(productModel.getSubCategoryId()));
        } else if (productModel.isFashion()) {
            category.add(String.valueOf(productModel.getTargetGroupId()));
            category.add(String.valueOf(productModel.getTargetGroupSubCategoryId()));
        }
        submit.setCategory(category);
        submit.setImages(getByteConvertedImages(context, productModel));

        return submit;

    }

    private static ArrayList<String> getByteConvertedImages(Context context, ProductModel productModel) {

        ArrayList<String> byteImages = new ArrayList<>();

        ArrayList<ImageURI> productImagesUri = new ArrayList<>();
        productImagesUri.add(productModel.getImageURI());

        for (int i = 0; i < productModel.getImageURIList().size() - 1; i++) {
            productImagesUri.add(productModel.getImageURIList().get(i));
        }

        for (ImageURI eachImageUri : productImagesUri) {
            try {
                byteImages.add(getEncodedString(context, Uri.parse(eachImageUri.getUri())));
            } catch (IOException e) {
                //byteImages.add("");
            }
        }

        return byteImages;
    }

    public static String getImageByte(Context context, Uri uri) {
        try {
            return getEncodedString(context, uri);
        } catch (IOException e) {
            return "";
        }
    }

    public static String getEncodedString(Context context, Uri uri) throws IOException {
        Bitmap bitmap = getImage(context, uri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] bytes = baos.toByteArray();
        String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
        return encoded;
    }

    public static Bitmap getImage(Context context, Uri uri) throws FileNotFoundException {
        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");

        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        try {
            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
