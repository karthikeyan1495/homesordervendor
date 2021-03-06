package com.homesordervendor.imageloader;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.homesordervendor.R;

/**
 * Created by mac on 3/3/18.
 */

public class ImageLoader {

    @BindingAdapter({"productFrontImage"})
    public static void productFrontImage(ImageView view, String uriString){
        if (uriString!=null) {
            GlideApp.with(view.getContext()).load(Uri.parse(uriString)).dontAnimate().dontTransform().into(view);
        }
    }

    @BindingAdapter({"productImage"})
    public static void productImage(ImageView view, String url){
        GlideApp.with(view.getContext()).load(url).dontAnimate().dontTransform().into(view);
    }

    @BindingAdapter({"profileImage"})
    public static void profileImage(ImageView view, String url){
        GlideApp.with(view.getContext()).load(url).placeholder(R.drawable.placeholder).dontAnimate()
                .dontTransform()
                .into(view);
    }
}
