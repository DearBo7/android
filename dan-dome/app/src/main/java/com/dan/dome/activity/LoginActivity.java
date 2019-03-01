package com.dan.dome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.dan.dome.MainActivity;
import com.dan.dome.R;
import com.dan.dome.activity.base.BaseFinalActivity;
import com.dan.dome.config.HttpConfig;
import com.dan.dome.enums.LoginKeyEnum;
import com.dan.dome.service.LoginService;
import com.dan.dome.util.SystemApplication;
import com.dan.library.config.HttpStatusCode;
import com.dan.library.util.AjaxResult;
import com.dan.library.util.JsonUtil;
import com.dan.library.util.StatusBarUtils;
import com.dan.library.util.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Dan on 2018/10/17 12:00
 */
public class LoginActivity extends BaseFinalActivity {

    @ViewInject(id = R.id.btn_login_submit, click = "btnLoginClick")
    Button btnSubmit;
    @ViewInject(id = R.id.et_login_username)
    EditText etUserName;
    @ViewInject(id = R.id.et_login_pwd)
    EditText etPwd;
    /**
     * 是否记住密码
     */
    @ViewInject(id = R.id.cb_login_remember_pwd)
    CheckBox cbRememberPwd;
    /**
     * 是否自动登录
     */
    @ViewInject(id = R.id.cb_login_auto_login)
    CheckBox cbAutoLogin;

    private LoginService loginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
        StatusBarUtils.setWindowStatusBarColor(this);
        setContentView(R.layout.login_activity);
        loginService = new LoginService(LoginActivity.this);
        String account = loginService.getStringValue(LoginKeyEnum.USER_NAME.key);
        String password = loginService.getStringValue(LoginKeyEnum.PWD.key);
        //自动登录
        Boolean autoLoginFlag = loginService.getBooleanValue(LoginKeyEnum.AUTO_LOGIN.key);
        //记住密码
        Boolean rememberPwdFlag = loginService.getBooleanValue(LoginKeyEnum.REMEMBER_PWD.key);
        if (rememberPwdFlag || autoLoginFlag) {
            etUserName.setText(account);
            etPwd.setText(password);
            cbRememberPwd.setChecked(rememberPwdFlag);
            cbAutoLogin.setChecked(autoLoginFlag);
            if (autoLoginFlag) {
                login(account, password);
            }
        }
    }

    /**
     * 点击登录
     */
    public void btnLoginClick(View v) {
        final String account = etUserName.getText().toString();
        final String password = etPwd.getText().toString();
        if (StringUtils.isBlank(account)) {
            ToastUtil.makeText(LoginActivity.this, "账号不能为空!");
            return;
        }
        if (StringUtils.isBlank(password)) {
            ToastUtil.makeText(LoginActivity.this, "密码不能为空!");
            return;
        }
        login(account, password);

        //ToastUtil.makeText(LoginActivity.this, "登录成功!userName:" + userName + ",pwd:" + pwd);
    }

    private void login(final String account, final String password) {
        final AjaxParams params = new AjaxParams();
        params.put("account", account);
        params.put("password", password);
        final FinalHttp http = new FinalHttp();
        try {
            http.post(HttpConfig.getLoginUrl(), params, new AjaxCallBack<String>() {

                @Override
                public void onSuccess(String json) {
                    btnSubmit.setBackgroundResource(android.R.color.holo_purple);
                    btnSubmit.setText("登录");
                    btnSubmit.setEnabled(true);
                    mLoading.dismiss();
                    AjaxResult result = JsonUtil.fromJson(json, AjaxResult.class);
                    if (result != null && result.getCode().equals(1)) {
                        if (cbRememberPwd.isChecked() || cbAutoLogin.isChecked()) {
                            loginService.saveUser(LoginKeyEnum.USER_NAME.key, account);
                            loginService.saveUser(LoginKeyEnum.PWD.key, password);
                        } else {
                            loginService.deleteUser(LoginKeyEnum.USER_NAME.key);
                            loginService.deleteUser(LoginKeyEnum.PWD.key);
                        }
                        loginService.saveUser(LoginKeyEnum.REMEMBER_PWD.key, cbRememberPwd.isChecked());
                        loginService.saveUser(LoginKeyEnum.AUTO_LOGIN.key, cbAutoLogin.isChecked());
                        SystemApplication.setDataToken(result.getData().toString());
                        SystemApplication.setUserAccount(account);
                        SystemApplication.setUserPassword(password);
                        ToastUtil.makeText(LoginActivity.this, "登录成功!" + JsonUtil.toJson(result));
                        //跳转
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtil.makeText(LoginActivity.this, result.getMsg() == null ? "登录失败!" : result.getMsg());
                    }
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    mLoading.dismiss();
                    System.out.println("errorNo:" + errorNo + ",strMsg:" + strMsg + ",Throwable:" + t);
                    ToastUtil.makeText(LoginActivity.this, HttpStatusCode.getHttpStatusMsg(errorNo));
                    btnSubmit.setBackgroundResource(android.R.color.holo_blue_dark);
                    btnSubmit.setEnabled(true);
                    btnSubmit.setText("登录");
                }

                @Override
                public void onStart() {
                    mLoading.show();
                    btnSubmit.setBackgroundResource(android.R.color.darker_gray);
                    btnSubmit.setEnabled(false);
                    btnSubmit.setText("登录中...");
                    //睡眠一秒钟
                    //SystemClock.sleep(1000 * 2);
                }
            });
        } catch (Exception e) {
            ToastUtil.makeText(LoginActivity.this, "登录错误!" + e.getMessage());
        }
    }
}
