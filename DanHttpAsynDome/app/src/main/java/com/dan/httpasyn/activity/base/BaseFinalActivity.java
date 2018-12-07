package com.dan.httpasyn.activity.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dan.httpasyn.util.DialogUtils;

import net.tsz.afinal.FinalActivity;

/**
 * Created by Dan on 2018/10/17 13:06
 */
public class BaseFinalActivity extends FinalActivity {

    public Dialog mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        mLoading= DialogUtils.createLoadingDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
