package com.example.dan.helloword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DanMainActivity extends AppCompatActivity {

    /**
     * 当界面创建时调用的方法
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //必须执行此代码操作，初始化父类
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dan_main);
        int butClick = R.id.butClick;
        System.out.println(butClick);
    }
}
