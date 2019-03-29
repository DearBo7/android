package com.dan.ui.widget.switchs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dan.ui.R;
import com.dan.ui.adapter.SimpleSpinnerTextFormatter;
import com.dan.ui.adapter.SimpleTextAdapter;
import com.dan.ui.adapter.impl.SpinnerTextFormatter;
import com.dan.ui.autoline.AutoWrapLineLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dan on 2019/3/28 9:22
 */
public class LinearSwitchButton<T> extends LinearLayout {

    private Context context;

    /**
     * 左边
     */
    private ListView mListViewLeft;
    /**
     * 右边
     */
    private AutoWrapLineLayout mAutoWrapLineLayoutRight;

    /**
     * Key:显示左边,value显示右边
     */
    private Map<String, List<T>> groupMap = new LinkedHashMap<>();
    /**
     * 左边集合
     */
    private List<String> groupList = new LinkedList<String>();
    private SimpleTextAdapter regionListViewAdapterLeft;
    private SpinnerTextFormatter regionSpinnerTextFormatterLeft = new SimpleSpinnerTextFormatter();
    /**
     * 右边集合
     */
    private LinkedList<T> childrenItem = new LinkedList<T>();

    /**
     * key 为对象
     */
    private List<T> groupLeftList = new ArrayList<>();

    private int lastRegionIndex = -1;
    private int lastPlateIndex = -1;
    private String showString;
    private T selectItem;

    private OnSelectListener mOnSelectListener;

    public LinearSwitchButton(Context context) {
        super(context);
        init(context);
    }

    public LinearSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_multiple_button, this, true);
        mListViewLeft = findViewById(R.id.listViewLeft);
        mAutoWrapLineLayoutRight = findViewById(R.id.fl_Right);
        setBackground(getResources().getDrawable(R.drawable.choosearea_bg_left));
    }

    /**
     * 创建左边默认的构造
     */
    private void defaultCreateRegionListViewAdapterLeft() {
        regionListViewAdapterLeft = new SimpleTextAdapter(context, groupLeftList, R.drawable.choose_item_selected, R.drawable.item_etv_choose_ear_selector);
        regionListViewAdapterLeft.setTextSize(16);
        regionListViewAdapterLeft.setTextFormatter(regionSpinnerTextFormatterLeft);

        regionListViewAdapterLeft.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String selectedText, Object o) {
                lastRegionIndex = position;
                if (null != mOnSelectListener) {
                    mOnSelectListener.onLeftItemClick(o, position, mAutoWrapLineLayoutRight);
                }
            }
        });
        setRegionAdapterLeft(regionListViewAdapterLeft);
    }

    public void setDataSource(Map<String, List<T>> groupMapValue) {
        if (groupMapValue == null) {
            return;
        }
        //清空旧值
        if (!groupMap.isEmpty()) {
            groupMap.clear();
        }
        if (!groupList.isEmpty()) {
            groupList.clear();
        }
        //设置新值
        groupMap.putAll(groupMapValue);
        groupList.addAll(new LinkedList<String>(groupMap.keySet()));

        regionListViewAdapterLeft = new SimpleTextAdapter(context, groupList, R.drawable.choose_item_selected, com.dan.ui.R.drawable.item_etv_choose_ear_selector);
        regionListViewAdapterLeft.setTextSize(16);
        regionListViewAdapterLeft.setTextFormatter(regionSpinnerTextFormatterLeft);

        regionListViewAdapterLeft.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String selectedText, Object o) {
                lastRegionIndex = position;
                if (groupMap.containsKey(selectedText)) {
                    childrenItem.clear();
                    childrenItem.addAll(groupMap.get(selectedText));
                    //删除右边页面上的布局
                    mAutoWrapLineLayoutRight.removeAllViews();
                    System.out.println("mAutoWrapLineLayoutRight.removeAllViews()===childrenItem.size():" + childrenItem.size());
                    //View tv = LayoutInflater.from(getContext()).inflate(R.layout.item_switch_button, null);
                    //绑定右边控件
                    if (null != mOnSelectListener && null != mAutoWrapLineLayoutRight) {
                        mOnSelectListener.onLeftItemClick(o, position, mAutoWrapLineLayoutRight);
                    }
                }
            }
        });
        setRegionAdapterLeft(regionListViewAdapterLeft);
    }

    public void setDataSourceLeft(List<T> groupLeft) {
        if (groupLeft == null) {
            return;
        }
        //清空旧值
        if (!groupLeftList.isEmpty()) {
            groupLeftList.clear();
        }
        //设置新值
        groupLeftList.addAll(groupLeft);
        defaultCreateRegionListViewAdapterLeft();
        //清空右边数据
        if (null != mAutoWrapLineLayoutRight) {
            mAutoWrapLineLayoutRight.removeAllViews();
        }
    }

    public void setDataSourceLeftOrUpdate(List<T> groupLeft) {

        setDataSourceLeftOrUpdate(groupLeft, true);
    }

    public void setDataSourceLeftOrUpdate(List<T> groupLeft, boolean rightClearFlag) {
        if (groupLeft == null) {
            return;
        }
        //清空旧值
        if (!groupLeftList.isEmpty()) {
            groupLeftList.clear();
        }
        //设置新值
        groupLeftList.addAll(groupLeft);
        if (null == regionListViewAdapterLeft) {
            defaultCreateRegionListViewAdapterLeft();
        } else {
            //更新数据
            regionListViewAdapterLeft.notifyDataSetChanged();
        }
        if (rightClearFlag && null != mAutoWrapLineLayoutRight) {
            //清空右边数据
            mAutoWrapLineLayoutRight.removeAllViews();
        }
    }

    public void setSpinnerTextFormatterLeft(SpinnerTextFormatter formatter) {
        this.regionSpinnerTextFormatterLeft = formatter;
    }

    public void setRegionAdapterLeft(SimpleTextAdapter regionAdapter) {
        mListViewLeft.setAdapter(regionAdapter);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener<T> {

        void onLeftItemClick(T t, int position, AutoWrapLineLayout autoWrapLineLayout);
    }
}
