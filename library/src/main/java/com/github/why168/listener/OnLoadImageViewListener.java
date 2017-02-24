package com.github.why168.listener;

import android.content.Context;
import android.widget.ImageView;

/**
 * Load ImageView Listener
 *
 * @author Edwin.Wu
 * @version 2016/12/6 14:40
 * @since JDK1.8
 */
public interface OnLoadImageViewListener {
    /**
     * create image
     *
     * @param context context
     * @return image
     */
    ImageView createImageView(Context context);

    /**
     * image load
     *
     * @param imageView ImageView
     * @param parameter String    可以为一个文件路径、uri或者url
     *                  Uri   uri类型
     *                  File  文件
     *                  Integer   资源Id,R.drawable.xxx或者R.mipmap.xxx
     *                  byte[]    类型
     *                  T 自定义类型
     */
    void onLoadImageView(ImageView imageView, Object parameter);
}
