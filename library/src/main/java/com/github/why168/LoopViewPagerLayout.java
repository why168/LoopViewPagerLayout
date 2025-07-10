package com.github.why168;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.viewpager.widget.ViewPager;

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

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * LoopViewPagerLayout
 *
 * @author Edwin.Wu
 * @version 2016/11/14 23:58
 * @see <a href="https://github.com/why168/LoopViewPagerLayout">LoopViewPagerLayout</a>
 * @since JDK11
 */
public class LoopViewPagerLayout extends RelativeLayout {
    private ViewPager loopViewPager;
    private LinearLayout indicatorLayout;
    private LinearLayout animIndicatorLayout;
    private OnBannerItemClickListener onBannerItemClickListener = null;
    private OnLoadImageViewListener onLoadImageViewListener = null;
    private LoopPagerAdapterWrapper loopPagerAdapterWrapper;
    private int totalDistance;//Little red dot all the distance to move
    private final int size = Tools.dip2px(getContext(), 8);//The size of the set point;
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
    private static class LoopHandler extends Handler {
        private final WeakReference<LoopViewPagerLayout> weakReference;

        public LoopHandler(LoopViewPagerLayout layout) {
            super(Looper.getMainLooper());
            this.weakReference = new WeakReference<>(layout);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoopViewPagerLayout layout = weakReference.get();
            if (layout != null && msg.what == MESSAGE_LOOP) {
                int currentItem = layout.loopViewPager.getCurrentItem();
                int totalCount = layout.loopPagerAdapterWrapper != null ? layout.loopPagerAdapterWrapper.getCount() : 0;
                if (currentItem < totalCount - 1) {
                    layout.loopViewPager.setCurrentItem(currentItem + 1, true);
                    sendEmptyMessageDelayed(MESSAGE_LOOP, layout.getLoop_ms());
                }
            }
        }
    }

    private LoopHandler handler;

    public LoopViewPagerLayout(Context context) {
        super(context);
        this.handler = new LoopHandler(this);
        L.e("Initialize LoopViewPagerLayout ---> context");
    }

    public LoopViewPagerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.handler = new LoopHandler(this);
        L.e("Initialize LoopViewPagerLayout ---> context, attrs");
    }

    public LoopViewPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.handler = new LoopHandler(this);
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
        L.e("initializeView");
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

        L.e("initializeData");
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
        L.e("setLoopData");
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

        // 设置初始位置到中间位置，确保可以向前向后无限滚动
        int totalCount = loopPagerAdapterWrapper.getCount();
        int index = totalCount / 2 - (totalCount / 2) % bannerInfos.size();
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
        if (loop_ms < 1500) loop_ms = 1500;
        return loop_ms;
    }

    public boolean setDebug(boolean isDebug) {
        L.deBug = isDebug;
        return L.deBug;
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
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessageDelayed(MESSAGE_LOOP, getLoop_ms());
        }
        L.e("startLoop");
    }

    /**
     * stopLoop
     * 一定要在onDestroy中防止内存泄漏。
     */
    public void stopLoop() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        L.e("stopLoop");
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
            if (loopPagerAdapterWrapper.getCount() > 0 && bannerInfos != null && bannerInfos.size() > 0) {
                // 防止除零错误：当只有一张图片时，直接返回
                if (bannerInfos.size() == 1) {
                    animIndicator.setTranslationX(0);
                    return;
                }
                
                int currentIndex = position % bannerInfos.size();
                float length = (currentIndex + positionOffset) / (bannerInfos.size() - 1);
                
                // 边界检查：防止指示器滑出范围
                if (length > 1.0f) {
                    length = 1.0f;
                } else if (length < 0.0f) {
                    length = 0.0f;
                }
                
                float path = length * totalDistance;
                L.e("path " + path + " = length * " + length + " totalDistance " + totalDistance);
                animIndicator.setTranslationX(path);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (bannerInfos != null && bannerInfos.size() > 0) {
                int i = position % bannerInfos.size();
                if (bannerInfos.size() == 1) {
                    // 只有一张图片时，指示器保持在起始位置
                    animIndicator.setTranslationX(0);
                } else if (i == 0) {
                    animIndicator.setTranslationX(0);
                } else if (i == bannerInfos.size() - 1) {
                    animIndicator.setTranslationX(totalDistance);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // 可以在这里添加状态改变时的处理逻辑
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
