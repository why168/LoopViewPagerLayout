package com.edwin.loopviewpager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Base
 * Activity
 *
 * @author Edwin.Wu
 * @version 2016/11/7 17:16
 * @since JDK1.8
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setUpContentView();
        setUpView();
        setUpData(savedInstanceState);
    }


    protected abstract void setUpContentView();

    protected abstract void setUpView();

    protected abstract void setUpData(Bundle savedInstanceState);

}
