package com.edwin.loopviewpager.loader;

import android.content.Context;
import android.widget.ImageView;

import com.edwin.loopviewpager.R;
import com.github.why168.listener.OnLoadImageViewListener;
import com.github.why168.utils.L;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Picasso
 * ImageViewLoader
 *
 * @author Edwin.Wu
 * @version 2016/12/6 18:10
 * @see <a href="https://github.com/square/picasso">Picasso/a>
 * @since JDK1.8
 */
public class OnPicassoImageViewLoader implements OnLoadImageViewListener {

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        return imageView;
    }

    @Override
    public void onLoadImageView(ImageView imageView, Object object) {
        Picasso
                .with(imageView.getContext())
                .load((String) object)
                .error(R.drawable.indicator_normal_background)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        L.e("Picasso ---> onSuccess");
                    }

                    @Override
                    public void onError() {
                        L.e("Picasso ---> onError");
                    }
                });
    }
}
