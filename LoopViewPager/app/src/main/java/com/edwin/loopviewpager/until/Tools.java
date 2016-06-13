package com.edwin.loopviewpager.until;

import android.content.Context;

/**
 * 工具类
 *
 * @USER Edwin
 * @DATE 16/6/13 下午10:14
 */
public class Tools {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dip
     * @return
     */
    public static int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param px 像素
     * @return
     */
    public static float px2dip(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }
}
