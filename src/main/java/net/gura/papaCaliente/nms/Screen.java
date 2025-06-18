package net.gura.papaCaliente.nms;

import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Screen {

    public static void flashRed(Player player, Plugin plugin, int durationSeconds, int flashIntervalTicks) {

        new BukkitRunnable() {
            int totalFlashes = (durationSeconds * 20) / flashIntervalTicks;
            int flashesDone = 0;

            @Override
            public void run() {
                if (flashesDone >= totalFlashes || !player.isOnline()) {
                    cancel();
                    return;
                }

                sendFlash(player);
                flashesDone++;
            }
        }.runTaskTimer(plugin, 0L, flashIntervalTicks);
    }

    public static void sendFlash(Player player) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

        ClientboundEntityEventPacket packet = new ClientboundEntityEventPacket(nmsPlayer, (byte) 2);
        nmsPlayer.connection.send(packet);
    }
}
