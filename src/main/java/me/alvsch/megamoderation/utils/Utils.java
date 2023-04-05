package me.alvsch.megamoderation.utils;

import me.alvsch.megamoderation.MuteEntry;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Utils {

    private static final HashMap<String, Long> seconds_per_unit = new HashMap<String, Long>() {{
        put("s", 1L);
        put("m", 60L);
        put("h", 3600L);
        put("d", 86400L);
    }};


    public static String color(String msg) { return ChatColor.translateAlternateColorCodes('&', msg); }

    public static List<String> color(String[] msg) {
        List<String> list = new ArrayList<>();
        for(String s : msg) {
            list.add(Utils.color(s));
        }
        return list;
    }

    public static boolean isValidContainer(Block block) {
        List<Material> validContainer = new ArrayList<>();
        validContainer.add(Material.CHEST);
        validContainer.add(Material.TRAPPED_CHEST);
        validContainer.add(Material.BARREL);

        return validContainer.contains(block.getType()) || block.getType().toString().toUpperCase().contains("SHULKER_BOX");
    }

    public static String title(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean hasPermission(CommandSender sender, String perm) {
        return perm == null || sender.hasPermission(perm);
    }

    public static String mergeList(String[] strings){
        return String.join(" ", strings);
    }

    public static String nullToEmpty(String str) {
        if(str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static String formatTime(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd'd' hh'h' mm'm' ss's'");
        return formatter.format(millis);

    }

    public static String formatString(String str, String name) {
        str = str.replace("%player%", name);


        return str;
    }

    public static String formatBanReason(BanEntry banEntry) {
        StringBuilder sb = new StringBuilder();

        if (banEntry.getExpiration() == null) {
            sb.append("You have been permanently banned!");
        } else {
            sb.append("You have been banned for: ")
                    .append(formatTime(new Date().getTime() - banEntry.getExpiration().getTime()))
                    .append(" from this server!");

        }

        sb.append("\n");
        sb.append("Reason: ").append(banEntry.getReason());

        return sb.toString();
    }

    public static String formatMuteReason(MuteEntry muteEntry) {
        StringBuilder sb = new StringBuilder();

        if (muteEntry.getExpiration() == null) {
            sb.append("You have been permanently muted!");
        } else {
            sb.append("You have been muted for: ")
                    .append(formatTime(new Date().getTime() - muteEntry.getExpiration().getTime()));

        }

        sb.append("\n");
        sb.append("Reason: ").append(muteEntry.getReason());

        return sb.toString();
    }

    public static String formatReport(CommandSender sender, Player target, String reason) {

        String s = "---- Report ----" + "\n" +
                target.getName() + " was reported for:" + "\n" +
                reason + "\n" +
                "Reported By: " + sender.getName() + "\n";

        return Utils.color("&6"+ s);
    }

    public static long stringToMilli(String s) {
        try {
            return Long.parseLong(s.substring(0, s.length() - 1)) * seconds_per_unit.get(s.substring(s.length() - 1)) * 1000L;
        } catch (Exception e) {
            return -1L;
        }
    }

    public static void staffchat(String msg, CommandSender sender){
        Bukkit.broadcast(Utils.color("&c[StaffChat] ") + sender.getName() + ": " + msg, "megamoderation.staffchat.see");
    }

}
