package com.github.why168.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.github.why168.listener.OnBannerItemClickListener;
import com.github.why168.listener.OnLoadImageViewListener;
import com.github.why168.modle.BannerInfo;

import java.util.ArrayList;

/**
 * LoopPagerAdapterWrapper
 *
 * @author Edwin.Wu
 * @version 2016/12/1 17:48
 * @since JDK11
 */
public class LoopPagerAdapterWrapper extends PagerAdapter {
    private final Context context;
    private final ArrayList<BannerInfo> bannerInfos;//banner data
    private final OnBannerItemClickListener onBannerItemClickListener;
    private final OnLoadImageViewListener onLoadImageViewListener;

    public LoopPagerAdapterWrapper(Context context, ArrayList<BannerInfo> bannerInfos, OnBannerItemClickListener onBannerItemClickListener, OnLoadImageViewListener onLoadImageViewListener) {
        this.context = context;
        this.bannerInfos = bannerInfos;
        this.onBannerItemClickListener = onBannerItemClickListener;
        this.onLoadImageViewListener = onLoadImageViewListener;
    }


    @Override
    public int getCount() {
        // 使用更合理的数值实现无限循环，避免性能问题
        // 1000 * bannerInfos.size() 足以提供良好的循环体验
        return bannerInfos == null || bannerInfos.size() == 0 ? 0 : 1000 * bannerInfos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position,@NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final int index = position % bannerInfos.size();
        final BannerInfo bannerInfo = bannerInfos.get(index);
        ImageView child = null;
        if (onLoadImageViewListener != null) {
            child = onLoadImageViewListener.createImageView(context);
            onLoadImageViewListener.onLoadImageView(child, bannerInfo.data);
            container.addView(child);

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBannerItemClickListener != null)
                        onBannerItemClickListener.onBannerClick(index, bannerInfos);
                }
            });
        } else {
            throw new NullPointerException("LoopViewPagerLayout onLoadImageViewListener is not initialize,Be sure to initialize the onLoadImageView");
        }


        return child;
    }
}