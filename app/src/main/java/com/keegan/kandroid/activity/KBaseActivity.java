package com.keegan.kandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.keegan.core.AppAction;
import com.keegan.kandroid.KApplication;

/**
 * Activity抽象基类
 *
 * @version 1.0 创建时间：15/6/26
 */
public abstract class KBaseActivity extends FragmentActivity {
    // 上下文实例
    public Context context; //为了给子类Activitity使用，context = getApplicationContext();
    // 应用全局的实例
    public KApplication application;
    // 核心层的Action实例
    public AppAction appAction;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        context = getApplicationContext();
        application = (KApplication) this.getApplication();
        appAction = application.getAppAction();

        initVariables();
        initViews(saveInstanceState);
        loadData();
    }

    protected abstract void initVariables();
    protected abstract void initViews(Bundle saveInstanceState);
    protected abstract void loadData();
}
