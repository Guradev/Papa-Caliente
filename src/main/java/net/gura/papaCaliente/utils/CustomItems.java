package net.gura.papaCaliente.utils;

import net.gura.papaCaliente.PapaCaliente;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
        // Guardamos un identificador en la papa caliente para asegurarnos que sea la correcta.
        NamespacedKey itemkey = new NamespacedKey(PapaCaliente.getPlugin(), "papa_caliente");
        meta.getPersistentDataContainer().set(itemkey, PersistentDataType.BYTE,(byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isPapaCaliente(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey itemkey = new NamespacedKey(PapaCaliente.getPlugin(), "papa_caliente");
        Byte tag = meta.getPersistentDataContainer().get(itemkey, PersistentDataType.BYTE);
        return tag != null && tag == (byte) 1;
    }

    //Add custom admin and mod items
}
