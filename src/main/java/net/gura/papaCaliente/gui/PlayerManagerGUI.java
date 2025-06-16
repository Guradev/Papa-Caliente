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
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerManagerGUI {

    private static final int ITEMS_PER_PAGE = 36;

    public static void openGUI(Player admin, int page) {
        GameManager gm = PapaCaliente.getPlugin().getGameManager();
        List<Player> players = new ArrayList<>(gm.getPlayers());

        int totalPages = (int) Math.ceil(Math.max(1, players.size()) / (double) ITEMS_PER_PAGE);
        page = Math.max(0, Math.min(page, totalPages - 1)); // Clamp page between 0 and totalPages - 1

        Component title = Component.text("ɢᴇꜱᴛɪóɴ ᴊᴜɢᴀᴅᴏʀᴇꜱ [")
                .append(Component.text((page + 1) + "/" + totalPages).color(NamedTextColor.DARK_RED))
                .append(Component.text("]"))
                .color(NamedTextColor.DARK_RED);

        Inventory inv = Bukkit.createInventory(null, 54, title);
        admin.openInventory(inv);

        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, players.size());

        for (int i = start; i < end; i++) {
            Player target = players.get(i);
            inv.addItem(createPlayerHead(gm, target));
        }

        // Add controls
        if (page > 0) {
            inv.setItem(45, clickItem(Material.ARROW, "§aʀᴇᴛʀᴏᴄᴇᴅᴇʀ", "Página anterior"));
        } else {
            inv.setItem(45, clickItem(Material.ARROW, "§aʀᴇᴛʀᴏᴄᴇᴅᴇʀ", "Volver al menú de administrador"));
        }

        if (page < totalPages - 1) {
            inv.setItem(53, clickItem(Material.ARROW, "§aᴀᴠᴀɴᴢᴀʀ", "Página siguiente"));
        } else {
            inv.setItem(53, clickItem(Material.BARRIER, "§cᴄᴇʀʀᴀʀ", "Cierra el menú"));
        }

        inv.setItem(47, clickItem(Material.SLIME_BALL, "§aᴀɢʀᴇɢᴀʀ ᴛᴏᴅᴏꜱ", "Agregar a todos los jugadores online"));
        inv.setItem(49, clickItem(Material.TNT, "§cʟɪᴍᴘɪᴀʀ ʟɪꜱᴛᴀ", "Quitar a todos los jugadores del evento"));
    }

    private static ItemStack createPlayerHead(GameManager gm, Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        meta.setOwningPlayer(player);
        meta.displayName(Component.text(player.getName(), NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Estado: ", NamedTextColor.GRAY)
                .append(Component.text(gm.isInGame(player) ? "En juego" : "Fuera del juego", NamedTextColor.GREEN))
                .decoration(TextDecoration.ITALIC, false));

        if (gm.getCurrentHolder() != null && gm.getCurrentHolder().equals(player)) {
            lore.add(Component.text("¡Tiene la papa caliente!", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.text("Click izquierdo para teletransportarte al jugador", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Click derecho para eliminarlo del evento", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        head.setItemMeta(meta);
        return head;
    }

    private static ItemStack clickItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name).color(NamedTextColor.DARK_RED).decoration(TextDecoration.ITALIC, false));
        if (lore != null) {
            meta.lore(List.of(Component.text(lore, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        }
        item.setItemMeta(meta);
        return item;
    }
}