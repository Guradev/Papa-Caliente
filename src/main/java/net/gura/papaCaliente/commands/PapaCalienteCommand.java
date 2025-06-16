package net.gura.papaCaliente.commands;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.game.GameManager;
import net.gura.papaCaliente.gui.AdminGUI;
import net.gura.papaCaliente.gui.PlayerManagerGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PapaCalienteCommand implements CommandExecutor {

    GameManager gm = PapaCaliente.getPlugin().getGameManager();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String @NotNull [] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("§6Solo jugadores pueden ejecutar este comando.");
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("gui") && args[1].equalsIgnoreCase("admin")) {
            if (!player.hasPermission("papacaliente.admin")) {
                player.sendMessage("§cNo tienes permisos.");
                return true;
            }
            AdminGUI.openGUI(player);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
            gm.startGame();
            return true;
        }

        if (args[0].equalsIgnoreCase("addplayer")) {
            if (args.length < 2) {
                commandSender.sendMessage(Component.text( "Debes especificar un nombre de usuario").color(NamedTextColor.RED));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10F, 1F);
                return true;
            }

            Player jugador = Bukkit.getPlayerExact(args[1]);
            if (jugador == null) {
                commandSender.sendMessage(Component.text( "El jugador no está en línea").color(NamedTextColor.RED));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10F, 1F);
                return true;
            }
            gm.addPlayer(jugador);
            commandSender.sendMessage(Component.text("Has agregado a " + jugador.getName() + " al evento").color(NamedTextColor.GREEN));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10F, 1F);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("stop")) {
            gm.stopGame();
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("gui") && args[1].equalsIgnoreCase("playermanager")) {
            PlayerManagerGUI.openGUI(player);
            return true;
        }
        return true;
    }
}
