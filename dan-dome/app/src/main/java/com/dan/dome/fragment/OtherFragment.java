package com.dan.dome.fragment;

import com.dan.dome.fragment.base.BaseFragment;

/**
 * Created by Dan on 2019/2/19 14:08
 */
public class OtherFragment extends BaseFragment {

    @Override
    protected void setShowOrHide() {
        mainActivity.tvMainTitle.setText("其他");
    }
}
