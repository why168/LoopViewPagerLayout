package com.edwin.loopviewpager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.adapter.ListHeadViewAdapter;
import com.edwin.loopviewpager.base.BaseFragment;
import com.edwin.loopviewpager.loader.OnGlideImageViewLoader;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.listener.OnBannerItemClickListener;
import com.github.why168.modle.BannerInfo;
import com.github.why168.modle.IndicatorLocation;
import com.github.why168.modle.LoopStyle;
import com.github.why168.utils.L;

import java.util.ArrayList;

/**
 * ListView HeadView add LoopViewPagerLayout
 *
 * @author Edwin.Wu
 * @version 2016/11/8 16:46
 * @since JDK11
 */
public class ListHeadViewFragment extends BaseFragment implements OnBannerItemClickListener {
    private ListView mListView;
    private LoopViewPagerLayout mLoopViewPagerLayout;

    public static ListHeadViewFragment getInstance() {
        return new ListHeadViewFragment();
    }

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
        // 设置LoopViewPager参数
        mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
        mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)
        mLoopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        mLoopViewPagerLayout.setIndicatorLocation(IndicatorLocation.Center);//指示器位置-中Center
        L.e("LoopViewPager List 参数设置完毕");

        // 初始化
        mLoopViewPagerLayout.initializeData(mActivity);

        // 准备数据
        ArrayList<BannerInfo> bannerInfos = new ArrayList<>();
        bannerInfos.add(new BannerInfo<Integer>(R.mipmap.a, "第一张图片"));
        bannerInfos.add(new BannerInfo<String>("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2275951577,3020532777&fm=27&gp=0.jpg", "第二张图片"));
        bannerInfos.add(new BannerInfo<Integer>(R.mipmap.b, "第三张图片"));
        bannerInfos.add(new BannerInfo<Integer>(R.mipmap.c, "第四张图片"));
        bannerInfos.add(new BannerInfo<Integer>(R.mipmap.d, "第五张图片"));

        // 设置监听
        mLoopViewPagerLayout.setOnLoadImageViewListener(new OnGlideImageViewLoader());
        mLoopViewPagerLayout.setOnBannerItemClickListener(this);

        // 设置数据
        mLoopViewPagerLayout.setLoopData(bannerInfos);

        mListView.addHeaderView(mLoopViewPagerLayout);
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

    @Override
    public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
        Toast.makeText(mActivity, "index = " + index + " title = " + banner.get(index).title, Toast.LENGTH_SHORT).show();
    }
}
