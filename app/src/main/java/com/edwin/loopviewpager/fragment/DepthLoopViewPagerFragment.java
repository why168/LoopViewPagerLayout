package com.edwin.loopviewpager.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.base.BaseFragment;
import com.edwin.loopviewpager.loader.OnPicassoImageViewLoader;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.listener.OnBannerItemClickListener;
import com.github.why168.modle.BannerInfo;
import com.github.why168.modle.IndicatorLocation;
import com.github.why168.modle.LoopStyle;
import com.github.why168.utils.L;

import java.util.ArrayList;

/**
 * Depth
 * LoopViewPagerFragment
 *
 * @author Edwin.Wu
 * @version 2016/11/7 17:27
 * @since JDK11
 */
public class DepthLoopViewPagerFragment extends BaseFragment implements OnBannerItemClickListener {
    private LoopViewPagerLayout mLoopViewPagerLayout;

    public static DepthLoopViewPagerFragment getInstance() {
        return new DepthLoopViewPagerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_loop_viewpager;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mLoopViewPagerLayout = (LoopViewPagerLayout) view.findViewById(R.id.mLoopViewPagerLayout);
    }

    @Override
    protected void initData() {
        // 设置LoopViewPager参数
        mLoopViewPagerLayout.setDebug(true);
        mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
        mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)
        mLoopViewPagerLayout.setLoop_style(LoopStyle.Depth);//轮播的样式-深度depth
        mLoopViewPagerLayout.setIndicatorLocation(IndicatorLocation.Left);//指示器位置-左Left
        L.e("LoopViewPager Depth 参数设置完毕");

        // 初始化
        mLoopViewPagerLayout.initializeData(mActivity);

        // 准备数据
        ArrayList<BannerInfo> bannerInfos = new ArrayList<>();
        bannerInfos.add(new BannerInfo<String>("http://t1.mmonly.cc/uploads/tu/201710/9999/84ca7d2fb4.jpg", "第一张图片"));
        bannerInfos.add(new BannerInfo<String>("http://t1.mmonly.cc/uploads/tu/201710/9999/70d59c5cac.jpg", "第二张图片"));
        bannerInfos.add(new BannerInfo<String>("http://t1.mmonly.cc/uploads/tu/zyf/tt/20160324/hbanh31xprw.jpg", "第三张图片"));
        bannerInfos.add(new BannerInfo<String>("http://t1.mmonly.cc/uploads/allimg/tuku2/16321JO7-0.jpg", "第四张图片"));
        bannerInfos.add(new BannerInfo<String>("http://t1.mmonly.cc/uploads/tu/bj/20160304/afy1mp4cji2.jpg", "第五张图片"));

        // 设置监听
        mLoopViewPagerLayout.setOnLoadImageViewListener(new OnPicassoImageViewLoader());
        mLoopViewPagerLayout.setOnBannerItemClickListener(this);

        // 设置数据
        mLoopViewPagerLayout.setLoopData(bannerInfos);
    }

    @Override
    public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
        Toast.makeText(mActivity, "index = " + index + " title = " + banner.get(index).title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        // 开始循环
        mLoopViewPagerLayout.startLoop();
        super.onStart();
    }

    @Override
    public void onStop() {
        // 停止循环
        mLoopViewPagerLayout.stopLoop();
        super.onStop();
    }


}
