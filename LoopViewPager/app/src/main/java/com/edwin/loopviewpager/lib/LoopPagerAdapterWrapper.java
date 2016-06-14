package com.edwin.loopviewpager.lib;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * LoopPagerAdapterWrapper
 *
 * @USER Edwin
 * @DATE 16/6/11 下午7:11
 */
public class LoopPagerAdapterWrapper extends PagerAdapter {

    private List<ImageView> imageViews = new ArrayList<ImageView>(4);

    public LoopPagerAdapterWrapper(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
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
        ImageView child = imageViews.get(position % imageViews.size());
        container.addView(child);
        return child;
    }
}
