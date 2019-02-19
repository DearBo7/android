package com.dan.dome.activity.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.dan.dome.R;
import com.dan.library.util.DialogUtils;
import com.dan.library.util.StatusBarUtils;

import net.tsz.afinal.FinalActivity;

/**
 * Created by Dan on 2018/10/17 13:06
 */
public class BaseFinalActivity extends FinalActivity {

    public Dialog mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.head_background_back_all);
        ActivityCollector.addActivity(this);

        mLoading= DialogUtils.createLoadingDialog(this);
        //软件盘自动打开
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
