package com.edwin.loopviewpager.lib;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.lib.animation.DepthPageTransformer;
import com.edwin.loopviewpager.lib.animation.ZoomOutPageTransformer;
import com.edwin.loopviewpager.until.L;

import java.lang.reflect.Field;

/**
 * ViewPager无限轮播
 *
 * @USER Edwin
 * @DATE 16/6/11 下午5:56
 */
public class LoopViewPager extends ViewPager implements View.OnTouchListener {
    private static final int MESSAGE_LOOP = 5;
    private Context context;
    private static int loop_ms = 4000;//轮播的时间(毫秒)

    public int getLoop_ms() {
        //太快也受不了,慢慢来
        if (loop_ms < 1000)
            loop_ms = 1000;
        return loop_ms;
    }

    public void setLoop_ms(int loop_ms) {
        this.loop_ms = loop_ms;
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        setOnTouchListener(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoopViewPager);
        int loop_ms = typedArray.getInteger(R.styleable.LoopViewPager_loop_ms, 4000);
        int loop_duration = typedArray.getInteger(R.styleable.LoopViewPager_loop_duration, 2000);
        int loop_style = typedArray.getInteger(R.styleable.LoopViewPager_loop_style, -1);
        setLoop_ms(loop_ms);

        //TODO 防止花屏
        if (loop_duration > loop_ms)
            loop_duration = loop_ms;

        //TODO 设置滑动的速率
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            LoopScroller mScroller = new LoopScroller(context,
                    new AccelerateInterpolator());
            //可以用setDuration的方式调整速率
            mScroller.setmDuration(loop_duration);
            mField.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO 设置样式
        if (loop_style == 1) {
            setPageTransformer(true, new DepthPageTransformer());
        } else if (loop_style == 2) {
            setPageTransformer(true, new ZoomOutPageTransformer());
        }
        typedArray.recycle();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopLoop();
                break;
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


    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (msg.what == MESSAGE_LOOP) {
                setCurrentItem(getCurrentItem() + 1);
                removeCallbacksAndMessages(MESSAGE_LOOP);
                sendEmptyMessageDelayed(MESSAGE_LOOP, getLoop_ms());
            }
        }
    };


    /**
     * 开始循环
     */
    public void startLoop() {
        handler.sendEmptyMessageDelayed(MESSAGE_LOOP, getLoop_ms());
        L.e("开始循环 startLoop");
    }

    /**
     * 停止循环
     * 务必在onDestory执行
     */
    public void stopLoop() {
        handler.removeMessages(MESSAGE_LOOP);
        L.e("停止循环 stopLoop");
    }


    /**
     * 压缩图片
     *
     * @param res       资源
     * @param resId     图片ID
     * @param reqWidth  0表示获取ViewPager的宽
     * @param reqHeight 0表示获取ViewPager的高
     * @return
     */
    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {
        // 第一次解码inJustDecodeBounds = true检查尺寸
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // 计算inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 解码与inSampleSize设置位图
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 算法
     * 假如你要求宽高200*200
     * 图片实际宽高1000*1000，除以2以后变成500*500
     * 发现比要求的还是大，就再除以2.。直到达到要求
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return 压缩比例
     */
    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 原始图像的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // 计算最大inSampleSize值是2的幂,让两者高度和宽度大于请求的高度和宽度。
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


}
