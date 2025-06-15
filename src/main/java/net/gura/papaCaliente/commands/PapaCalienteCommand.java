package net.gura.papaCaliente.commands;

import net.gura.papaCaliente.gui.AdminGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PapaCalienteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("§cSolo jugadores pueden ejecutar este comando.");
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("gui") && args[1].equalsIgnoreCase("admin")) {
            AdminGUI.openGUI(player);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
            //Force start Game Logic
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("stop")) {
            //Force stop Game Logic
        }

        return true;
    }
}
