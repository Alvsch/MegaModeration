package me.alvsch.megamoderation.events;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.User;
import me.alvsch.megamoderation.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ModModeEvents implements Listener {

    MegaModeration plugin;

    public ModModeEvents(MegaModeration plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        // Clicked on player
        if(!(e.getRightClicked() instanceof Player)) {
            return;
        }
        // Get item clicked with
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return;
        }

        // Get item id
        int id = ItemUtils.getItemId(item, plugin);
        if(id == 1) {
            // Compass
            List<String> lore = meta.getLore();
            if(lore == null) lore = new ArrayList<>();
            lore.set(0, "Tagged Target: " + e.getRightClicked().getName());
            meta.setLore(lore);
            player.sendMessage("You tagged: " + e.getRightClicked().getName());
        } else if (id == 2) {
            // Freeze wand
            String[] args = {e.getRightClicked().getName()};
            plugin.commandManager.get("freeze").onCommand(e.getPlayer(), args);
        } else if (id == 5) {
            // Inspect
            String[] args = {e.getRightClicked().getName()};
            plugin.commandManager.get("invsee").onCommand(e.getPlayer(), args);
        }

        item.setItemMeta(meta);
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        // Right-clicked
        if(!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        // Get item clicked with
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return;
        }

        // Get item id
        int id = ItemUtils.getItemId(item, plugin);
        if (id == 3) {
            // Vanish on
            plugin.commandManager.get("vanish").onCommand(e.getPlayer(), new String[0]);
            e.getPlayer().getInventory().setItemInMainHand(plugin.itemHandler.vanish_off);

        } else if (id == 4) {
            // Vanish off
            plugin.commandManager.get("vanish").onCommand(e.getPlayer(), new String[0]);
            e.getPlayer().getInventory().setItemInMainHand(plugin.itemHandler.vanish_on);

        }
    }

    @EventHandler
    public void playerDropItem(PlayerDropItemEvent e) {
        User user = plugin.usermap.getUser(e.getPlayer());
        if(user.isModmode() && !e.getPlayer().hasPermission("megamoderation.modemode.full")) {
            e.setCancelled(true);
        }
    }

}
