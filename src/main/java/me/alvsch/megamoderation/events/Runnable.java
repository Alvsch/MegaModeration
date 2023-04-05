package me.alvsch.megamoderation.events;

import me.alvsch.megamoderation.MegaModeration;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Runnable {

    private final MegaModeration plugin;

    public Runnable(MegaModeration plugin) {
        this.plugin = plugin;
    }

    public void startVanishActionBarTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : plugin.vanish) {
                    if(player.isOnline()) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(plugin.getPhrase("vanish-actionbar")));
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 2 * 20);
    }
    public void startFrozenActionBarTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : plugin.freeze) {
                    if(player.isOnline()) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(plugin.getPhrase("frozen")));
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 2 * 20);
    }

}
