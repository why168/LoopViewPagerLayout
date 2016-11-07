package com.github.why168.entity;

/**
 * Loop style
 * 默认empty 深度depth 缩小zoo
 *
 * @USER Edwin
 * @DATE 2016/11/2 00:41
 */
public enum LoopStyle {
    Empty(-1),
    Depth(1),
    Zoom(2);

    private int value;

    LoopStyle(int idx) {
        this.value = idx;
    }

    public int getValue() {
        return value;
    }

}
