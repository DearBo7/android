package com.dan.httpasyn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.dan.httpasyn.activity.base.BaseFinalActivity;
import com.dan.httpasyn.config.HttpConfig;
import com.dan.httpasyn.config.HttpStatusCode;
import com.dan.httpasyn.service.LoginService;
import com.dan.httpasyn.util.AjaxResult;
import com.dan.httpasyn.util.JsonUtil;
import com.dan.httpasyn.util.SystemApplication;
import com.dan.httpasyn.util.ToastUtil;

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

    private String rememberPwdKey = "rememberPwdKey";
    private String autoLoginKey = "autoLoginKey";
    private String userNameKey = "userNameKey";
    private String pwdKey = "pwdKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //表示设置当前的Activity 无Title并且全屏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        loginService = new LoginService(LoginActivity.this);
        String account = loginService.getStringValue(userNameKey);
        String password = loginService.getStringValue(pwdKey);
        //自动登录
        Boolean autoLoginFlag = loginService.getBooleanValue(autoLoginKey);
        //记住密码
        Boolean rememberPwdFlag = loginService.getBooleanValue(rememberPwdKey);
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
            http.post(HttpConfig.loginUrl, params, new AjaxCallBack<String>() {

                @Override
                public void onSuccess(String json) {
                    btnSubmit.setBackgroundResource(android.R.color.holo_purple);
                    btnSubmit.setText("登录");
                    btnSubmit.setEnabled(true);
                    mLoading.dismiss();
                    AjaxResult result = JsonUtil.fromJson(json, AjaxResult.class);
                    if (result != null && result.getCode().equals(1)) {
                        if (cbRememberPwd.isChecked() || cbAutoLogin.isChecked()) {
                            loginService.saveUser(userNameKey, account);
                            loginService.saveUser(pwdKey, password);
                        } else {
                            loginService.deleteUser(userNameKey);
                            loginService.deleteUser(pwdKey);
                        }
                        loginService.saveUser(rememberPwdKey, cbRememberPwd.isChecked());
                        loginService.saveUser(autoLoginKey, cbAutoLogin.isChecked());
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
