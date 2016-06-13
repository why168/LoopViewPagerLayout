# LoopViewPager

无限轮播。。。持续更新

按住－放下后 不会出现闪动

## API

* startLoop() 开始轮播
* stopLoop() 停止轮播,务必在onDestory中调用
* loop_ms 轮播的速度(毫秒)
* loop_duration 滑动的速率(毫秒)
* loop_style 轮播的样式(枚举值: 默认empty 深度depth 缩小zoom)



## 定义自定义属性

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="LoopViewPager">
        <attr name="loop_ms" format="integer" />
        <attr name="loop_duration" format="integer" />
        <attr name="loop_style" format="enum">
            <enum name="empty" value="-1"/>
            <enum name="depth" value="1"/>
            <enum name="zoom" value="2"/>
        </attr>
    </declare-styleable>
</resources>
```

```xml
<RelativeLayout
        android:id="@+id/rl_main_rootView"
        android:layout_width="match_parent"
        android:layout_height="150dp">

        <com.edwin.loopviewpager.lib.LoopViewPager
            android:id="@+id/edwin_LoopViewPager"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            edwin:loop_duration="1000"
            edwin:loop_ms="4000" 
            edwin:loop_style="zoom"/>

        <LinearLayout
            android:id="@+id/ll_main_indicatorLayout"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_centerInParent="true"
            android:layout_margin="10dp"
            android:gravity="center"
            android:orientation="horizontal" />

    </RelativeLayout>
```


### 效果图  
![Image of 示例](https://github.com/why168/LoopViewPager/blob/master/LoopViewPager/loop3.gif?raw=true)





