package com.github.why168;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.why168.adapter.LoopPagerAdapterWrapper;
import com.github.why168.animate.DepthPageTransformer;
import com.github.why168.animate.ZoomOutPageTransformer;
import com.github.why168.listener.OnBannerItemClickListener;
import com.github.why168.listener.OnLoadImageViewListener;
import com.github.why168.modle.BannerInfo;
import com.github.why168.modle.IndicatorLocation;
import com.github.why168.modle.LoopStyle;
import com.github.why168.scroller.LoopScroller;
import com.github.why168.utils.L;
import com.github.why168.utils.Tools;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * LoopViewPagerLayout
 *
 * @author Edwin.Wu
 * @version 2016/11/14 23:58
 * @see <a href="https://github.com/why168/LoopViewPagerLayout">LoopViewPagerLayout/a>
 * @since JDK1.8
 */
public class LoopViewPagerLayout extends RelativeLayout {
    private ViewPager loopViewPager;
    private LinearLayout indicatorLayout;
    private LinearLayout animIndicatorLayout;
    private OnBannerItemClickListener onBannerItemClickListener = null;
    private OnLoadImageViewListener onLoadImageViewListener = null;
    private LoopPagerAdapterWrapper loopPagerAdapterWrapper;
    private int totalDistance;//Little red dot all the distance to move
    private int size = Tools.dip2px(getContext(), 8);//The size of the set point;
    private ArrayList<BannerInfo> bannerInfos;//banner data
    private TextView animIndicator;//Little red dot on the move
    private TextView[] indicators;//Initializes the white dots
    @DrawableRes
    private int normalBackground = R.drawable.indicator_normal_background;
    @DrawableRes
    private int selectedBackground = R.drawable.indicator_selected_background;
    private static final int MESSAGE_LOOP = 5;
    private int loop_ms = 4000;//loop speed(ms)
    private int loop_style = -1; //loop style(enum values[-1:empty,1:depth 2:zoom])
    private IndicatorLocation indicatorLocation = IndicatorLocation.Center; //Indicator Location(enum values[1:left,0:depth 2:right])
    private int loop_duration = 2000;//loop rate(ms)
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (msg.what == MESSAGE_LOOP) {
                if (loopViewPager.getCurrentItem() < Short.MAX_VALUE - 1) {
                    loopViewPager.setCurrentItem(loopViewPager.getCurrentItem() + 1, true);
                    sendEmptyMessageDelayed(MESSAGE_LOOP, getLoop_ms());
                }
            }
        }
    };

    public LoopViewPagerLayout(Context context) {
        super(context);
        L.e("Initialize LoopViewPagerLayout ---> context");
    }

    public LoopViewPagerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        L.e("Initialize LoopViewPagerLayout ---> context, attrs");
    }

    public LoopViewPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        L.e("Initialize LoopViewPagerLayout ---> context, attrs, defStyleAttr");
    }


    /**
     * onBannerItemClickListener
     *
     * @param onBannerItemClickListener onBannerItemClickListener
     */
    public void setOnBannerItemClickListener(OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    /**
     * OnLoadImageViewListener
     *
     * @param onLoadImageViewListener onLoadImageViewListener
     */
    public void setOnLoadImageViewListener(OnLoadImageViewListener onLoadImageViewListener) {
        this.onLoadImageViewListener = onLoadImageViewListener;
    }

    /**
     * Be sure to initialize the View
     */
    private void initializeView() {
        L.e("LoopViewPager ---> initializeView");
        float density = getResources().getDisplayMetrics().density;

        loopViewPager = new ViewPager(getContext());
        loopViewPager.setId(R.id.loop_viewpager);
        LayoutParams loop_params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addView(loopViewPager, loop_params);

        // FrameLayout
        FrameLayout indicatorFrameLayout = new FrameLayout(getContext());
        LayoutParams f_params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ((int) (20 * density)));
        f_params.addRule(RelativeLayout.CENTER_HORIZONTAL);//android:layout_centerHorizontal="true"
        f_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//android:layout_alignParentBottom="true"

        switch (indicatorLocation) {
            case Left:
                f_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);// android:layout_alignParentLeft="true"
                break;
            case Right:
                f_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);//android:layout_alignParentRight="true"
                break;
            default:
                break;
        }

        f_params.setMargins(((int) (10 * density)), 0, ((int) (10 * density)), 0);
        addView(indicatorFrameLayout, f_params);

        // 指标的布局
        indicatorLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams ind_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        indicatorLayout.setGravity(Gravity.CENTER);
        indicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
        indicatorFrameLayout.addView(indicatorLayout, ind_params);

        // 动画指标布局
        animIndicatorLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams ind_params2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        animIndicatorLayout.setGravity(Gravity.CENTER | Gravity.START);
        animIndicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
        indicatorFrameLayout.addView(animIndicatorLayout, ind_params2);
    }

    /**
     * 确保初始化数据
     *
     * @param context context
     */
    public void initializeData(Context context) {
        initializeView();

        L.e("LoopViewPager ---> initializeData");
        if (loop_duration > loop_ms) // 防止花屏
            loop_duration = loop_ms;

        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            LoopScroller mScroller = new LoopScroller(context);
//            LoopScroller mScroller = new LoopScroller(context, new AccelerateInterpolator());
//            LoopScroller mScroller = new LoopScroller(context, new AnticipateInterpolator());
//            LoopScroller mScroller = new LoopScroller(context, new PathInterpolator());
//            LoopScroller mScroller = new LoopScroller(context, new BounceInterpolator());
//            LoopScroller mScroller = new LoopScroller(context, new OvershootInterpolator());
//            LoopScroller mScroller = new LoopScroller(context, new AnticipateOvershootInterpolator());
//            LoopScroller mScroller = new LoopScroller(context, new LinearInterpolator());
//            LoopScroller mScroller = new LoopScroller(context, new AccelerateInterpolator());
//            LoopScroller mScroller = new LoopScroller(context, new DecelerateInterpolator());
//            LoopScroller mScroller = new LoopScroller(context, new CycleInterpolator(20));
            //可以用setDuration的方式调整速率
            mScroller.setmDuration(loop_duration);
            mField.set(loopViewPager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loop_style == 1) {
            loopViewPager.setPageTransformer(true, new DepthPageTransformer());
        } else if (loop_style == 2) {
            loopViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        }

        loopViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        stopLoop();
                        break;
                    case MotionEvent.ACTION_UP:
                        startLoop();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * initialize the Data
     *
     * @param bannerInfos BannerInfo
     */
    public void setLoopData(ArrayList<BannerInfo> bannerInfos) {
        L.e("LoopViewPager ---> setLoopData");
        if (bannerInfos != null && bannerInfos.size() > 0) {
            this.bannerInfos = bannerInfos;
        } else {
            throw new NullPointerException("LoopViewPagerLayout bannerInfos is null or bannerInfos.size() isEmpty");
        }

        // 防止初始化多次，清除图片和小红点。
        if (indicatorLayout.getChildCount() > 0) {
            indicatorLayout.removeAllViews();
            removeView(animIndicator);
        }

        InitIndicator();

        InitLittleRed();

//        indicatorLayout.getViewTreeObserver().addOnPreDrawListener(new IndicatorPreDrawListener());
        totalDistance = 2 * size * (indicators.length - 1);

        loopPagerAdapterWrapper = new LoopPagerAdapterWrapper(getContext(), bannerInfos, onBannerItemClickListener, onLoadImageViewListener);
        loopViewPager.setAdapter(loopPagerAdapterWrapper);
        loopViewPager.addOnPageChangeListener(new ViewPageChangeListener());

        int index = Short.MAX_VALUE / 2 - (Short.MAX_VALUE / 2) % bannerInfos.size();
        loopViewPager.setCurrentItem(index);
    }

    private void InitIndicator() {
        indicatorLayout.removeAllViews();
        indicators = new TextView[bannerInfos.size()];
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new TextView(getContext());
            indicators[i].setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            if (i != indicators.length - 1) {
                params.setMargins(0, 0, size, 0);
            } else {
                params.setMargins(0, 0, 0, 0);
            }
            indicators[i].setLayoutParams(params);
            indicators[i].setBackgroundResource(getNormalBackground());//设置默认的背景颜色
            indicatorLayout.addView(indicators[i]);
        }

    }

    private void InitLittleRed() {
        animIndicatorLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        animIndicator = new TextView(getContext());
        animIndicator.setGravity(Gravity.CENTER);
        animIndicator.setBackgroundResource(getSelectedBackground());//设置选中的背景颜色
        animIndicatorLayout.addView(animIndicator, params);
    }

    public int getLoop_ms() {
        if (loop_ms < 1500)
            loop_ms = 1500;
        return loop_ms;
    }

    /**
     * loop speed
     *
     * @param loop_ms (ms)
     */
    public void setLoop_ms(int loop_ms) {
        this.loop_ms = loop_ms;
    }

    /**
     * loop rate
     *
     * @param loop_duration (ms)
     */
    public void setLoop_duration(int loop_duration) {
        this.loop_duration = loop_duration;
    }

    /**
     * loop style
     *
     * @param loop_style (enum values[-1:empty,1:depth 2:zoom])
     */
    public void setLoop_style(LoopStyle loop_style) {
        this.loop_style = loop_style.getValue();
    }

    /**
     * 指示器的位置
     *
     * @param indicatorLocation (enum values[1:left,0:depth,2:right])
     */
    public void setIndicatorLocation(IndicatorLocation indicatorLocation) {
        this.indicatorLocation = indicatorLocation;
    }

    /**
     * startLoop
     */
    public void startLoop() {
        handler.removeCallbacksAndMessages(MESSAGE_LOOP);
        handler.sendEmptyMessageDelayed(MESSAGE_LOOP, getLoop_ms());
        L.e("LoopViewPager ---> startLoop");
    }

    /**
     * stopLoop
     * 一定要在onDestroy中防止内存泄漏。
     */
    public void stopLoop() {
        handler.removeMessages(MESSAGE_LOOP);
        L.e("LoopViewPager ---> stopLoop");
    }

    /**
     * LoopViewPager
     *
     * @return ViewPager
     */
    public ViewPager getLoopViewPager() {
        return loopViewPager;
    }

    public int getNormalBackground() {
        return normalBackground;
    }

    public void setNormalBackground(@DrawableRes int normalBackground) {
        this.normalBackground = normalBackground;
    }

    public int getSelectedBackground() {
        return selectedBackground;
    }

    public void setSelectedBackground(@DrawableRes int selectedBackground) {
        this.selectedBackground = selectedBackground;
    }

    /**
     * OnPageChangeListener
     */
    private class ViewPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (loopPagerAdapterWrapper.getCount() > 0) {
                float length = ((position % bannerInfos.size()) + positionOffset) / (bannerInfos.size() - 1);
                // 为了防止最后一小红点滑出去
                if (length >= 1)
                    return;
                float path = length * totalDistance;
//                L.e("path " + path + " = length * " + length + " totalDistance " + totalDistance);
//                ViewCompat.setTranslationX(animIndicator, path);
                animIndicator.setTranslationX(path);
            }
        }

        @Override
        public void onPageSelected(int position) {
            int i = position % bannerInfos.size();
            if (i == 0) {
                animIndicator.setTranslationX(totalDistance * 0.0f);
            } else if (i == bannerInfos.size() - 1) {
                animIndicator.setTranslationX(totalDistance * 1.0f);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

//    private class IndicatorPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
//        @Override
//        public boolean onPreDraw() {
//            Rect firstRect = new Rect();
//            indicatorLayout.getChildAt(0).getGlobalVisibleRect(firstRect);
//
//            L.e("firstRect = " + firstRect.toShortString());
//            Rect lastRect = new Rect();
//            indicatorLayout.getChildAt(indicators.length - 1).getGlobalVisibleRect(lastRect);
//
//            L.e("lastRect = " + lastRect.toShortString());
//
//            totalDistance = lastRect.left - firstRect.left;
//            L.e("totalDistance = " + totalDistance);
//
//            totalDistance = 2 * size * (indicators.length - 1);
//            L.e("---------- totalDistance = " + totalDistance);
//            indicatorLayout.getViewTreeObserver().removeOnPreDrawListener(this);
//            return false;
//        }
//    }
}
