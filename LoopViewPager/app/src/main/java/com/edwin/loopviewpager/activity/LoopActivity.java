package com.edwin.loopviewpager.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.base.BaseActivity;
import com.edwin.loopviewpager.fragment.LoopViewPagerFragment;
import com.github.why168.entity.LoopStyle;

/**
 * LoopActivity
 *
 * @USER Edwin
 * @DATE 2016/11/8 00:15
 */
public class LoopActivity extends BaseActivity implements View.OnClickListener {
    private TextView text_loop_info;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_loop);
    }

    @Override
    protected void setUpView() {
        findViewById(R.id.button_onclick_empty).setOnClickListener(this);
        findViewById(R.id.button_onclick_depth).setOnClickListener(this);
        findViewById(R.id.button_onclick_zoom).setOnClickListener(this);
        text_loop_info = (TextView) findViewById(R.id.text_loop_info);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        LoopViewPagerToEmpty();
    }

    private void LoopViewPagerToEmpty() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        LoopViewPagerFragment loopViewPagerFragment = LoopViewPagerFragment.getInstance(LoopStyle.Empty.getValue());
        fragmentTransaction.replace(R.id.fragment_content, loopViewPagerFragment);
        fragmentTransaction.commit();
        Toast.makeText(mContext, "replace  Empty successful", Toast.LENGTH_SHORT).show();
        text_loop_info.setText("This is Empty style");
    }

    private void LoopViewPagerToDepth() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        LoopViewPagerFragment loopViewPagerFragment = LoopViewPagerFragment.getInstance(LoopStyle.Depth.getValue());
        fragmentTransaction.replace(R.id.fragment_content, loopViewPagerFragment);
        fragmentTransaction.commit();
        Toast.makeText(mContext, "replace  Depth successful", Toast.LENGTH_SHORT).show();
        text_loop_info.setText("This is Depth style");
    }

    private void LoopViewPagerToZoom() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        LoopViewPagerFragment loopViewPagerFragment = LoopViewPagerFragment.getInstance(LoopStyle.Zoom.getValue());
        fragmentTransaction.replace(R.id.fragment_content, loopViewPagerFragment);
        fragmentTransaction.commit();
        Toast.makeText(mContext, "replace  Zoom successful", Toast.LENGTH_SHORT).show();
        text_loop_info.setText("This is Zoom style");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_onclick_empty:
                LoopViewPagerToEmpty();
                break;
            case R.id.button_onclick_depth:
                LoopViewPagerToDepth();
                break;
            case R.id.button_onclick_zoom:
                LoopViewPagerToZoom();
                break;
        }
    }
}
