package com.github.why168.modle;

/**
 * 指示器位置
 * Left左
 * center中
 * Right右
 *
 * @author Edwin.Wu
 * @version 2016/12/1 18:59
 * @since JDK11
 */
public enum IndicatorLocation {
    Left(1),
    Center(0),
    Right(2);

    private final int value;

    IndicatorLocation(int idx) {
        this.value = idx;
    }

    public int getValue() {
        return value;
    }
}
