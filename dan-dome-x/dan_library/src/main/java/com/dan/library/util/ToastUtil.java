package com.dan.library.util;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Dan on 2018/10/17 15:07
 */
public class ToastUtil {
    private static final String TAG = "ToastUtil";

    public static void makeText(Context context, String msg) {
        makeText(context, msg, Toast.LENGTH_SHORT);
    }

    public static void makeText(Context context, String msg, int duration) {
        try {
            Toast.makeText(context, msg, duration).show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, msg, duration).show();
            Looper.loop();
        }
    }
}
