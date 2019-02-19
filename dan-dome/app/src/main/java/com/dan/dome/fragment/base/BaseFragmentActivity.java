package com.dan.dome.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.dan.dome.R;
import com.dan.library.util.StatusBarUtils;

/**
 * Created by Dan on 2019/2/19 13:47
 */
public class BaseFragmentActivity extends FragmentActivity {

    protected AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertDialog = new AlertDialog.Builder(getApplicationContext());
        StatusBarUtils.setWindowStatusBarColor(this, R.color.head_background_back_all);
        //软件盘自动打开
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
}
