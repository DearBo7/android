package com.dan.dome.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dan.dome.R;
import com.dan.dome.entity.User;
import com.dan.dome.fragment.base.BaseFragment;
import com.dan.ui.adapter.recycler.base.QuickRecyclerAdapter;
import com.dan.ui.widget.dragrecycler.DragSortListRecycler;
import com.dan.ui.widget.dragrecycler.DragSortListRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Bo on 2019/2/19 14:08
 */
public class OtherListFragment extends BaseFragment {

    private static final String TAG = "OtherListFragment";

    @BindView(R.id.recycler_view)
    DragSortListRecycler dragSortListRecycler;

    private DragSortListRecyclerAdapter<User> sortListRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_list_fragment, null);
        super.initCreateView(view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        //添加分割线
        dragSortListRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        sortListRecyclerAdapter = new DragSortListRecyclerAdapter<User>(getContext(), R.layout.recycler_item_book_shelf) {

            @Override
            public void onBindViewHolder(QuickViewHolder viewHolder, User user, int position) {
                viewHolder.setText(R.id.tv_book_name, user.getName());
            }
        };
        dragSortListRecycler.setAdapter(sortListRecyclerAdapter);

    }

    private void initData() {
        List<User> userList = new ArrayList<>();
        User user;
        for (int i = 1; i <= 50; i++) {
            user = new User();
            user.setName("名称：" + i);
            userList.add(user);
        }
        sortListRecyclerAdapter.setData(userList);
    }

    private void initListener() {
        sortListRecyclerAdapter.setOnItemClickListener(new QuickRecyclerAdapter.OnItemClickListener<User>() {
            @Override
            public void onItemClick(View view, User user, int position) {
                System.out.println("setOnItemClickListener===>" + user.getName());
            }
        });
        sortListRecyclerAdapter.setOnItemLongClickListener(new QuickRecyclerAdapter.OnItemLongClickListener<User>() {
            @Override
            public void onItemLongClick(View view, User user, int position) {
                System.out.println("setOnItemLongClickListener===>" + user.getName());
            }
        });
    }


    @Override
    protected void setShowOrHide() {
        mainActivity.tvMainTitle.setText("其他列表");
    }
}
