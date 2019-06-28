package com.dan.library.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dan on 2019/1/18 17:51
 */
public class MySpinnerEntity<T extends BaseSpinnerEntity> {

    private List<T> spinnerDataList;

    public MySpinnerEntity(List<T> data) {
        spinnerDataList = data;
    }

    public MySpinnerEntity(T[] data) {
        spinnerDataList = new ArrayList<>();
        spinnerDataList.addAll(Arrays.asList(data));
    }

    public List<String> toMsgList() {
        List<String> returnList = new ArrayList<>();
        if (spinnerDataList != null) {
            for (T t : spinnerDataList) {
                returnList.add(t.toShowMsg());
            }
        }
        return returnList;
    }
}
