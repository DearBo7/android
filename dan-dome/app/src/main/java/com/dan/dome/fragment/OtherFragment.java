package com.dan.dome.fragment;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan.dome.R;
import com.dan.dome.entity.Material;
import com.dan.dome.fragment.base.BaseFragment;
import com.dan.expand.adapter.SimpleSpinnerTextFormatter;
import com.dan.expand.view.ExpandTabView;
import com.dan.expand.view.ViewMiddle;
import com.dan.library.util.JsonUtil;
import com.dan.library.util.ToastUtil;

import org.angmarch.views.NiceSpinner;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dan on 2019/2/19 14:08
 */
public class OtherFragment extends BaseFragment {
    private static final String TAG = "OtherFragment";
    private NiceSpinner niceSpinner;

    private ExpandTabView expandTabView;
    private List<View> mViewArray = new ArrayList<View>();
    private ViewMiddle viewMiddle;
    private Map<String, List<Material>> mapList = new LinkedHashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_fragment, null);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        niceSpinner = view.findViewById(R.id.nice_spinner);
        expandTabView = view.findViewById(R.id.etv_list);

        viewMiddle = new ViewMiddle(getContext());
        mViewArray.add(viewMiddle);
        //设置创建时按钮禁用
        expandTabView.setCreateToggleButtonEnabled(false);
        expandTabView.setValue(Arrays.asList("原料加载中..."), mViewArray);
        //设置右边显示数据,格式化
        viewMiddle.setPlateTextFormatter(new SimpleSpinnerTextFormatter() {
            @Override
            public Spannable format(Object item) {
                String value;
                if (item instanceof Material) {
                    value = ((Material) item).getMaterialName();
                } else {
                    value = item.toString();
                }
                return new SpannableString(value);
            }
        });
        setData();
    }

    private void initListener() {
        //设置下拉框监听
        viewMiddle.setOnSelectListener((showText, o) -> {
            expandTabView.onPressBack();
            //就新增了一个选择原料，所以直接设置0
            int index = 0;
            if (!expandTabView.getTitle(index).equals(showText)) {
                expandTabView.setTitle(showText, index);
                if (o instanceof Material) {
                    Material material = (Material) o;
                    Log.i(TAG, "选择material:" + JsonUtil.toJson(material));
                    ToastUtil.makeText(getContext(), "选择material:" + JsonUtil.toJson(material));
                }
            }
        });
    }

    private void setData() {
        List<String> dataset = new ArrayList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);

        List<Material> materialList = null;
        Material user = null;
        long id = 0;
        for (int i = 1; i <= 27; i++) {
            materialList = new ArrayList<>();
            for (int j = 1; j <= 50; j++) {
                user = new Material();
                id = i + j;
                user.setId(id);
                user.setMaterialName("MaterialName:i->" + i + "===j->" + j);
                user.setMaterialCode("MaterialCode:i->" + i + "===j->" + j);
                materialList.add(user);
            }
            mapList.put(i + "Key", materialList);
        }
        viewMiddle.setDataSource(mapList);

        //设置选中第一个
        Material material = (Material) viewMiddle.setPlateDefaultSelectAndValue(0, 0);
        if (material == null) {
            return;
        }
        //有数据时,取消禁用
        expandTabView.setToggleButton(true);
        Log.i(TAG, "设置默认选中:material:" + JsonUtil.toJson(material));
        ToastUtil.makeText(getContext(), "设置默认选中:material:" + JsonUtil.toJson(material));
        if (StringUtils.isNotBlank(viewMiddle.getShowText())) {
            expandTabView.setTitle(viewMiddle.getShowText(), 0);
        }
    }

    @Override
    protected void setShowOrHide() {
        mainActivity.tvMainTitle.setText("其他");
    }
}
