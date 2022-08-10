package com.olexyn.min.log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.olexyn.min.log.LogPrint.END;
import static com.olexyn.min.log.LogPrint.LOAD;
import static com.olexyn.min.log.LogPrint.PLAIN;
import static com.olexyn.min.log.LogPrint.SAVE;
import static com.olexyn.min.log.LogPrint.START;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

public class LogU {

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

    private static String startMsg(@NotNull String msg, @Nullable Object... args) {
        if (args != null) { msg = String.format(msg, args); }
        return String.format("[START >    ]   %-80s", msg);
    }

    private static String endMsg(@NotNull String msg, @Nullable Object... args) {
        if (args != null) { msg = String.format(msg, args); }
        return String.format("[      > END]   %-80s", msg);
    }

    private static String loadMsg(@NotNull String msg, @Nullable Object... args) {
        if (args != null) { msg = String.format(msg, args); }
        return String.format("[LOAD       ]   %-80s", msg);
    }

    private static String saveMsg(@NotNull String msg, @Nullable Object... args) {
        if (args != null) { msg = String.format(msg, args); }
        return String.format("[       SAVE]   %-80s", msg);
    }

    private static String plainMsg(@NotNull String msg, @Nullable Object... args) {
        if (args != null) { msg = String.format(msg, args); }
        return String.format("[           ]   %-80s", msg);
    }






    private static void log(Level level, LogPrint logPrint, @NotNull String msg, @Nullable Object... args) {
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

    public static void infoStart(boolean is, @NotNull String msg, @Nullable Object... args) {
        if (is) { infoStart(msg, args); }
    }

    public static void infoStart(@NotNull String msg, @Nullable Object... args) {
        log(INFO, START, msg, args);
    }

    public static void infoEnd(boolean is, @NotNull String msg, @Nullable Object... args) {
        if (is) { infoStart(msg, args); }
    }

    public static void infoEnd(@NotNull String msg, @Nullable Object... args) {
        log(INFO, END, msg, args);
    }

    public static void infoPlain(boolean is, @NotNull String msg, @Nullable Object... args) {
        if (is) { infoPlain(msg, args); }
    }

    public static void infoPlain(@NotNull String msg, @Nullable Object... args) {
        log(INFO, PLAIN, msg, args);
    }

    public static void warnStart(@NotNull String msg, @Nullable Object... args) {
        log(WARNING, START, msg, args);
    }

    public static void warnEnd(@NotNull String msg, @Nullable Object... args) {
        log(WARNING, END, msg, args);
    }

    public static void warnPlain(@NotNull String msg, @Nullable Object... args) {
        log(WARNING, PLAIN, msg, args);
    }

    public static void save(@NotNull String msg, @Nullable Object... args) {
        log(INFO, SAVE, msg, args);
    }

    public static void load(@NotNull String msg, @Nullable Object... args) {
        log(INFO, LOAD, msg, args);
    }

    public static void warnNull() {
        log(WARNING, PLAIN, NULL_INPUT_MESSAGE);
    }

}
