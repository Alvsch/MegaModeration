package me.alvsch.megamoderation.utils;

import me.alvsch.megamoderation.MegaModeration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {

    public static ItemStack createItem(Material material, String name, int amount, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(Utils.color(name));
        meta.setLore(Utils.color(lore));

        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack createItem(Material material, String name, int amount, int id, MegaModeration plugin, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(new NamespacedKey(plugin, "id"), PersistentDataType.INTEGER, id);

        meta.setDisplayName(Utils.color(name));
        meta.setLore(Utils.color(lore));

        item.setItemMeta(meta);
        return item;
    }

    public static int getItemId(ItemStack item, MegaModeration plugin) {
        int id = 0;
        try {
            id = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "id"), PersistentDataType.INTEGER);
        } catch (NullPointerException e) {
            return -1;
        }
        return id;

    }

    public static void rename(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(Utils.color(name));

        item.setItemMeta(meta);

    }

    public static void updateLore(ItemStack item, String... lore) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

    }
    public static void setLore(ItemStack item, int line, String new_lore) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        assert lore != null;

        try {
            lore.set(line, Utils.color(new_lore));
        } catch (Exception ignored) {
        }

        item.setItemMeta(meta);

    }

}
