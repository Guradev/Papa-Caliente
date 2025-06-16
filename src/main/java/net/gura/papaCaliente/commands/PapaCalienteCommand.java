package net.gura.papaCaliente.commands;

import net.gura.papaCaliente.PapaCaliente;
import net.gura.papaCaliente.game.GameManager;
import net.gura.papaCaliente.gui.AdminGUI;
import net.gura.papaCaliente.gui.PlayerManagerGUI;
import org.bukkit.Bukkit;
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
            commandSender.sendMessage("§cSolo jugadores pueden ejecutar este comando.");
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("gui") && args[1].equalsIgnoreCase("admin")) {
            AdminGUI.openGUI(player);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
            gm.startGame();
            return true;
        }

        if (args[0].equalsIgnoreCase("addplayer")) {
            if (args.length < 2) {
                commandSender.sendMessage("Debes especificar un nombre de jugador para agregar.");
                return true;
            }

            Player jugador = Bukkit.getPlayerExact(args[1]);
            if (jugador == null) {
                commandSender.sendMessage("El jugador no está en línea.");
                return true;
            }
            gm.addPlayer(jugador);
            commandSender.sendMessage("Has agregado a " + jugador.getName() + " al evento.");
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
