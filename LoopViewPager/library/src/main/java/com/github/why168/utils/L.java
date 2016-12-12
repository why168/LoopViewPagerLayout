package com.github.why168.utils;

import android.util.Log;

/**
 * Print Log
 *
 * @author Edwin.Wu
 * @version 2016/6/12 上午1:02
 * @since JDK1.8
 */
public class L {
    public static boolean deBug = false;
    public static String TAG = "Edwin";

    public static void e(String msg) {
        if (deBug)
            Log.e(TAG, msg);
    }

    public static void e(String TAG, String msg) {
        if (deBug)
            Log.e(TAG, msg);
    }

    public static void e(String TAG, Throwable tr) {
        if (deBug)
            Log.e(TAG, "Error——", tr);
    }

    public static void e(String TAG, String msg, Throwable tr) {
        if (deBug)
            Log.e(TAG, msg, tr);
    }
}
