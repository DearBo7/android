package com.dan.dome.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan.dome.R;
import com.dan.dome.fragment.base.BaseFragment;

/**
 * Created by Dan on 2019/2/19 14:08
 */
public class OtherListFragment extends BaseFragment {
    private static final String TAG = "OtherListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_list_fragment, null);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {

    }

    private void initListener() {

    }

    private void setData() {

    }

    @Override
    protected void setShowOrHide() {
        mainActivity.tvMainTitle.setText("其他列表");
    }
}
