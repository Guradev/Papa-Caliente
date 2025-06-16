package net.gura.papaCaliente.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerManagerGUI {

    public static final Component TITLE =  Component.text("ɢᴇꜱᴛɪóɴ ᴊᴜɢᴀᴅᴏʀᴇꜱ");
    public static void openGUI(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, TITLE.color(NamedTextColor.DARK_RED).asComponent());
        admin.openInventory(inv);

        inv.setItem(45, clickItem(Material.ARROW, "§aʀᴇᴛʀᴏᴄᴇᴅᴇʀ", "Vuelve al menú principal"));
        inv.setItem(53, clickItem(Material.BARRIER, "§cᴄᴇʀʀᴀʀ", "Cierra el menú"));


    }

    private static ItemStack clickItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name).color(NamedTextColor.DARK_RED).asComponent());

        if (lore != null) {
            meta.lore(List.of(Component.text(lore, NamedTextColor.GRAY)));
        }

        item.setItemMeta(meta);
        return item;
    }
}
