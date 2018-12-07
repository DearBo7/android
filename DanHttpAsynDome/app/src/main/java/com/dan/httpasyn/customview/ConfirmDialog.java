package com.dan.httpasyn.customview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.dan.httpasyn.activity.R;


/**
 * Created by Dan on 2018/11/9 14:44
 */
public class ConfirmDialog extends Dialog {

    public ConfirmDialog(Context context, String title, String msg, final Callback callback) {
        super(context, R.style.AppTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callback(1);
                ConfirmDialog.this.cancel();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.callback(0);
                ConfirmDialog.this.cancel();
            }
        });
        builder.setIcon(R.mipmap.ic_launcher);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showAlert(Context context, String title, String message, OnClickListener listener) {
        showAlert(context, title, message, "设置", false, listener);
    }

    public static void showAlert(Context context, String title, String message, String positiveBtnTxt, boolean cancelableFlag, OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveBtnTxt, listener);
        builder.setCancelable(cancelableFlag);
        builder.setIcon(R.mipmap.ic_launcher);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface Callback {
        void callback(int position);
    }
}
