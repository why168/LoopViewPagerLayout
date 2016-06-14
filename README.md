# LoopViewPager

无限轮播。。。持续更新


按住－放下后 不会出现闪动

## API

* startLoop() 开始轮播
* stopLoop() 停止轮播,务必在onDestory中调用
* loop_ms 轮播的速度(毫秒)
* loop_duration 滑动的速率(毫秒)
* loop_style 轮播的样式(枚举值: 默认empty 深度depth 缩小zoom)

2.0新增加AIP
* setLoopData(ArrayList<BannerInfo> bannerInfos, OnBannerItemClickListener onBannerItemClickListener) 可点击每张图片回调BannerInfo参数，图片通过BannerInfo中的resId参数传入 如：点击banner跳转网页url等...




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



```xml 2.0版本布局 view_loop_viewpager
<?xml version="1.0" encoding="utf-8"?>
<merge xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:edwin="http://schemas.android.com/apk/res-auto">

    <com.edwin.loopviewpager.lib.view.LoopViewPager
        android:id="@+id/edwin_LoopViewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        edwin:loop_duration="1000"
        edwin:loop_ms="4000"
        edwin:loop_style="depth" />

    <LinearLayout
        android:id="@+id/ll_main_indicatorLayout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerInParent="true"
        android:layout_margin="10dp"
        android:gravity="center"
        android:orientation="horizontal" />

</merge>
```

```xml 2.0版本布局 直接使用
    <com.edwin.loopviewpager.lib.LoopViewPagerLayout
        android:id="@+id/ll_LoopViewPager2"
        android:layout_width="match_parent"
        android:layout_height="200dp" />
```



```xml 1.0版本布局
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

### 效果图  depth(深度动画)
![Image of 示例](https://github.com/why168/LoopViewPager/blob/master/LoopViewPager/gif2_depth?raw=true)
### 效果图  empty(默认动画)
![Image of 示例](https://github.com/why168/LoopViewPager/blob/master/LoopViewPager/gif2_empty?raw=true)
### 效果图  zoom(缩小动画)
![Image of 示例](https://github.com/why168/LoopViewPager/blob/master/LoopViewPager/gif2_zoom?raw=true)



# 更新说明

* 2016/6/12
1.省略

* 2016/6/15 2.0版本再次进行封装,大更新！
1.增加LoopViewPager布局，把LoopViewPager和LinearLayout一起结合起来了，方便直接地通过view_loop_viewpager修改一些参数，低耦合高类聚的原则；
2.修复bug 滑倒第二图再次按住滑动，松开手之后会连续滑动2张图。

* 玩命加载中...








