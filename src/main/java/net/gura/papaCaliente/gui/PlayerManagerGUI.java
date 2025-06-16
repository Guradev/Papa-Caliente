package net.gura.papaCaliente.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerManagerGUI {

    public static final String TITLE = ChatColor.DARK_RED + "ɢᴇꜱᴛɪóɴ ᴊᴜɢᴀᴅᴏʀᴇꜱ";
    public static void openGUI(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, TITLE);
        admin.openInventory(inv);

        inv.setItem(45, clickItem(Material.ARROW, "§aʀᴇᴛʀᴏᴄᴇᴅᴇʀ", "Vuelve al menú principal"));
        inv.setItem(53, clickItem(Material.BARRIER, "§cᴄᴇʀʀᴀʀ", "Cierra el menú"));


    }

    private static ItemStack clickItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null) {
            meta.setLore(java.util.List.of("§7" + lore));
        }
        item.setItemMeta(meta);
        return item;
    }

}
