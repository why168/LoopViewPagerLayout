package com.github.why168.modle;

/**
 * Loop style
 * 默认empty
 * 深度depth
 * 缩小zoo
 *
 * @author Edwin.Wu
 * @version 2016/11/2 00:41
 * @since JDK11
 */
public enum LoopStyle {
    Empty(-1),
    Depth(1),
    Zoom(2);

    private final int value;

    LoopStyle(int idx) {
        this.value = idx;
    }

    public int getValue() {
        return value;
    }

}
