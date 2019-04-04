package com.dan.day1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dan on 2018/9/7 10:00
 * 程序运行是就显示的界面
 */
public class DanActivity extends AppCompatActivity {

    private final static int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 必须执行此句代码. 执行父类的初始化操作.
        super.onCreate(savedInstanceState);

        //去除标题,必须在setContentView之前调用
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置当前界面显示的布局.
        setContentView(R.layout.dan_maim);
        //获取一个按钮
        Button button = (Button) findViewById(R.id.btnCall);
        //设置一个点击事件
        button.setOnClickListener(new MyOnclickList());
    }

    class MyOnclickList implements OnClickListener {
        @Override
        public void onClick(View v) {
            ///声明一个EditText对象et_telNumber，并通过资源id得到EditText实例
            EditText et_telNumber = (EditText) findViewById(R.id.number);
            //得到电话号码
            String telNumber = et_telNumber.getText().toString();
            if (telNumber.isEmpty()) {
                return;
            }
            System.out.println("当前手机号：" + telNumber);
            //读取内存权限
            String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
            //判断是否有权限
            if (!checkPermission(Manifest.permission.CALL_PHONE)) {
                //没有就去获取权限
                getPermission(Manifest.permission.CALL_PHONE);
            }
            call(telNumber);
        }
    }

    private void call(String telNumber) {
        //创建一个意图
        Intent intent = new Intent();
        //指定其动作作为拨打电话
        intent.setAction(Intent.ACTION_CALL);
        //指定将要拨出的号码
        intent.setData(Uri.parse("tel:" + telNumber));
        //执行这个动作
        startActivity(intent);
    }

    /**
     * 检查某个权限是否已经获得
     */
    private boolean checkPermission(String permission) {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        ActivityCompat.checkSelfPermission(DanActivity.this, permission);

        return ActivityCompat.checkSelfPermission(DanActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取权限
     */
    private void getPermission(String permission, String... par) {
        int length = par.length == 0 ? 1 : par.length;
        String[] permissions = new String[length];
        if (par.length != 0) {
            for (int i = 0; i < par.length; i++) {
                permissions[i] = par[i];
            }
        } else {
            permissions[0] = permission;
        }
        //申请权限
        ActivityCompat.requestPermissions(DanActivity.this, permissions, REQUEST_EXTERNAL_STORAGE);

        //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
        if (ActivityCompat.shouldShowRequestPermissionRationale(DanActivity.this, permission)) {
            Toast.makeText(DanActivity.this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
        }
    }


}
