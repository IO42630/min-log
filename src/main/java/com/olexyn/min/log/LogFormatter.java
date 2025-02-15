package com.olexyn.min.log;

import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import static com.olexyn.min.log.LogUtil.jobName;

public class LogFormatter extends SimpleFormatter {

    private static String FORMAT = "[%1$tF %1$tT][%2$-7s][%3$-20s] %4$-80s [%5$s]\n";
    private static String ROOT_PKG = "com.olexyn.";
    private static final String LOG_PKG = "com.olexyn.min.log";

    public LogFormatter(String projectPackageName, String format) {
        if (projectPackageName != null) {
            ROOT_PKG = projectPackageName;
        }
        if (format != null) {
            FORMAT = format;
        }
    }

    @Override
    public synchronized String format(LogRecord logRecord) {
        String msg = logRecord.getMessage();
        return String.format(FORMAT,
            new Date(logRecord.getMillis()),
            logRecord.getLevel().getLocalizedName(),
            jobName(),
            msg,
            methodName()
        );
    }



    public static String methodName() {
        int pos = 1;
        var callerStack = Thread.currentThread().getStackTrace();
        while (
            callerStack[pos].getClassName().startsWith(LOG_PKG) ||
                !callerStack[pos].getClassName().startsWith(ROOT_PKG)
        ) {
            pos++;
            if (pos > 100) { return ""; }
        }
        return String.format(
            "%s.%s",
            callerStack[pos].getClassName().split(ROOT_PKG)[1],
            callerStack[pos].getMethodName()
        );
    }

}
