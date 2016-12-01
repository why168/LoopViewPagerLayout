package com.edwin.loopviewpager.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.base.BaseFragment;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.entity.LoopStyle;
import com.github.why168.utils.L;

import java.util.ArrayList;

/**
 * LoopViewPagerFragment
 *
 * @author Edwin.Wu
 * @version 2016/11/7 17:27
 * @since JDK1.8
 */
public class LoopViewPagerFragment extends BaseFragment implements LoopViewPagerLayout.OnBannerItemClickListener, LoopViewPagerLayout.OnLoadImageViewListener {
    private static final String LOOP_TYPE = "loop_type";
    private LoopViewPagerLayout mLoopViewPagerLayout;

    public static LoopViewPagerFragment getInstance(int loopStyle) {
        LoopViewPagerFragment instance = new LoopViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LOOP_TYPE, loopStyle);
        instance.setArguments(bundle);
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
        //TODO 初始化View
        mLoopViewPagerLayout.initializeView();
        //TODO 设置LoopViewPager参数
        mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
        mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)

        Bundle arguments = getArguments();
        int anInt = arguments.getInt(LOOP_TYPE, -1);
        switch (anInt) {
            case -1:
                mLoopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
                break;
            case 1:
                mLoopViewPagerLayout.setLoop_style(LoopStyle.Depth);//轮播的样式-深度depth
                break;
            case 2:
                mLoopViewPagerLayout.setLoop_style(LoopStyle.Zoom);//轮播的样式-缩小zoom
                break;
        }
        L.e("LoopViewPager 参数设置完毕");
        mLoopViewPagerLayout.initializeData(mActivity);
        //TODO 准备数据
        ArrayList<LoopViewPagerLayout.BannerInfo> bannerInfos = new ArrayList<>();
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.a, "第一张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.c, "第二张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.d, "第三张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.b, "第四张图片"));
        mLoopViewPagerLayout.setLoopData(bannerInfos, this, this);
    }

    @Override
    public void onBannerClick(int index, ArrayList<LoopViewPagerLayout.BannerInfo> banner) {
        Toast.makeText(mActivity, "index = " + index + " title = " + banner.get(index).title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadImageView(ImageView view, Object object) {
        /**
         * Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！
         * Fresco：Facebook出的，天生骄傲！不是一般的强大。
         * Glide：Google推荐的图片加载库，专注于流畅的滚动。
         */
        Glide
                .with(view.getContext())
                .load(object)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(view);
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
