package com.olexyn.min.log;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.olexyn.min.log.LogPrint.END;
import static com.olexyn.min.log.LogPrint.LOAD;
import static com.olexyn.min.log.LogPrint.PLAIN;
import static com.olexyn.min.log.LogPrint.SAVE;
import static com.olexyn.min.log.LogPrint.START;
import static com.olexyn.min.log.LogUtil.jobName;
import static java.lang.String.format;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

@SuppressWarnings("unused")
public final class LogU {

    public static final List<String> THREADS_TO_IGNORE_BELOW_WARNING = new ArrayList<>();

    private LogU() { }

    public static final String NULL_INPUT_MESSAGE = "expected parameter was NULL";

    private static Logger logger = make(null, null, null);

    public static void remake(String logFileDir, String projectPackageName, String format) {
        logger = make(logFileDir, projectPackageName, format);
    }

    public static Logger make(String logFileDir, String projectPackageName, String format) {
        if (logFileDir == null) {
            logFileDir = System.getProperty("user.dir");
        }
        var logger = Logger.getLogger("default");
        Arrays.stream(logger.getHandlers()).forEach(logger::removeHandler);
        logger.setUseParentHandlers(false);
        var ch = new ConsoleHandler();
        ch.setFormatter(new LogFormatter(projectPackageName, format));
        logger.addHandler(ch);

        try {
            var fh = new FileHandler(logFileDir, true);
            fh.setFormatter(new LogFormatter(projectPackageName, format));
            logger.addHandler(fh);
        } catch (IOException ignored) { }
        logger.setLevel(Level.INFO);
        return logger;
    }

    private static String startMsg(@NonNull String msg, @Nullable Object... args) {
        if (args != null) { msg = format(msg, args); }
        return format("[START >] %-60s", msg);
    }

    private static String endMsg(@NonNull String msg, @Nullable Object... args) {
        if (args != null) { msg = format(msg, args); }
        return format("[  > END] %-60s", msg);
    }

    private static String loadMsg(@NonNull String msg, @Nullable Object... args) {
        if (args != null) { msg = format(msg, args); }
        return format("[LOAD   ] %-60s", msg);
    }

    private static String saveMsg(@NonNull String msg, @Nullable Object... args) {
        if (args != null) { msg = format(msg, args); }
        return format("[   SAVE] %-60s", msg);
    }

    private static String plainMsg(@NonNull String msg, @Nullable Object... args) {
        if (args != null) { msg = format(msg, args); }
        return format("[       ] %-60s", msg);
    }

    public static void log(Level level, LogPrint logPrint, @NonNull String msg, @Nullable Object... args) {
        if (level.intValue() <= WARNING.intValue()) {
            for (var ignore : THREADS_TO_IGNORE_BELOW_WARNING) {
                if (jobName().contains(ignore)) {
                    return;
                }
            }
        }

        switch (logPrint) {
            case START -> msg = startMsg(msg, args);
            case END -> msg = endMsg(msg, args);
            case PLAIN -> msg = plainMsg(msg, args);
            case SAVE -> msg = saveMsg(msg, args);
            case LOAD -> msg = loadMsg(msg,args);
            default -> { }
        }
        logger.log(level, msg);
    }

    public static void infoStart(boolean is, @NonNull String msg, @Nullable Object... args) {
        if (is) { infoStart(msg, args); }
    }

    public static void infoStart(@NonNull String msg, @Nullable Object... args) {
        log(INFO, START, msg, args);
    }

    public static void infoEnd(boolean is, @NonNull String msg, @Nullable Object... args) {
        if (is) { infoStart(msg, args); }
    }

    public static void infoEnd(@NonNull String msg, @Nullable Object... args) {
        log(INFO, END, msg, args);
    }

    public static void infoPlain(boolean is, @NonNull String msg, @Nullable Object... args) {
        if (is) { infoPlain(msg, args); }
    }

    public static void infoPlain(@NonNull String msg, @Nullable Object... args) {
        log(INFO, PLAIN, msg, args);
    }

    public static void warnStart(@NonNull String msg, @Nullable Object... args) {
        log(WARNING, START, msg, args);
    }

    public static void warnEnd(@NonNull String msg, @Nullable Object... args) {
        log(WARNING, END, msg, args);
    }

    public static void warnPlain(@NonNull String msg, @Nullable Object... args) {
        log(WARNING, PLAIN, msg, args);
    }

    public static void save(@NonNull String msg, @Nullable Object... args) {
        log(INFO, SAVE, msg, args);
    }

    public static void load(@NonNull String msg, @Nullable Object... args) {
        log(INFO, LOAD, msg, args);
    }

    public static void warnNull() {
        log(WARNING, PLAIN, NULL_INPUT_MESSAGE);
    }

}
