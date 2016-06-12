package com.edwin.loopviewpager;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edwin.loopviewpager.lib.LoopPagerAdapterWrapper;
import com.edwin.loopviewpager.lib.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @USER Edwin
 * @DATE 16/6/12 上午00:30
 */
public class MainActivity extends AppCompatActivity {
    private LoopViewPager mLoopViewPager;
    private LoopPagerAdapterWrapper myAdapter;
    private List<ImageView> imageViews = new ArrayList<ImageView>(4);
    private int[] ids = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d};
    private LinearLayout indicatorLayout;
    private TextView animIndicator;
    private RelativeLayout rootView;
    private int totalDistance;//小红点要移动的全部距离
    private int startX;//小红点开始位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();


    }

    private void initView() {

        mLoopViewPager = (LoopViewPager) findViewById(R.id.LoopViewPager);
        indicatorLayout = (LinearLayout) findViewById(R.id.indicatorLayout);
        rootView = (RelativeLayout) findViewById(R.id.rootView);

    }

    private void initData() {
        //TODO   small point
        final Drawable indicatorNormal = getResources().getDrawable(R.drawable.indicator_normal_background);
        final Drawable indicatorSelected = getResources().getDrawable(R.drawable.indicator_selected_background);
        int size = 8;
        final TextView[] indicators = new TextView[ids.length];
        for (int i = 0; i < indicators.length; i++) {

            indicators[i] = new TextView(this);
            indicators[i].setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);

            if (i != indicators.length - 1) {
                params.setMargins(0, 0, size, 0);
            } else {
                params.setMargins(0, 0, 0, 0);
            }
            indicators[i].setLayoutParams(params);
            indicators[i].setBackgroundDrawable(indicatorNormal);
            indicatorLayout.addView(indicators[i]);
        }


        //TODO
        for (int i = 0; i < 4; i++) {
            Bitmap bitmap = mLoopViewPager.decodeSampledBitmapFromResource(getResources(), ids[i], 300, 300);
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }

        myAdapter = new LoopPagerAdapterWrapper(imageViews);
        mLoopViewPager.setAdapter(myAdapter);

        mLoopViewPager.addOnPageChangeListener(pageChangeListener);

        //TODO
        animIndicator = new TextView(this);
        animIndicator.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        animIndicator.setBackgroundDrawable(indicatorSelected);
        rootView.addView(animIndicator);


        indicatorLayout.getViewTreeObserver()
                .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        indicatorLayout.getViewTreeObserver().removeOnPreDrawListener(this);

                        Rect rootRect = new Rect();
                        Point globalOffset = new Point();
                        rootView.getGlobalVisibleRect(rootRect, globalOffset);

                        Rect firstRect = new Rect();
                        indicatorLayout.getChildAt(0).getGlobalVisibleRect(firstRect);
                        firstRect.offset(-globalOffset.x, -globalOffset.y);

                        Rect lastRect = new Rect();
                        indicatorLayout.getChildAt(indicators.length - 1).getGlobalVisibleRect(lastRect);

                        totalDistance = lastRect.left - indicatorLayout.getLeft();
                        startX = firstRect.left;

                        ViewCompat.setTranslationX(animIndicator, firstRect.left);
                        ViewCompat.setTranslationY(animIndicator, firstRect.top);
                        return true;
                    }
                });

        mLoopViewPager.setCurrentItem(Integer.MAX_VALUE/2 - (Integer.MAX_VALUE /2)%imageViews .size());
        //TODO 开始轮播
        mLoopViewPager.startLoop();
    }

    private ViewPager.OnPageChangeListener pageChangeListener =
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (myAdapter.getCount() > 0) {
                        float length = ((position % 4) + positionOffset) / (imageViews.size() - 1);
                        //TODO 防止最后一张图片小红点滑出去了.
                        if (length >= 1)
                            length = 1;
                        float path = length * totalDistance;
                        ViewCompat.setTranslationX(animIndicator, startX + path);
                    }
                }

                /**
                 * @see ViewPager#SCROLL_STATE_IDLE     滚动状态前
                 * @see ViewPager#SCROLL_STATE_DRAGGING 滚动状态中
                 * @see ViewPager#SCROLL_STATE_SETTLING 滚动状态后
                 */
                @Override
                public void onPageScrollStateChanged(int state) {

                }

                @Override
                public void onPageSelected(int position) {

                }
            };


    @Override
    protected void onDestroy() {
        mLoopViewPager.stopLoop();
        super.onDestroy();
    }
}
