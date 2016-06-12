package com.edwin.loopviewpager.lib;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.edwin.loopviewpager.L;

/**
 * ViewPager无限轮播
 *
 * @USER Edwin
 * @DATE 16/6/11 下午5:56
 */
public class LoopViewPager extends ViewPager implements View.OnTouchListener {
    private static final int MESSAGE_LOOP = 5;
    private Context context;


    public LoopViewPager(Context context) {
        super(context);
        init(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        setOnTouchListener(this);
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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                L.e("Edwin", "按下");
                stopLoop();
                break;
            case MotionEvent.ACTION_MOVE:
                L.e("Edwin", "移动");
                stopLoop();
                break;
            case MotionEvent.ACTION_UP:
                L.e("Edwin", "抬起");
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
                sendEmptyMessageDelayed(MESSAGE_LOOP, 2000);
            }
        }
    };

    public void stopLoop() {
        handler.removeMessages(MESSAGE_LOOP);
        L.e("stopLoop");
    }

    public void startLoop() {
        handler.sendEmptyMessageDelayed(MESSAGE_LOOP, 2000);
        L.e("startLoop");
    }
}
