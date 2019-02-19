package com.dan.library.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Dan on 2018/10/17 15:07
 */
public class ToastUtil {

    public static void makeText(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void makeText(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }
}
