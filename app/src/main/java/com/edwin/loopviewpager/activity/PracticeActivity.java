package com.edwin.loopviewpager.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.base.BaseActivity;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.listener.OnLoadImageViewListener;
import com.github.why168.modle.BannerInfo;
import com.github.why168.modle.IndicatorLocation;
import com.github.why168.modle.LoopStyle;

import java.util.ArrayList;

/**
 * 练习
 *
 * @author Edwin.Wu
 * @version 2016/11/14 17:55
 * @since JDK1.8
 */
public class PracticeActivity extends BaseActivity {
    private Button mButton;
    private LoopViewPagerLayout mLoopViewPagerLayout;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_practice);
    }

    @Override
    protected void setUpView() {
        mButton = (Button) findViewById(R.id.mButton);
        mLoopViewPagerLayout = (LoopViewPagerLayout) findViewById(R.id.mLoopViewPagerLayout);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 更新新数据
                ArrayList<BannerInfo> data = new ArrayList<>();
                data.add(new BannerInfo<Integer>(R.mipmap.d, "第一张图片"));
                data.add(new BannerInfo<Integer>(R.mipmap.d, "第三张图片"));
                data.add(new BannerInfo<Integer>(R.mipmap.d, "第五张图片"));
                mLoopViewPagerLayout.setLoopData(data);//设置数据
            }
        });

        mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
        mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)
        mLoopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        mLoopViewPagerLayout.setIndicatorLocation(IndicatorLocation.Center);//指示器位置-中Center
        mLoopViewPagerLayout.initializeData(this);//初始化数据
        ArrayList<BannerInfo> data = new ArrayList<>();
        data.add(new BannerInfo<Integer>(R.mipmap.a, "第一张图片"));
        data.add(new BannerInfo<Integer>(R.mipmap.b, "第三张图片"));
        data.add(new BannerInfo<Integer>(R.mipmap.c, "第四张图片"));
        data.add(new BannerInfo<Integer>(R.mipmap.d, "第五张图片"));
        mLoopViewPagerLayout.setOnLoadImageViewListener(new OnLoadImageViewListener() {
            @Override
            public ImageView createImageView(Context context) {
                ImageView view = new ImageView(context);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                return view;
            }

            @Override
            public void onLoadImageView(ImageView imageView, Object parameter) {
                imageView.setImageResource((Integer) parameter);
            }
        });//设置图片加载&自定义图片监听
        mLoopViewPagerLayout.setLoopData(data);//设置数据
    }
}
