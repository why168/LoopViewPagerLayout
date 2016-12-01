package com.github.why168;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.why168.animate.DepthPageTransformer;
import com.github.why168.animate.ZoomOutPageTransformer;
import com.github.why168.entity.LoopStyle;
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
 * @since JDK1.8
 */
public class LoopViewPagerLayout extends RelativeLayout implements View.OnTouchListener {
    private FrameLayout indicatorFrameLayout;
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
    private static final int MESSAGE_LOOP = 5;
    private int loop_ms = 4000;//loop speed(ms)
    private int loop_style = -1; //loop style(enum values[-1:empty,1:depth 2:zoom])
    private int loop_duration = 2000;//loop rate(ms)
    private Handler handler = new Handler() {
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

    public interface OnBannerItemClickListener {
        void onBannerClick(int index, ArrayList<BannerInfo> banner);
    }

    public interface OnLoadImageViewListener {
        void onLoadImageView(ImageView view, Object object);
    }

    /**
     * Be sure to initialize the View
     */
    public void initializeView() {
        L.e("LoopViewPager ---> initializeView");
        float density = getResources().getDisplayMetrics().density;

        loopViewPager = new ViewPager(getContext());
        LayoutParams loop_params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addView(loopViewPager, loop_params);

        //TODO FrameLayout
        indicatorFrameLayout = new FrameLayout(getContext());
        LayoutParams f_params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ((int) (20 * density)));
        f_params.addRule(RelativeLayout.CENTER_HORIZONTAL);//android:layout_centerHorizontal="true"
        f_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//android:layout_alignParentBottom="true"
        addView(indicatorFrameLayout, f_params);


        //TODO indicatorLayout
        indicatorLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams ind_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        indicatorLayout.setGravity(Gravity.CENTER);
        indicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
        indicatorFrameLayout.addView(indicatorLayout, ind_params);

        //TODO indicatorLayout2
        animIndicatorLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams ind_params2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        animIndicatorLayout.setGravity(Gravity.CENTER | Gravity.START);
        animIndicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
        indicatorFrameLayout.addView(animIndicatorLayout, ind_params2);
    }

    /**
     * Be sure to initialize the Data
     *
     * @param context context
     */
    public void initializeData(Context context) {
        L.e("LoopViewPager ---> initViewPager");
        //TODO To prevent the flower screen
        if (loop_duration > loop_ms)
            loop_duration = loop_ms;

        //TODO loop_duration
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            LoopScroller mScroller = new LoopScroller(context, new AccelerateInterpolator());
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

        //TODO loop_style
        if (loop_style == 1) {
            loopViewPager.setPageTransformer(true, new DepthPageTransformer());
        } else if (loop_style == 2) {
            loopViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        }

        //TODO Listener
        loopViewPager.setOnTouchListener(this);
        L.e("LoopViewPager init");
    }

    /**
     * initialize the Data
     *
     * @param bannerInfos               BannerInfo
     * @param onBannerItemClickListener ItemClick
     */
    public void setLoopData(ArrayList<BannerInfo> bannerInfos, OnBannerItemClickListener onBannerItemClickListener, OnLoadImageViewListener onLoadImageViewListener) {
        L.e("LoopViewPager 1---> setLoopData");
        if (bannerInfos != null && bannerInfos.size() > 0) {
            this.bannerInfos = bannerInfos;
        } else {
            throw new NullPointerException("LoopViewPagerLayout bannerInfos is null or bannerInfos.size() isEmpty");
        }
        this.onBannerItemClickListener = onBannerItemClickListener;
        this.onLoadImageViewListener = onLoadImageViewListener;
        //TODO Initialize multiple times, clear images and little red dot
        if (indicatorLayout.getChildCount() > 0) {
            indicatorLayout.removeAllViews();
            removeView(animIndicator);
        }

        InitIndicator();

        InitLittleRed();

        indicatorLayout.getViewTreeObserver().addOnPreDrawListener(new MyOnPreDrawListener());

        loopPagerAdapterWrapper = new LoopPagerAdapterWrapper();
        loopViewPager.setAdapter(loopPagerAdapterWrapper);
        loopViewPager.addOnPageChangeListener(new MyPageChangeListener());

        int index = Short.MAX_VALUE / 2 - (Short.MAX_VALUE / 2) % bannerInfos.size();
        loopViewPager.setCurrentItem(index);
    }

    private void InitIndicator() {
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
            indicators[i].setBackgroundResource(R.drawable.indicator_normal_background);//设置默认的背景颜色
            indicatorLayout.addView(indicators[i]);
        }

    }

    private void InitLittleRed() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        animIndicator = new TextView(getContext());
        animIndicator.setGravity(Gravity.CENTER);
        animIndicator.setBackgroundResource(R.drawable.indicator_selected_background);//设置选中的背景颜色
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
     * startLoop
     */
    public void startLoop() {
        handler.removeCallbacksAndMessages(MESSAGE_LOOP);
        handler.sendEmptyMessageDelayed(MESSAGE_LOOP, getLoop_ms());
        L.e("LoopViewPager ---> startLoop");
    }

    /**
     * stopLoop
     * Be sure to in onDestory,To prevent a memory leak
     */
    public void stopLoop() {
        handler.removeMessages(MESSAGE_LOOP);
        L.e("LoopViewPager ---> stopLoop");
    }


    /**
     * ViewPager-onTouch
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                L.e("ACTION_DOWN");
                stopLoop();
                break;
            case MotionEvent.ACTION_MOVE:
                L.e("ACTION_MOVE");
                stopLoop();
                break;
            case MotionEvent.ACTION_UP:
                L.e("ACTION_UP");
                startLoop();
                break;
            default:
                break;
        }
        return false;
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (loopPagerAdapterWrapper.getCount() > 0) {
                float length = ((position % bannerInfos.size()) + positionOffset) / (bannerInfos.size() - 1);
//                //TODO To prevent the last picture the little red dot slip out.
//                if (length >= 1)
//                    length = 1;
                float path = length * totalDistance;
//                L.e("path " + path + " = length * " + length + " totalDistance " + totalDistance);
                ViewCompat.setTranslationX(animIndicator, path);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyOnPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        @Override
        public boolean onPreDraw() {
            Rect firstRect = new Rect();
            indicatorLayout.getChildAt(0).getGlobalVisibleRect(firstRect);

            L.e("firstRect = " + firstRect.toShortString());
            Rect lastRect = new Rect();
            indicatorLayout.getChildAt(indicators.length - 1).getGlobalVisibleRect(lastRect);

            L.e("lastRect = " + lastRect.toShortString());

            totalDistance = lastRect.left - firstRect.left;
            L.e("totalDistance = " + totalDistance);

            indicatorLayout.getViewTreeObserver().removeOnPreDrawListener(this);

            return false;
        }
    }

    private class LoopPagerAdapterWrapper extends PagerAdapter {

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
            final BannerInfo bannerInfo = bannerInfos.get(index);
            final ImageView child = new ImageView(getContext());
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBannerItemClickListener != null)
                        onBannerItemClickListener.onBannerClick(index, bannerInfos);
                }
            });

            if (onLoadImageViewListener != null) {
                onLoadImageViewListener.onLoadImageView(child, bannerInfo.url);
            }

//            Glide
//                    .with(child.getContext())
//                    .load(bannerInfo.url)
//                    .centerCrop()
////                    .placeholder()
//                    .crossFade()
//                    .into(child);

            child.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(child);
            return child;
        }
    }

    /**
     * @param <T> String    可以为一个文件路径、uri或者url
     *            Uri   uri类型
     *            File  文件
     *            Integer   资源Id,R.drawable.xxx或者R.mipmap.xxx
     *            byte[]    类型
     *            T 自定义类型
     */
    public static class BannerInfo<T> {
        public T url;
        public String title;

        public BannerInfo(T url, String title) {
            this.url = url;
            this.title = title;
        }
    }

}