package com.edwin.loopviewpager.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.why168.listener.OnLoadImageViewListener;

/**
 * Fresco
 * ImageViewLoader
 *
 * @author Edwin.Wu
 * @version 2016/12/6 18:11
 * @see <a href="https://github.com/facebook/fresco">Fresco/a>
 * @since JDK1.8
 */
public class OnFrescoImageViewLoader implements OnLoadImageViewListener {

    @Override
    public SimpleDraweeView createImageView(Context context) {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
        return simpleDraweeView;
    }

    @Override
    public void onLoadImageView(ImageView imageView, Object parameter) {
        Uri uri = Uri.parse((String) parameter);
        imageView.setImageURI(uri);
    }
}
