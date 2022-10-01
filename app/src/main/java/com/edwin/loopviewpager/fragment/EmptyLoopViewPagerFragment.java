package com.edwin.loopviewpager.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.base.BaseFragment;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.listener.OnBannerItemClickListener;
import com.github.why168.loader.OnDefaultImageViewLoader;
import com.github.why168.modle.BannerInfo;
import com.github.why168.modle.IndicatorLocation;
import com.github.why168.modle.LoopStyle;
import com.github.why168.utils.L;

import java.util.ArrayList;

/**
 * Empty
 * LoopViewPagerFragment
 *
 * @author Edwin.Wu
 * @version 2016/11/7 17:27
 * @since JDK11
 */
public class EmptyLoopViewPagerFragment extends BaseFragment implements OnBannerItemClickListener {
    private LoopViewPagerLayout mLoopViewPagerLayout;

    public static EmptyLoopViewPagerFragment getInstance() {
        return new EmptyLoopViewPagerFragment();
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
        mLoopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        mLoopViewPagerLayout.setIndicatorLocation(IndicatorLocation.Center);//指示器位置-中Center
        mLoopViewPagerLayout.setNormalBackground(R.drawable.normal_background);//默认指示器颜色
        mLoopViewPagerLayout.setSelectedBackground(R.drawable.selected_background);//选中指示器颜色
        L.e("LoopViewPager Empty 参数设置完毕");

        // 初始化
        mLoopViewPagerLayout.initializeData(mActivity);

        // 准备数据
        ArrayList<BannerInfo> bannerInfos = new ArrayList<>();
        bannerInfos.add(new BannerInfo<Integer>(R.mipmap.a, "第一张图片"));
        bannerInfos.add(new BannerInfo<String>("https://avatars2.githubusercontent.com/u/13330076?v=3&u=33de3c989c70716d321d79a99b8d176c7b88349e&s=400", "第二张图片"));
        bannerInfos.add(new BannerInfo<Integer>(R.mipmap.b, "第三张图片"));
        bannerInfos.add(new BannerInfo<Integer>(R.mipmap.c, "第四张图片"));
        bannerInfos.add(new BannerInfo<Integer>(R.mipmap.d, "第五张图片"));

        // 设置监听
        mLoopViewPagerLayout.setOnLoadImageViewListener(new OnDefaultImageViewLoader() {
            @Override
            public void onLoadImageView(ImageView view, Object object) {
                Glide
                        .with(view.getContext())
                        .load(object)
                        .centerCrop()
                        .error(R.mipmap.ic_launcher)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(view);
            }
        });
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
