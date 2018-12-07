package com.dan.testday3;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dan.DanUtil;
import com.dan.service.UserService;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by Dan on 2018/9/18 9:59
 */
public class LinearLayoutDomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout_dome);

        final EditText etNumber = (EditText) findViewById(R.id.et_number);
        final EditText etPwd = (EditText) findViewById(R.id.et_pwd);
        final CheckBox cbRememberPwd = (CheckBox) findViewById(R.id.cb_remember_pwd);

        Map<String, String> userMap = UserService.getUserInfo(LinearLayoutDomeActivity.this);
        if (userMap != null) {
            etNumber.setText(userMap.get("number"));
            etPwd.setText(userMap.get("pwd"));
            cbRememberPwd.setChecked(true);
        }
        //SharedPreferences方式
        //Map<String, String> sharedPreferences = UserService.getUserInfoSharedPreferences(LinearLayoutDomeActivity.this);
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取出账号密码
                String number = etNumber.getText().toString();
                String pwd = etPwd.getText().toString();
                if (StringUtils.isBlank(number) || StringUtils.isBlank(pwd)) {
                    //弹出
                    Toast.makeText(LinearLayoutDomeActivity.this, "请正确输入!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //记住密码
                if (cbRememberPwd.isChecked()) {
                    boolean saveFlag = UserService.saveUserInfo(LinearLayoutDomeActivity.this, number.trim(), pwd.trim());
                    if (saveFlag) {
                        Toast.makeText(LinearLayoutDomeActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LinearLayoutDomeActivity.this, "保存失败!", Toast.LENGTH_SHORT).show();
                    }
                   /* boolean preferences = UserService.saveUserInfoSharedPreferences(LinearLayoutDomeActivity.this, number.trim(), pwd.trim());
                    System.out.println("preferences:" + preferences);*/
                    Log.i("", "账号：" + number + ",密码：" + pwd);
                } else {
                    boolean deleteUserInfo = UserService.deleteUserInfo(LinearLayoutDomeActivity.this);
                    //boolean b = UserService.deleteUserInfoSharedPreferences(LinearLayoutDomeActivity.this);
                    Log.i("", "删除保存记录：" + deleteUserInfo);
                }
                //获取内存卡大小
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                String sdMemoryInfo = DanUtil.getMemoryInfo(LinearLayoutDomeActivity.this, absolutePath);
                //
                String dataDirectory = Environment.getDataDirectory().getAbsolutePath();
                String memoryInfo = DanUtil.getMemoryInfo(LinearLayoutDomeActivity.this, dataDirectory);
                Toast.makeText(LinearLayoutDomeActivity.this, "登录成功!\n" + "手机内存:" + sdMemoryInfo + "\n手机内部空间：" + memoryInfo, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
