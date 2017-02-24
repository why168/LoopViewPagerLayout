package com.edwin.loopviewpager.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edwin.loopviewpager.R;
import com.github.why168.listener.OnLoadImageViewListener;

/**
 * Glide
 * ImageViewLoader
 *
 * @author Edwin.Wu
 * @version 2016/12/6 18:04
 * @see <a href="https://github.com/bumptech/glide">Glide/a>
 * @since JDK1.8
 */
public class OnGlideImageViewLoader implements OnLoadImageViewListener {

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void onLoadImageView(ImageView view, Object parameter) {
        Glide
                .with(view.getContext())
                .load(parameter)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(view);
    }
}
