package com.dan.dome.fragment.base;

import android.app.Dialog;
import android.support.v4.app.Fragment;

import com.dan.dome.MainActivity;
import com.dan.library.util.DialogUtils;

/**
 * Created by Dan on 2019/2/19 14:08
 */
public abstract class BaseFragment extends Fragment {

    public Dialog mLoading;

    public MainActivity mainActivity;

    @Override
    public void onStart() {
        super.onStart();
        mLoading = DialogUtils.createLoadingDialog(getContext());
        mainActivity = (MainActivity) getActivity();
        setShowOrHide();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setShowOrHide();
        }
    }

    protected abstract void setShowOrHide();
}
