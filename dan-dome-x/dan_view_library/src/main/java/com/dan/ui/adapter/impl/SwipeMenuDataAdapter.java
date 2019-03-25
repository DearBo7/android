package com.dan.ui.adapter.impl;

import java.util.List;

/**
 * Created by Dan on 2019/2/25 15:38
 */
public interface SwipeMenuDataAdapter<T> {

    void addFirst(T item);

    void addFirst(List<T> collection);

    void add(T item);

    void add(List<T> collection);

    boolean update(T item, int index);

    boolean remove(int index);

    boolean remove();

    List<T> getDataAll();

    void setData(List<T> collection);

    void clear();

}
