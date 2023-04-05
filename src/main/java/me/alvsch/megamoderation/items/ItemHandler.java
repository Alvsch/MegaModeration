package me.alvsch.megamoderation.items;

import me.alvsch.megamoderation.MegaModeration;
import me.alvsch.megamoderation.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemHandler {

    public ItemStack player_tracker;
    public ItemStack freeze_staff;
    public ItemStack vanish_on;
    public ItemStack vanish_off;
    public ItemStack inspect_tool;

    public ItemHandler(MegaModeration plugin) {

        player_tracker = ItemUtils.createItem(Material.COMPASS, "&fPlayer Tagger", 1, 1, plugin, "&fTagged Player: ");
        freeze_staff = ItemUtils.createItem(Material.BLAZE_ROD, "&bFreeze Staff", 1, 2, plugin);
        vanish_on = ItemUtils.createItem(Material.LIME_DYE, "&7Vanish: &aON", 1, 3, plugin);
        vanish_off = ItemUtils.createItem(Material.GRAY_DYE, "&7Vanish: &cOFF", 1, 4, plugin);
        inspect_tool = ItemUtils.createItem(Material.CHEST, "&eInspect Tool", 1, 5, plugin);

    }

}
