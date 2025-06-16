package net.gura.papaCaliente.listeners;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.game.GameManager;
import net.gura.papaCaliente.gui.AdminGUI;
import net.gura.papaCaliente.gui.PlayerManagerGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerManagerGUIListener implements Listener {

    private final Pattern pagePattern = Pattern.compile("\\[(\\d+)/(\\d+)]");

    @EventHandler
    public void ClickItem(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player admin)) return;

        String title = event.getView().title().toString();

        if (!title.contains("ɢᴇꜱᴛɪóɴ ᴊᴜɢᴀᴅᴏʀᴇꜱ")) return;
        event.setCancelled(true);

        if (event.getClickedInventory() != event.getInventory()) return;

        GameManager gm = PapaCaliente.getPlugin().getGameManager();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Material type = clickedItem.getType();

        int currentPage = 0;
        Matcher matcher = pagePattern.matcher(title);
        if (matcher.find()) {
            currentPage = Integer.parseInt(matcher.group(1)) - 1;
        }

        switch (type) {
            case ARROW -> {
                ItemMeta meta = clickedItem.getItemMeta();
                if (meta == null) return;
                Component nameComponent = meta.displayName();
                if (nameComponent == null) return;
                String plainName = PlainTextComponentSerializer.plainText().serialize(nameComponent);
                if (plainName.contains("ʀᴇᴛʀᴏᴄᴇᴅᴇʀ")) {
                    if (currentPage > 0) {
                        PlayerManagerGUI.openGUI(admin, currentPage - 1);
                    } else {
                        AdminGUI.openGUI(admin);
                    }
                } else if (plainName.contains("ᴀᴠᴀɴᴢᴀʀ")) {
                    PlayerManagerGUI.openGUI(admin, currentPage + 1);
                }
            }

            case BARRIER -> admin.closeInventory();

            case SLIME_BALL -> {
                Bukkit.getOnlinePlayers().forEach(gm::addPlayer);
                admin.sendMessage("§aTodos los jugadores online fueron añadidos al evento.");
                PlayerManagerGUI.openGUI(admin, currentPage);
            }

            case TNT -> {
                gm.getPlayers().forEach(gm::removePlayer);
                admin.sendMessage("§cTodos los jugadores fueron eliminados del evento.");
                PlayerManagerGUI.openGUI(admin, currentPage);
            }

            case PLAYER_HEAD -> {
                if (!(clickedItem.getItemMeta() instanceof SkullMeta skullMeta)) return;

                OfflinePlayer target = skullMeta.getOwningPlayer();
                if (target == null || !target.isOnline()) {
                    admin.sendMessage("§cJugador no disponible.");
                    return;
                }

                Player targetPlayer = (Player) target;

                if (event.getClick() == ClickType.LEFT) {
                    admin.teleport(targetPlayer);
                    admin.sendMessage("§aTeletransportado a §f" + targetPlayer.getName());
                    admin.playSound(admin.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F);
                } else if (event.getClick() == ClickType.RIGHT) {
                    gm.removePlayer(targetPlayer);
                    admin.sendMessage("§c" + targetPlayer.getName() + " fue eliminado del evento.");
                    targetPlayer.sendMessage("§cHas sido eliminado del evento por un administrador.");
                    PlayerManagerGUI.openGUI(admin, currentPage);
                }
            }
        }
    }
}