package com.dan.ui.widget.dragrecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.dan.ui.adapter.recycler.RecyclerViewUtil;
import com.dan.ui.widget.dragrecycler.base.DragTouchHelper;
import com.dan.ui.widget.dragrecycler.base.OnItemDragSortListener;
import com.dan.ui.widget.dragrecycler.base.TouchCallback;

import java.util.Collections;

/**
 * Created by Dan on 2019/7/15 12:37
 */
public class DragSortListRecycler extends RecyclerView implements OnItemDragSortListener {

    private final TouchCallback touchCallback;
    /**
     * 拖拽操作帮助类
     */
    private DragTouchHelper dragTouchHelper;

    public DragSortListRecycler(@NonNull Context context) {
        this(context, null);
    }

    public DragSortListRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragSortListRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setItemAnimator(new DefaultItemAnimator());

        touchCallback = new TouchCallback(this);

        dragTouchHelper = new DragTouchHelper(touchCallback);

        dragTouchHelper.attachToRecyclerView(this);

        RecyclerViewUtil.vertical(context, this);

        init();
    }

    private void init() {
        //设置默认允许长按拖动
        touchCallback.setEnableDrag(true);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof DragSortListRecyclerAdapter) {
            DragSortListRecyclerAdapter listAdapter = (DragSortListRecyclerAdapter) adapter;
            listAdapter.setOnItemDragSortListener(this);
        }
    }

    @Override
    public void onStartDrags(ViewHolder viewHolder) {
        dragTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onSwiped(int position) {
        //处理划动删除操作
        DragSortListRecyclerAdapter mAdapter = (DragSortListRecyclerAdapter) getAdapter();
        if (mAdapter != null && mAdapter.checkIndex(position)) {
            mAdapter.remove(position);
        }
    }

    @Override
    public boolean onItemMove(int srcPosition, int targetPosition) {
        //处理拖拽事件
        DragSortListRecyclerAdapter mAdapter = (DragSortListRecyclerAdapter) getAdapter();
        if (mAdapter == null || mAdapter.getDataList() == null || mAdapter.getDataList().size() == 0) {
            return false;
        }
        if (mAdapter.checkIndex(srcPosition) && mAdapter.checkIndex(targetPosition)) {
            //交换数据源两个数据的位置
            Collections.swap(mAdapter.getDataList(), srcPosition, targetPosition);
            //更新视图
            mAdapter.notifyItemMoved(srcPosition, targetPosition);
            //消费事件
            return true;
        }
        return false;
    }

    /**
     * 是否允许长按拖动
     *
     * @param enabledFlag true允许
     */
    public void isLongPressDrag(boolean enabledFlag) {
        touchCallback.setEnableDrag(enabledFlag);
    }

}
