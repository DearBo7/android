package com.dan.httpasyn.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.httpasyn.activity.base.BaseFinalActivity;
import com.dan.httpasyn.entity.User;
import com.dan.httpasyn.util.AjaxResult;
import com.dan.httpasyn.util.JsonUtil;
import com.dan.httpasyn.util.SystemApplication;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.commons.lang3.StringUtils;

public class MainActivity extends BaseFinalActivity {

    @ViewInject(id = R.id.tv_main_title)
    private TextView tvMainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(SystemApplication.toPrint());
        getUser(SystemApplication.getDataToken());
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getUser(String dataToken) {
        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("oauth", dataToken);
        http.get("http://119.37.194.4:5555/xtp-api/user/getUser", params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                AjaxResult result = JsonUtil.fromJson(json, AjaxResult.class);
                if (result != null && result.getCode().equals(1) && StringUtils.isNoneBlank(result.getData().toString())) {
                    String toJson = JsonUtil.toJson(result.getData());
                    SystemApplication.user = JsonUtil.fromJson(toJson, User.class);
                    tvMainTitle.setText(SystemApplication.getUser().getName());
                } else {
                    SystemApplication.user = new User();
                }
            }
        });
    }
}
