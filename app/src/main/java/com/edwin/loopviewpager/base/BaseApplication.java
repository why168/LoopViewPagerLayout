package com.edwin.loopviewpager.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author Edwin.Wu
 * @version 2016/12/6 23:42
 * @since JDK1.8
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
