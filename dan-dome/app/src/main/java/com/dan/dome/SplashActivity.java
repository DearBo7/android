package com.dan.dome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dan.dome.activity.LoginActivity;
import com.dan.library.util.StatusBarUtils;

/**
 * Created by Dan on 2019/1/17 15:30
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {

    //两秒后进入系统
    private final int SPLASH_DISPLAY_TIME = 2000;
    private boolean testFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏全屏
        StatusBarUtils.setWindowFullScreen(this);

        setContentView(R.layout.activity_splash);
        //停留2秒然后进主页面
        new android.os.Handler().postDelayed(() -> {
            Intent intent;
            if (testFlag) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            }
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }, SPLASH_DISPLAY_TIME);

    }

}
