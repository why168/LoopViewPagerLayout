package com.github.why168.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.why168.LoopViewPagerLayout;

import java.util.ArrayList;

/**
 * LoopPagerAdapterWrapper
 *
 * @author Edwin.Wu
 * @version 2016/12/1 17:48
 * @since JDK1.8
 */
public class LoopPagerAdapterWrapper extends PagerAdapter {
    private Context context;
    private ArrayList<LoopViewPagerLayout.BannerInfo> bannerInfos;//banner data
    private LoopViewPagerLayout.OnBannerItemClickListener onBannerItemClickListener = null;
    private LoopViewPagerLayout.OnLoadImageViewListener onLoadImageViewListener = null;

    public LoopPagerAdapterWrapper(Context context, ArrayList<LoopViewPagerLayout.BannerInfo> bannerInfos, LoopViewPagerLayout.OnBannerItemClickListener onBannerItemClickListener, LoopViewPagerLayout.OnLoadImageViewListener onLoadImageViewListener) {
        this.context = context;
        this.bannerInfos = bannerInfos;
        this.onBannerItemClickListener = onBannerItemClickListener;
        this.onLoadImageViewListener = onLoadImageViewListener;
    }

    @Override
    public int getCount() {
        return Short.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int index = position % bannerInfos.size();
        final LoopViewPagerLayout.BannerInfo bannerInfo = bannerInfos.get(index);
        final ImageView child = new ImageView(context);
        if (onLoadImageViewListener != null) {
            onLoadImageViewListener.onLoadImageView(child, bannerInfo.url);
        }
        child.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(child);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null)
                    onBannerItemClickListener.onBannerClick(index, bannerInfos);
            }
        });
        return child;
    }
}