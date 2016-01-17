package com.talesdev.beyondthelimit;

import jline.console.ConsoleReader;
import jline.console.CursorBuffer;
import org.pmw.tinylog.Configuration;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.LogEntry;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.pmw.tinylog.writers.LogEntryValue;
import org.pmw.tinylog.writers.PropertiesSupport;
import org.pmw.tinylog.writers.Writer;

import java.io.IOException;
import java.io.PrintStream;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author MoKunz
 */
@PropertiesSupport(name = "customconsole", properties = {})
public class CustomConsoleWriter implements Writer {
    private ConsoleReader console;
    private CursorBuffer stashed;

    @Override
    public Set<LogEntryValue> getRequiredLogEntryValues() {
        return EnumSet.of(LogEntryValue.LEVEL, LogEntryValue.RENDERED_LOG_ENTRY);
    }

    @Override
    public void init(final Configuration configuration) {
        // Do nothing
        try {
            console = new ConsoleReader(System.in, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(final LogEntry logEntry) {
        getPrintStream(logEntry.getLevel()).print(logEntry.getRenderedLogEntry());
    }

    @Override
    public void flush() {
        try {
            console.print(ConsoleReader.RESET_LINE + "");
            console.flush();
            try {
                console.drawLine();
            } catch (Throwable ex) {
                console.getCursorBuffer().clear();
            }
            console.flush();
        } catch (IOException ex) {
            Logger.error("I/O exception flushing console output");
        }
    }

    @Override
    public void close() {
        // Do nothing
    }
    private static PrintStream getPrintStream(final Level level) {
        if (level == Level.ERROR || level == Level.WARNING) {
            return System.err;
        } else {
            return System.out;
        }
    }
}
