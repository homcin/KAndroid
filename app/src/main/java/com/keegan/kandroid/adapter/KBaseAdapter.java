package com.keegan.kandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter抽象基类，子类需要实现getView方法，通过addItems(List<T> itemList)添加数据
 *
 * @version 1.0 创建时间：15/6/28
 */
public abstract class KBaseAdapter<T> extends BaseAdapter {

    protected Context context; //为了inflater = LayoutInflater.from(context);
    protected LayoutInflater inflater;
    protected List<T> itemList = new ArrayList<T>();

    public KBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 判断数据是否为空
     *
     * @return 为空返回true，不为空返回false
     */
    public boolean isEmpty() { return itemList.isEmpty(); }

    /**
     * 在原有的数据上添加新数据
     *
     * @param itemList
     */
    public void addItems(List<T> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    /**
     * 设置为新的数据，旧数据会被清空
     *
     * @param itemList
     */
    public void setItems(List<T> itemList) {
        this.itemList.clear();
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearItems() {
        itemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() { return itemList.size(); }

    @Override
    public Object getItem(int position) { return itemList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);
}
