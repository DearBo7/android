package com.dan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import com.dan.entity.Test1Entity;
import com.dan.testday3.R;

/**
 * Created by Dan on 2018/9/17 10:40
 */
public class Test1Adapter extends ArrayAdapter<Test1Entity> {

    private final LayoutInflater mInflater;
    private int resourceId;

    public Test1Adapter(@NonNull Context context, int resource, @NonNull List<Test1Entity> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 目前运行效率是很低的，每次都将布局重新加载了一遍，当 ListView 快速滚动时，就会成为性能的瓶颈。
     * getView() 方法中有一个 convertView 参数，这个参数会将之前加载好的布局进行缓存，以便之后可以进行重用。
     */
    /*@NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取当前项的实例
        Test1Entity entity = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //((ImageView) view.findViewById(R.id.image)).setImageResource(cat.getImageId());
        //((TextView) view.findViewById(R.id.name)).setText(cat.getName());

        return view;
    }*/

    /**
     * 提升运行效率
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        //获取当前项的实例
        Test1Entity entity = getItem(position);
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(resourceId, parent, false);
            viewHolder.image = convertView.findViewById(R.id.tu_1);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.score = convertView.findViewById(R.id.score);
            viewHolder.episode = convertView.findViewById(R.id.episode);
            viewHolder.ratingBar = convertView.findViewById(R.id.ratingBar);
            //保存
            convertView.setTag(viewHolder);
        } else {
            //取出
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setImageResource(entity.getImageId());
        viewHolder.title.setText(entity.getTitle());
        viewHolder.score.setText(entity.getScore().toString());
        viewHolder.episode.setText("集数：" + entity.getEpisode());
        viewHolder.ratingBar.setRating(entity.getScore().floatValue());

        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView title;
        TextView score;
        TextView episode;
        RatingBar ratingBar;
    }
}
