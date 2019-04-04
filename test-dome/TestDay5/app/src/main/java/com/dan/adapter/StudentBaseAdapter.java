package com.dan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dan.day5.R;

import java.util.List;

/**
 * Created by Dan on 2018/9/19 16:44
 */
public class StudentBaseAdapter extends BaseAdapter {

    private List<String> stringList;
    private final LayoutInflater mInflater;
    private int resourceId;

    public StudentBaseAdapter(Context context, int resourceId, List<String> stringList) {
        this.stringList = stringList;
        this.resourceId = resourceId;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * ListView显示条数
     */
    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 重新计算listview的高度
     */
    public void setListViewParams(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * @param position    当前返回View的位置
     * @param convertView 缓存对象
     * @param parent      ListView对象
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object item = getItem(position);
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(resourceId, parent, false);
            viewHolder.text = convertView.findViewById(R.id.tx_id);
            //保存
            convertView.setTag(viewHolder);
        } else {
            //取出
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text.setText(String.valueOf(item));
        return convertView;
    }

    static class ViewHolder {
        TextView text;
    }
}
