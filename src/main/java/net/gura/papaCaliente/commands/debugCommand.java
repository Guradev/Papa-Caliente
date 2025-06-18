package net.gura.papaCaliente.commands;

import net.gura.papaCaliente.utils.Debugger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// For debugging purposes ONLY (CAN BE SPAMMY)
public class debugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        Player player = (Player) commandSender;

        if (!commandSender.isOp()) {
            commandSender.sendMessage(Component.text("No tienes permisos para ejecutar este comando").color(NamedTextColor.RED));
            return true;
        }
        if (Debugger.isDebugEnabled()) {
            commandSender.sendMessage(Component.text("El modo debug ya fue habilitado").color(NamedTextColor.RED));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10F, 1F);
            return true;
        }
        if (args.length >= 2 && args[0].equalsIgnoreCase("debug")) {
            switch (args[1].toLowerCase()) {
                case "on":
                    Debugger.setDebugValue(true);
                    commandSender.sendMessage(Component.text("El modo debug ha sido habilitado correctamente").color(NamedTextColor.GREEN));
                    //Debug messages
                    break;
                case "off":
                    Debugger.setDebugValue(false);
                    commandSender.sendMessage(Component.text("El modo debug ha sido deshabilitado correctamente").color(NamedTextColor.GREEN));
                    break;
            }
        }
        return true;
    }
}
