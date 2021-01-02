package dev.nitrocommand.bukkit;

import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.Marker;

import java.util.logging.Level;

public class BukkitBasedLogger implements Logger {
    private Plugin plugin;

    public BukkitBasedLogger(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return plugin.getLogger().getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {

    }

    @Override
    public void trace(String format, Object arg) {

    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {

    }

    @Override
    public void trace(String format, Object... arguments) {

    }

    @Override
    public void trace(String msg, Throwable t) {

    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String msg) {

    }

    @Override
    public void trace(Marker marker, String format, Object arg) {

    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {

    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {

    }

    @Override
    public void debug(String format, Object arg) {

    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {

    }

    @Override
    public void debug(String format, Object... arguments) {

    }

    @Override
    public void debug(String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String msg) {

    }

    @Override
    public void debug(Marker marker, String format, Object arg) {

    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isInfoEnabled() {
        return plugin.getLogger().isLoggable(Level.INFO);
    }


    @Override
    public void info(String msg) {
        plugin.getLogger().info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        plugin.getLogger().info(String.format(format, arg));

    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        plugin.getLogger().info(String.format(format, arg1, arg2));

    }

    @Override
    public void info(String format, Object... arguments) {
        plugin.getLogger().info(String.format(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {
        plugin.getLogger().info(msg);
        plugin.getLogger().info(t.getMessage());

    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    @Override
    public void info(Marker marker, String msg) {

    }

    @Override
    public void info(Marker marker, String format, Object arg) {

    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String msg) {
        plugin.getLogger().warning(msg);

    }

    @Override
    public void warn(String format, Object arg) {
        plugin.getLogger().warning(String.format(format, arg));

    }

    @Override
    public void warn(String format, Object... arguments) {
        plugin.getLogger().warning(String.format(format, arguments));

    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        plugin.getLogger().warning(String.format(format, arg1, arg2));

    }

    @Override
    public void warn(String msg, Throwable t) {
        plugin.getLogger().warning(msg);
        plugin.getLogger().warning(t.getMessage());
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return true;
    }

    @Override
    public void warn(Marker marker, String msg) {

    }

    @Override
    public void warn(Marker marker, String format, Object arg) {

    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String msg) {
        plugin.getLogger().severe(msg);

    }

    @Override
    public void error(String format, Object arg) {
        plugin.getLogger().info(String.format(format, arg));

    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        plugin.getLogger().info(String.format(format, arg1, arg2));

    }

    @Override
    public void error(String format, Object... arguments) {
        plugin.getLogger().info(String.format(format, arguments));

    }

    @Override
    public void error(String msg, Throwable t) {
        plugin.getLogger().severe(msg);
        plugin.getLogger().severe(t.getMessage());
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return true;
    }

    @Override
    public void error(Marker marker, String msg) {

    }

    @Override
    public void error(Marker marker, String format, Object arg) {

    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {

    }
}
