package com.dan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dan.ui.R;
import com.dan.ui.adapter.impl.SpinnerTextFormatter;

import java.util.List;


public class SearchSelectAdapter<T> extends BaseAdapter {
    private List<T> mList;
    private Context context;
    private LayoutInflater inflater;
    private SpinnerTextFormatter spinnerTextFormatter;

    public SearchSelectAdapter(Context ctx, List<T> mList) {
        this(ctx, mList, new SimpleSpinnerTextFormatter());
    }

    public SearchSelectAdapter(Context ctx, List<T> mList, SpinnerTextFormatter spinnerTextFormatter) {
        this.context = ctx;
        this.mList = mList;
        this.inflater = LayoutInflater.from(ctx);
        this.spinnerTextFormatter = spinnerTextFormatter;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_select_single, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.info.setText(spinnerTextFormatter.format(mList.get(i)).toString());
        return view;
    }


    static class ViewHolder {
        TextView info;

        public ViewHolder(View view) {
            info = view.findViewById(R.id.tv_select_info);
        }
    }

}
