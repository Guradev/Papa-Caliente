package net.gura.papaCaliente.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UserGUI implements Listener {
    public static final Component TITLE = Component.text("ᴘᴀᴘᴀ ᴄᴀʟɪᴇɴᴛᴇ").color(NamedTextColor.DARK_GRAY);

    public static void openGUI(Player usuario) {
        Inventory inv = Bukkit.createInventory(null, 54, TITLE);
        usuario.openInventory(inv);

    }

    private static ItemStack clickItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name).color(NamedTextColor.DARK_RED).asComponent());

        if (lore != null) {
            meta.lore(List.of(Component.text(lore, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        }

        item.setItemMeta(meta);
        return item;
    }

}
