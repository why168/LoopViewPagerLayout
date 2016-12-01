package com.edwin.loopviewpager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.adapter.ListHeadViewAdapter;
import com.edwin.loopviewpager.base.BaseFragment;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.entity.LoopStyle;

import java.util.ArrayList;

/**
 * ListView HeadView add LoopViewPagerLayout
 *
 * @author Edwin.Wu
 * @version 2016/11/8 16:46
 * @since JDK1.8
 */
public class ListHeadViewFragment extends BaseFragment implements LoopViewPagerLayout.OnBannerItemClickListener, LoopViewPagerLayout.OnLoadImageViewListener {
    private ListView mListView;
    private LoopViewPagerLayout mLoopViewPagerLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_headview;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mListView = (ListView) view.findViewById(R.id.mListView_list);
    }

    @Override
    protected void initData() {
        mListView.setAdapter(new ListHeadViewAdapter(getContext()));
        View inflateView = LayoutInflater.from(mActivity).inflate(R.layout.item_banner, null);
        mLoopViewPagerLayout = (LoopViewPagerLayout) inflateView.findViewById(R.id.mLoopViewPagerLayout_banner);

        //TODO 初始化View
        mLoopViewPagerLayout.initializeView();
        //TODO 设置LoopViewPager参数
        mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
        mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)
        mLoopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty

        mLoopViewPagerLayout.initializeData(mActivity);
        //TODO 准备数据
        ArrayList<LoopViewPagerLayout.BannerInfo> bannerInfos = new ArrayList<>(4);
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.a, "第一张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.c, "第二张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<String>("https://avatars2.githubusercontent.com/u/13330076?v=3&u=33de3c989c70716d321d79a99b8d176c7b88349e&s=400", "第三张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.b, "第四张图片"));
        mLoopViewPagerLayout.setLoopData(bannerInfos, this, this);
        mListView.addHeaderView(mLoopViewPagerLayout);
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
}
