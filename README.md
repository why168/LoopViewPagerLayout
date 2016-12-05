## LoopViewPagerLayout无限轮播

[![](https://jitpack.io/v/why168/LoopViewPagerLayout.svg)](https://jitpack.io/#why168/LoopViewPagerLayout)

* 支持三种动画；
* 支持修改轮播的速度；
* 支持修改滑动速率；
* 支持点击事件回调监听；
* 支持自定义图片加载；
* 支持addHeaderView方式；
* 支持小红点位置左中右；
* 指示器小红点动态移动；
* 防闪屏花屏。


### 效果图 
![Image of 示例](https://raw.githubusercontent.com/why168/LoopViewPager/master/LoopViewPager/art/sample2.gif)



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
    compile 'com.github.why168:LoopViewPagerLayout:2.0.0'
}
```

代码混淆
------------
```java
#LoopViewPagerLayout
-dontwarn com.github.why168
-keep class com.github.why168
```

## API调用顺序

* setIndicatorLocation(IndicatorLocation.Right)：小红点位置（枚举值: 1:left，0:depth， 2:right）
* initializeView()：初始化View
* setLoop_ms：轮播的速度(毫秒)
* setLoop_duration：滑动的速率(毫秒)
* setLoop_style：轮播的样式(枚举值: -1默认empty，1深度1depth，2缩小zoom)
* initializeData(Content)：初始化数据
* setLoopData(ArrayList<BannerInfo>, OnBannerItemClickListener, OnLoadImageViewListener)：数据，数据回调监听，自定义图片加载监听
* startLoop()：开始轮播
* stopLoop()：停止轮播,务必在onDestory中调用


#### javadoc  

<https://jitpack.io/com/github/why168/LoopViewPagerLayout/v2.0.0/javadoc/>

## 布局 LoopViewPagerLayout

```xml 
<?xml version="1.0" encoding="utf-8"?>
<com.github.why168.LoopViewPagerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/mLoopViewPagerLayout"
    android:layout_width="match_parent"
    android:layout_height="200dp" />

```



## 更优雅地使用API-调用顺序不能乱
```java 
 mLoopViewPagerLayout = (LoopViewPagerLayout)findViewById(R.id.mLoopViewPagerLayout);
 mLoopViewPagerLayout.initializeView();//初始化View
 mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
 mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)
 mLoopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
 mLoopViewPagerLayout.initializeData(mActivity);//初始化数据
 ArrayList<LoopViewPagerLayout.BannerInfo> data = new ArrayList<>();
 data.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.a, "第一张图片"));
 data.add(new LoopViewPagerLayout.BannerInfo<String>("url", "第二张图片"));
 data.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.b, "第三张图片"));
 data.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.c, "第四张图片"));
 data.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.d, "第五张图片"));
 mLoopViewPagerLayout.setLoopData(data,this,this);
```

###回调函数
 
```java
public interface OnBannerItemClickListener {
    /**
     * banner click
     *
     * @param index  subscript
     * @param banner bean
     */
    void onBannerClick(int index, ArrayList<BannerInfo> banner);
}

public interface OnLoadImageViewListener {
    /**
     * image load
     *
     * @param view   ImageView
     * @param object parameter
     */
    void onLoadImageView(ImageView view, Object object);
}
```





## 更新说明

* 2016/06/12 
	1. 省略。

* 2016/06/15 
	1. 2.0版本再次进行封装,大更新！
	2. 增加LoopViewPager布局，把LoopViewPager和LinearLayout一起结合起来了，方便直接地通过view_loop_viewpager修改一些参数，低耦合高类聚的原则；
	3. 修复bug 滑倒第二图再次按住滑动，松开手之后会连续滑动2张图。

* 2016/07/01 
	1. 2.1版本更新！
	2. 因初始化多次,清空图片和小红点。感谢solochen提出的问题。(已经修复)。

* 2016/11/08 00:25
	1. 重构项目，1.0正式被发布，支持Gradle！；=
	2. 使用更方便。
	
* 2016/11/08 11:12 
	1. 更改名字 LoopViewPagerLayout,1.0.5正式被发布。

* 2016/11/28 19:20 
	1. 修复LoopViewPagerLayout的(layout_height)高度自适应，小红点显示错误bug,1.0.6正式被发布。

* 2016/12/01 00:08
	1. 父布局，子布局里面设置padding或者margin，宽高设置match_parent或者wrap_content小红点错位；
	2. 解决默认数4个修改成动态值；
	3. 优化代码。

* 2016/12/01 13:18
	1. 设计了一个回调方法，让用户自己定义图片加载OnLoadImageViewListener。url参数支持泛型，回调回来的是Object根据实际情况强转；
	2. 图片加载框架推荐：Glide，Picasso，Fresco；
	3. 2.0.0正式被发布。

* 2016/12/01 13:18
	1. 支持小红点三种位置摆放（左，中，右）。

			
<br>

#### 技术交流大本营
>欢迎加入Android技术交流大群，群号码：554610222
> > Android技术交流，进群后请改名片.<br>例如：北京-李四.<br>群内交流以技术为主，乱发黄图乱发广告乱开车者一律踢.
> >
> ><a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=3fe01fcf10b71c29729a7b016477ceb899a6eb057e8c89cf1ea7b6773a477393"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="Android技术交流大群" title="Android技术交流大群"></a>

<br>

##MIT License

```
Copyright (c) 2016 Edwin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```





