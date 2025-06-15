package net.gura.papaCaliente.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CustomItems {

    //Item de Papa Caliente
    public static ItemStack PapaCaliente(Player holder, int tickstoboom) {
        ItemStack item = new ItemStack(Material.POTATO);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c¡Papa Caliente!");
        meta.setLore(List.of(
                "§7Esta es la papa caliente",
                "§7Explotará en §f" + (tickstoboom/20) + "s"
        ));
        item.setItemMeta(meta);
        return item;
    }

    //Add custom admin and mod items
}
