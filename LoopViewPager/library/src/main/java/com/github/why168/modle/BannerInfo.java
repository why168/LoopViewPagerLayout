package com.github.why168.modle;

/**
 * @author Edwin.Wu
 * @version 2016/12/6 17:32
 * @since JDK1.8
 */
public class BannerInfo<T> {
    public T url;
    public String title;

    public BannerInfo(T url, String title) {
        this.url = url;
        this.title = title;
    }
}
