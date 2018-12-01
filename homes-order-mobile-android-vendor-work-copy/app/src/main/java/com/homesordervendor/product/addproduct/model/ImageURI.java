package com.homesordervendor.product.addproduct.model;

import java.io.Serializable;

/**
 * Created by mac on 3/3/18.
 */

public class ImageURI implements Serializable {

    int type;
    String uri;
    boolean isImage;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }
}
