package com.dan.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Dan on 2018/9/19 16:44
 */
public class StudentBaseAdapter extends BaseAdapter {

    /**
     * ListView显示条数
     */
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * @param position    当前返回View的位置
     * @param convertView 缓存对象
     * @param parent      ListView对象
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
