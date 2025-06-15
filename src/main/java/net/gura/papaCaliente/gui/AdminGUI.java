package net.gura.papaCaliente.gui;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminGUI {
    public static final String TITLE = ChatColor.DARK_RED + "Adminstrador de Evento";

    public static void openGUI(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 9, TITLE);

        inv.setItem(0, gameInfo());
        inv.setItem(1, clickItem(Material.LIME_WOOL, "§aEmpezar Evento", "Clic para forzar el inicio del evento"));
        inv.setItem(2, clickItem(Material.RED_WOOL, "§cParar Evento", "Clic para forzar el paro del evento"));
        inv.setItem(3, clickItem(Material.CLOCK, "§aResetear CountDown", "Clic para resetear el contador del evento"));
        inv.setItem(4, clickItem(Material.TNT, "§aExplotar Papa", "Forzar el explote de la papa (Causará que el usuario muera)"));
        inv.setItem(5, clickItem(Material.LIME_WOOL, "§aGestionar Jugadores", "Abre la GUI para gestionar a los jugadores"));
        inv.setItem(8, clickItem(Material.BARRIER, "§cCerrar", null));
        admin.openInventory(inv);

    }

    public static ItemStack gameInfo() {
        GameManager gm = PapaCaliente.getPlugin().getGameManager();

        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "§fInformación del Evento");

        String state = gm.getGameState().name();
        String players = String.valueOf(gm.getPlayers().size());
        String holder = gm.getCurrentHolder() != null ? gm.getCurrentHolder().getName() : "No hay holder";

        meta.setLore(java.util.List.of(
                "Estado: " + state,
                "Jugadores: " + players,
                "Holder de Papa" + holder
        ));

        info.setItemMeta(meta);
        return info;
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