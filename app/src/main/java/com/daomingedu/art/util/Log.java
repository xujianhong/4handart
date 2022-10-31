package com.daomingedu.art.util;


import com.daomingedu.art.BuildConfig;

import timber.log.Timber;

/**
 * 打印Log
 * Created by xjh on 2016/6/30.
 */
public class Log {
    public static boolean isPrint = BuildConfig.DEBUG;

    public Log() {
    }


    public static void i(Object obj) {
        if(isPrint) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Timber.i(String.valueOf(obj));
        }

    }

    public static void e(Object obj) {
        if(isPrint) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];

            Timber.e(String.valueOf(obj));
        }

    }
    public static void d(Object obj) {
        if(isPrint) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Timber.d(String.valueOf(obj));
        }

    }

    public static void w(Object obj) {
        if(isPrint) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Timber.tag(getDefaultTag(stackTraceElement)).w(String.valueOf(obj));
        }

    }
    /**
     * 获取默认的TAG名称.
     * 比如在MainActivity.java中调用了日志输出.
     * 则TAG为MainActivity
     */
    public static String getDefaultTag(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        String tag = stringArray[0];
        return tag;
    }

}
