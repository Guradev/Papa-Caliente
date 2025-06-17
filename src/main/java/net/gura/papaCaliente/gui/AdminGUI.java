package net.gura.papaCaliente.gui;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.game.GameManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AdminGUI {
    public static final Component TITLE = Component.text("ᴀᴅᴍɪɴɪꜱᴛʀᴀᴅᴏʀ ᴇᴠᴇɴᴛᴏ").color(NamedTextColor.DARK_RED);

    public static void openGUI(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, TITLE);

        inv.setItem(10, gameInfo());
        inv.setItem(12, clickItem(Material.LIME_WOOL, "§aᴇᴍᴘᴇᴢᴀʀ ᴇᴠᴇɴᴛᴏ", "Clic para forzar el inicio del evento"));
        inv.setItem(13, clickItem(Material.RED_WOOL, "§cᴘᴀʀᴀʀ ᴇᴠᴇɴᴛᴏ", "Clic para forzar el paro del evento"));
        inv.setItem(14, clickItem(Material.CLOCK, "§aʀᴇꜱᴇᴛᴇᴀʀ ᴄᴏɴᴛᴀᴅᴏʀ", "Clic para resetear el contador del evento"));
        inv.setItem(15, clickItem(Material.TNT, "§aᴇxᴘʟᴏᴛᴀʀ ᴘᴀᴘᴀ", "Forzar el explote de la papa (Causará que el usuario muera)"));
        inv.setItem(32, clickItem(Material.PLAYER_HEAD, "§aɢᴇꜱᴛɪᴏɴᴀʀ ᴊᴜɢᴀᴅᴏʀᴇꜱ", "Abre la GUI para gestionar a los jugadores"));
        inv.setItem(53, clickItem(Material.BARRIER, "§cᴄᴇʀʀᴀʀ", "Cierra el menú"));
        admin.openInventory(inv);

    }

    public static ItemStack gameInfo() {
        GameManager gm = PapaCaliente.getPlugin().getGameManager();

        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta meta = info.getItemMeta();
        meta.displayName(Component.text("ɪɴꜰᴏʀᴍᴀᴄɪóɴ ᴇᴠᴇɴᴛᴏ").color(NamedTextColor.DARK_RED).decoration(TextDecoration.ITALIC, false));
        info.setItemMeta(meta);

        String state = gm.getGameState().name();
        String players = String.valueOf(gm.getPlayers().size());
        String holder = gm.getCurrentHolder() != null ? gm.getCurrentHolder().getName() : "Ninguno";

        meta.lore(List.of(
                Component.text("Estado: ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(state, NamedTextColor.YELLOW)).decoration(TextDecoration.ITALIC, false),
                Component.text("Jugadores: ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(String.valueOf(players), NamedTextColor.GREEN)).decoration(TextDecoration.ITALIC, false),
                Component.text("Holder de Papa: ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(holder, NamedTextColor.RED)).decoration(TextDecoration.ITALIC, false)
        ));

        info.setItemMeta(meta);
        return info;
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