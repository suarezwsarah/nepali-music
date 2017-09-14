package com.apps.utils;

import android.content.Context;
import android.widget.ImageView;

public class MyDrawableUtil {

    public static void drawImage(ImageView imageView, int id) {
        Context context = Constant.context.getApplicationContext();
        imageView.setImageDrawable(context.getDrawable(id));
    }

}
