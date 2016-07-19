package com.keegan.kandroid;

import android.app.Application;

import com.keegan.core.AppAction;
import com.keegan.core.AppActionImpl;

/**
 * Application类，应用级别的操作都放这里
 *
 * @version 1.0 创建时间：15/6/25
 */
public class KApplication extends Application {

    private AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();
        appAction = new AppActionImpl(this);
    }

    public AppAction getAppAction() {
        return appAction;
    }
}
