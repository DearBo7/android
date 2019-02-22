package com.dan.expand.adapter;

import android.text.Spannable;
import android.text.SpannableString;

/**
 * Created by Dan on 2019/2/21 13:38
 */
public class SimpleSpinnerTextFormatter implements SpinnerTextFormatter {

    @Override
    public Spannable format(String text) {
        return new SpannableString(text);
    }

    @Override
    public Spannable format(Object item) {
        return new SpannableString(item.toString());
    }
}
