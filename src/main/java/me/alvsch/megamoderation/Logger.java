package me.alvsch.megamoderation;

import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.Bukkit;

public class Logger {
    public static void log(LogLevel level, String message) {
        if(message == null) return;

        switch (level) {
            case ERROR:
                Bukkit.getConsoleSender().sendMessage(Utils.color("&8[&c&lERROR&8] &f" + message));
                break;
            case WARNING:
                Bukkit.getConsoleSender().sendMessage(Utils.color("&8[&7WARNING&8] &f" + message));
                break;
            case INFO:
                Bukkit.getConsoleSender().sendMessage(Utils.color("&8[&fINFO&8] &f" + message));
                break;
            case SUCCESS:
                Bukkit.getConsoleSender().sendMessage(Utils.color("&8[&aSUCCESS&8] &f" + message));
                break;
            case OUTLINE:
                Bukkit.getConsoleSender().sendMessage(Utils.color("&7" + message));
                break;
        }


    }

    public enum LogLevel {ERROR, WARNING, INFO, SUCCESS, OUTLINE}
}
