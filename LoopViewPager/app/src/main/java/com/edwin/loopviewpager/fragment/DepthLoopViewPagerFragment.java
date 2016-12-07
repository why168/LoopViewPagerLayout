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
 * @since JDK1.8
 */
public class DepthLoopViewPagerFragment extends BaseFragment implements OnBannerItemClickListener {
    private LoopViewPagerLayout mLoopViewPagerLayout;

    public static DepthLoopViewPagerFragment getInstance() {
        DepthLoopViewPagerFragment instance = new DepthLoopViewPagerFragment();
        return instance;
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
        //TODO 设置LoopViewPager参数
        mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
        mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)
        mLoopViewPagerLayout.setLoop_style(LoopStyle.Depth);//轮播的样式-深度depth
        mLoopViewPagerLayout.setIndicatorLocation(IndicatorLocation.Left);//指示器位置-左Left
        L.e("LoopViewPager Depth 参数设置完毕");

        //TODO 初始化
        mLoopViewPagerLayout.initializeData(mActivity);

        //TODO 准备数据
        ArrayList<BannerInfo> bannerInfos = new ArrayList<>();
        bannerInfos.add(new BannerInfo<String>("http://mm.howkuai.com/wp-content/uploads/2016a/09/01/01.jpg", "第一张图片"));
        bannerInfos.add(new BannerInfo<String>("http://mm.howkuai.com/wp-content/uploads/2016a/08/02/03.jpg", "第二张图片"));
        bannerInfos.add(new BannerInfo<String>("http://mm.howkuai.com/wp-content/uploads/2016a/08/31/01.jpg", "第三张图片"));
        bannerInfos.add(new BannerInfo<String>("http://mm.howkuai.com/wp-content/uploads/2016a/08/28/01.jpg", "第四张图片"));
        bannerInfos.add(new BannerInfo<String>("http://mm.howkuai.com/wp-content/uploads/2016a/08/02/01.jpg", "第五张图片"));

        //TODO 设置监听
        mLoopViewPagerLayout.setOnLoadImageViewListener(new OnPicassoImageViewLoader());
        mLoopViewPagerLayout.setOnBannerItemClickListener(this);

        //TODO 设置数据
        mLoopViewPagerLayout.setLoopData(bannerInfos);
    }

    @Override
    public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
        Toast.makeText(mActivity, "index = " + index + " title = " + banner.get(index).title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        //TODO 开始循环
        mLoopViewPagerLayout.startLoop();
        super.onStart();
    }

    @Override
    public void onStop() {
        //TODO 停止循环
        mLoopViewPagerLayout.stopLoop();
        super.onStop();
    }


}
