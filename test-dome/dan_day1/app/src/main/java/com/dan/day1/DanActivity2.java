package com.dan.day1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * @fileName: DanActivity.java
 * @author: Dan
 * @createDate: 2018/9/6 15:41
 * @description: 程序运行是就显示的界面
 */
public class DanActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 必须执行此句代码. 执行父类的初始化操作.
        super.onCreate(savedInstanceState);
        // 设置当前界面显示的布局.
        setContentView(R.layout.dan_maim);

    }

    /**
     * 点击按钮
     */
    public void callClick(View v) {
        ///声明一个EditText对象et_telNumber，并通过资源id得到EditText实例
        EditText et_telNumber = (EditText) findViewById(R.id.number);
        //得到电话号码
        String telNumber = et_telNumber.getText().toString();
        if (telNumber.isEmpty()) {
            return;
        }
        System.out.println("telNumber:" + telNumber);
        //根据号码拨打电话
        call(telNumber);

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

}
