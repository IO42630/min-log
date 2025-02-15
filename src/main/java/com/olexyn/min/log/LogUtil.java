package com.olexyn.min.log;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogUtil {

    public static String jobName() {
        return Thread.currentThread().getName();
    }
}
