## LoopViewPager

LoopViewPager无限轮播：
1.支持三种动画；
2.支持修改轮播的速度；
3.支持修改滑动速率；
4.小红点动态移动；
5.防闪屏花屏。


### 效果图 
![Image of 示例](https://raw.githubusercontent.com/why168/LoopViewPager/master/LoopViewPager/art/sample.gif)



Gradle
------------
Step 1. Add the JitPack repository to your build file

```groovy
dependencies {
    allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
}
```
Step 2. Add the dependency

```groovy
dependencies {
    compile 'compile 'com.github.why168:LoopViewPager:1.0.0'
}
```

## API调用顺序

* initializeView()：初始化View
* setLoop_ms：轮播的速度(毫秒)
* setLoop_duration：滑动的速率(毫秒)
* setLoop_style：轮播的样式(枚举值: -1默认empty，1深度1depth，2缩小zoom)
* initializeData(Content)：初始化数据
* setLoopData(ArrayList<BannerInfo>, OnBannerItemClickListener)：数据，回调监听
* startLoop()：开始轮播
* stopLoop()：停止轮播,务必在onDestory中调用



## 布局 LoopViewPagerLayout

```xml 
<?xml version="1.0" encoding="utf-8"?>
<com.github.why168.LoopViewPagerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/mLoopViewPagerLayout"
    android:layout_width="match_parent"
    android:layout_height="200dp" />

```



## 更优雅地使用
```java 
 private LoopViewPagerLayout mLoopViewPagerLayout;
 mLoopViewPagerLayout = (LoopViewPagerLayout)findViewById(R.id.mLoopViewPagerLayout);
 mLoopViewPagerLayout.initializeView();//初始化View
 mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
 mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)
 mLoopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
 mLoopViewPagerLayout.initializeData(mActivity);//初始化数据
 ArrayList<LoopViewPagerLayout.BannerInfo> data = new ArrayList<>(4);
 data.add(new LoopViewPagerLayout.BannerInfo(R.mipmap.a, "第一张图片"));
 data.add(new LoopViewPagerLayout.BannerInfo(R.mipmap.c, "第二张图片"));
 data.add(new LoopViewPagerLayout.BannerInfo(R.mipmap.d, "第三张图片"));
 data.add(new LoopViewPagerLayout.BannerInfo(R.mipmap.b, "第四张图片"));
 mLoopViewPagerLayout.setLoopData(data, this);

```



# 更新说明

* 2016/6/12
1.省略

* 2016/6/15 2.0版本再次进行封装,大更新！
	* 1.增加LoopViewPager布局，把LoopViewPager和LinearLayout一起结合起来了，方便直接地通过view_loop_viewpager修改一些参数，低耦合高类聚的原则；
	* 2.修复bug 滑倒第二图再次按住滑动，松开手之后会连续滑动2张图。

* 2016/7/1 2.1版本更新！
	* 1.因初始化多次,清空图片和小红点。感谢solochen提出的问题。(已经修复)

* 2016/11/8 00:25重构项目，1.0正式被发布，支持Gradle！
	* 使用更方便








