package com.dan.util;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Dan on 2018/10/15 14:21
 */
public class PermissionsUtil {

    private AppCompatActivity activity;

    public PermissionsUtil(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * 检查某个权限是否已经获得
     */
    public boolean checkPermission(String permission) {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        ActivityCompat.checkSelfPermission(activity, permission);

        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取权限
     */
    public void getPermission(String permission, String[] permissions) {
        //申请权限
        ActivityCompat.requestPermissions(activity, permissions, 1);

        //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            Toast.makeText(activity, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
        }
    }
}
