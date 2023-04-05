package me.alvsch.megamoderation.events;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.MuteEntry;
import me.alvsch.megamoderation.User;
import me.alvsch.megamoderation.utils.InventoryUtils;
import me.alvsch.megamoderation.utils.Utils;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;

import java.util.Date;

public class PlayerListener implements Listener {

    MegaModeration plugin;

    public PlayerListener(MegaModeration plugin) {
        this.plugin = plugin;
    }

    // PlayerJoinEvent
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        // Make sure player is in database
        plugin.usermap.registerPlayer(player);


        // Send result of version check
        if (player.hasPermission("megamoderation.debug") || player.getUniqueId().equals(plugin.getAuthor())) {

            if (plugin.getVersion().equals(MegaModeration.Version.OUTDATED)) {
                player.sendMessage(Utils.color("&cThis version of " + plugin.getName() + " is outdated"));
            }
            if (plugin.getVersion().equals(MegaModeration.Version.UNSTABLE)) {
                player.sendMessage(Utils.color("&4This version of " + plugin.getName() + " is unstable"));
            }
        }

        // Hide all players in vanish
        if (!player.hasPermission("megamoderation.vanish.see")) {
            for (Player p : plugin.vanish) {
                player.hidePlayer(plugin, p);
            }
        }
    }

    // PlayerQuitEvent
    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        // Exit mod-mode
        if(plugin.modmode.containsKey(e.getPlayer())) {
            plugin.commandManager.get("modmode").onCommand(e.getPlayer(), new String[0]);
        }
    }

    // PlayerInteractEvent
    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        // Clicked block
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        Player player = e.getPlayer();
        if(e.getClickedBlock() == null) {
            return;
        }

        // Silent container
        if(plugin.silentcontainer.contains(player.getUniqueId())) {
            if(!Utils.isValidContainer(e.getClickedBlock())){
                return;
            }

            // Silently open container
            Container container = (Container) e.getClickedBlock().getState();
            Inventory inv = InventoryUtils.createInventory(null, container.getInventory().getSize() / 9, "Silent Container");
            inv.setContents(container.getInventory().getContents());
            player.openInventory(inv);

            e.setCancelled(true);
            player.sendMessage(plugin.getPhrase("silentcontainer"));
        }

    }

    // PlayerMoveEvent
    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        if(plugin.freeze.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){

        staffchat(e);
        globalmute(e);
        muted(e);

    }
    private void muted(AsyncPlayerChatEvent e) {
        User user = plugin.usermap.getUser(e.getPlayer());
        MuteEntry muteEntry = user.getMute();
        if(muteEntry != null) {
            if(muteEntry.getExpiration() != null && muteEntry.getExpiration().getTime() < new Date().getTime()) {
                user.setMute(null);
                return;
            }

            e.getPlayer().sendMessage(Utils.formatMuteReason(user.getMute()));
            e.setCancelled(true);
        }
    }
    private void globalmute(AsyncPlayerChatEvent e){
        // Globalmute on
        if(plugin.globalmute){
            if(e.getPlayer().hasPermission("megamoderation.globalmute.bypass")) {
                return;
            }
            // Cancel event
            e.setCancelled(true);
        }
    }
    private void staffchat(AsyncPlayerChatEvent e){
        // Staffchat on
        if(plugin.staffchat.contains(e.getPlayer().getUniqueId())){
            // Send staffchat message
            Utils.staffchat(e.getMessage(), e.getPlayer());
            e.setCancelled(true);
        }
    }


}
