package com.dan.dome.networks;

import android.util.Log;

import com.dan.library.entity.AjaxResult;
import com.dan.library.networks.HttpStatusCode;
import com.dan.library.util.JsonUtil;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.exception.ApiException;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Dan on 2019/3/29 9:21
 */
public abstract class AjaxResultCallBack<T> extends CallBack<String> {

    private static final String TAG = "AjaxResultCallBack";

    @Override
    public void onStart() {
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(ApiException e) {
        if (e.getCode() < ApiException.UNKNOWN) {
            e.setDisplayMessage(HttpStatusCode.getHttpStatusMsg(e.getCode()));
        } else {
            e.setDisplayMessage(e.getMessage());
        }
    }

    @Override
    public void onSuccess(String json) {
        AjaxResult ajaxResult = null;
        if (StringUtils.isNotBlank(json)) {
            try {
                ajaxResult = JsonUtil.fromJson(json, AjaxResult.class);
            } catch (Exception e) {
                Log.i(TAG, "onSuccess:json:" + json + ",解析异常:" + e.getMessage());
            }
        }
        onSuccessResult(ajaxResult, json);

    }

    public abstract void onSuccessResult(AjaxResult result, String str);
}
