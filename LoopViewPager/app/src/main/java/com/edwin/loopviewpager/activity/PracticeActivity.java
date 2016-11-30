package com.edwin.loopviewpager.activity;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.base.BaseActivity;
import com.github.why168.utils.L;

/**
 * 练习
 *
 * @author Edwin.Wu
 * @version 2016/11/14 17:55
 * @since JDK1.8
 */
public class PracticeActivity extends BaseActivity {
    private Button mButton;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_practice);
    }

    @Override
    protected void setUpView() {
        mButton = (Button) findViewById(R.id.mButton);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        mButton.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Rect rect = new Rect();
                Point point = new Point();
                boolean globalVisibleRect = mButton.getGlobalVisibleRect(rect, point);
                L.e("globalVisibleRect = " + globalVisibleRect);
                L.e("Rect -> " + rect.toShortString());
                L.e("Point -> " + point.toString());

                rect.offset(50,50);
                mButton.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }
}
