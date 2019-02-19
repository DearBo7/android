package com.dan.dome;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.dome.config.HttpConfig;
import com.dan.dome.entity.User;
import com.dan.dome.fragment.IndexFragment;
import com.dan.dome.fragment.MyFragment;
import com.dan.dome.fragment.OtherFragment;
import com.dan.dome.fragment.base.BaseFragmentActivity;
import com.dan.dome.util.SystemApplication;
import com.dan.library.customview.ConfirmDialog;
import com.dan.library.util.AjaxResult;
import com.dan.library.util.JsonUtil;
import com.dan.library.util.NetworkUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.commons.lang3.StringUtils;

public class MainActivity extends BaseFragmentActivity {

    public TextView tvMainTitle;

    private FrameLayout mvp;

    private RadioGroup rg_home;

    private FragmentManager fManager;

    private Fragment indexFragment, otherFragment, myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!NetworkUtil.isNetworkAvailable(MainActivity.this)) {
            new ConfirmDialog(getApplicationContext(), "你沒有开启数据连接！", "是否对网络进行设置？", "是", "否", new ConfirmDialog.Callback() {
                @Override
                public void callback(int position) {
                    if (position == 1) {
                        // 如果在设置完成后需要再次进行操作，可以重写操作代码，在这里不再重写
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    }
                }
            });
        }
        fManager = getSupportFragmentManager();
        initFragment();

    }

    private void initFragment() {
        tvMainTitle = findViewById(R.id.tx_title);
        mvp = findViewById(R.id.mvp);
        rg_home = findViewById(R.id.rg_home);

        //设置数据
        setData();
        //设置监听
        setListener();
    }

    private void setListener() {
        rg_home.setOnCheckedChangeListener((group, checkedId) -> {
            FragmentTransaction transaction = fManager.beginTransaction();
            hideAllFragment(transaction);
            switch (checkedId) {
                case R.id.rb_index:
                    if (indexFragment == null) {
                        indexFragment = new IndexFragment();
                        transaction.add(R.id.mvp, indexFragment);
                    } else {
                        transaction.show(indexFragment);
                    }
                    break;
                case R.id.rb_other:
                    if (otherFragment == null) {
                        otherFragment = new OtherFragment();
                        transaction.add(R.id.mvp, otherFragment);
                    } else {
                        transaction.show(otherFragment);
                    }
                    break;
                case R.id.rb_my:
                    if (myFragment == null) {
                        myFragment = new MyFragment();
                        transaction.add(R.id.mvp, myFragment);
                    } else {
                        transaction.show(myFragment);
                    }
                    break;
            }
            transaction.commit();
        });
        //默认选中第一个,触发一次事件  rg_home.check(R.id.rb_index);(会触发两次)
        ((RadioButton) findViewById(R.id.rb_index)).setChecked(true);

        mvp.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    mvp.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mvp.requestDisallowInterceptTouchEvent(false);
                default:
                    break;
            }
            return true;
        });
    }

    private void setData() {
        System.out.println(SystemApplication.toPrint());
        getUser(SystemApplication.getDataToken());
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {

        if (indexFragment != null) {
            fragmentTransaction.hide(indexFragment);
        }
        if (otherFragment != null) {
            fragmentTransaction.hide(otherFragment);
        }
        if (myFragment != null) {
            fragmentTransaction.hide(myFragment);
        }
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
        http.get(HttpConfig.getUser(), params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                AjaxResult result = JsonUtil.fromJson(json, AjaxResult.class);
                if (result != null && result.getCode().equals(1) && StringUtils.isNoneBlank(result.getData().toString())) {
                    String toJson = JsonUtil.toJson(result.getData());
                    SystemApplication.user = JsonUtil.fromJson(toJson, User.class);
                } else {
                    SystemApplication.user = new User();
                }
            }
        });
    }
}