package com.edwin.loopviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.edwin.loopviewpager.lib.LoopViewPagerLayout;

import java.util.ArrayList;

/**
 * 展示界面
 *
 * @USER Edwin
 * @DATE 16/6/15 上午12:17
 */
public class MainActivity extends AppCompatActivity implements LoopViewPagerLayout.OnBannerItemClickListener {
    private LoopViewPagerLayout mLoopViewPager1;
    private LoopViewPagerLayout mLoopViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    private void initView() {
        mLoopViewPager1 = (LoopViewPagerLayout) findViewById(R.id.view_main_loopViewPager1);
        mLoopViewPager2 = (LoopViewPagerLayout) findViewById(R.id.view_main_loopViewPager2);
    }

    private void initData() {
        ArrayList<LoopViewPagerLayout.BannerInfo> bannerInfos = new ArrayList<>(4);
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo(R.mipmap.a, "第一张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo(R.mipmap.b, "第二张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo(R.mipmap.d, "第三张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo(R.mipmap.c, "第四张图片"));
        mLoopViewPager1.setLoopData(bannerInfos, this);
        mLoopViewPager2.setLoopData(bannerInfos, this);
    }


    @Override
    protected void onStart() {
        //TODO 开始循环
        mLoopViewPager1.startLoop();
        mLoopViewPager2.startLoop();
        super.onStart();


    }

    @Override
    protected void onStop() {
        //TODO 停止循环
        mLoopViewPager1.stopLoop();
        mLoopViewPager2.stopLoop();
        super.onStop();
    }


    @Override
    public void onBannerClick(int index, ArrayList<LoopViewPagerLayout.BannerInfo> banner) {
        LoopViewPagerLayout.BannerInfo bannerInfo = banner.get(index);
        Toast.makeText(this, bannerInfo.title, Toast.LENGTH_SHORT).show();
    }
}
