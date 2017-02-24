package com.edwin.loopviewpager.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.base.BaseActivity;
import com.edwin.loopviewpager.fragment.DepthLoopViewPagerFragment;
import com.edwin.loopviewpager.fragment.EmptyLoopViewPagerFragment;
import com.edwin.loopviewpager.fragment.ListHeadViewFragment;
import com.edwin.loopviewpager.fragment.ZoomLoopViewPagerFragment;

/**
 * LoopActivity
 *
 * @author Edwin.Wu
 * @version 2016/11/8 00:15
 * @since JDK1.8
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
        findViewById(R.id.button_onclick_list).setOnClickListener(this);
        text_loop_info = (TextView) findViewById(R.id.text_loop_info);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        LoopViewPagerToEmpty();
    }

    private void LoopViewPagerToEmpty() {
        EmptyLoopViewPagerFragment instance = EmptyLoopViewPagerFragment.getInstance();

        replace(instance);

        Toast.makeText(mContext, "replace  Empty successful", Toast.LENGTH_SHORT).show();
        text_loop_info.setText("This is Empty style \n  Default Loader");
    }

    private void LoopViewPagerToDepth() {
        DepthLoopViewPagerFragment instance = DepthLoopViewPagerFragment.getInstance();

        replace(instance);

        Toast.makeText(mContext, "replace  Depth successful", Toast.LENGTH_SHORT).show();
        text_loop_info.setText("This is Depth style \n  Picasso Loader");
    }

    private void LoopViewPagerToZoom() {
        ZoomLoopViewPagerFragment instance = ZoomLoopViewPagerFragment.getInstance();

        replace(instance);

        Toast.makeText(mContext, "replace  Zoom successful", Toast.LENGTH_SHORT).show();
        text_loop_info.setText("This is Zoom style \n  Fresco Loader");
    }

    private void LoopViewPagerToListView() {
        ListHeadViewFragment instance = ListHeadViewFragment.getInstance();

        replace(instance);

        Toast.makeText(mContext, "replace ListView add HeadView", Toast.LENGTH_SHORT).show();
        text_loop_info.setText("replace ListView add HeadView \n  Glide Fresco");
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
            case R.id.button_onclick_list:
                LoopViewPagerToListView();
                break;
        }
    }

    private void replace(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment);
        fragmentTransaction.commit();
    }
}
