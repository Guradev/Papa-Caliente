package net.gura.papaCaliente.utils;

import net.gura.papaCaliente.PapaCaliente;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class CustomItems {

    //Item de Papa Caliente
    public static ItemStack PapaCaliente(Player holder) {
        ItemStack item = new ItemStack(Material.POTATO);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("¡Papa Caliente!").color(NamedTextColor.RED));
        meta.lore(List.of(
                Component.text("Esta es la papa caliente", NamedTextColor.GRAY),
                Component.text("Explotará en ", NamedTextColor.GRAY)
                        .append(Component.text("TEST" + "s", NamedTextColor.WHITE))
        ));
        // Guardamos un identificador en la papa caliente para asegurarnos que sea la correcta.
        NamespacedKey NBT = new NamespacedKey(PapaCaliente.getPlugin(), "papa_caliente");
        meta.getPersistentDataContainer().set(NBT, PersistentDataType.BYTE,(byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isPapaCaliente(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey NBT = new NamespacedKey(PapaCaliente.getPlugin(), "papa_caliente");
        Byte tag = meta.getPersistentDataContainer().get(NBT, PersistentDataType.BYTE);
        return tag != null && tag == (byte) 1;
    }

    //Add custom admin and mod items
}
