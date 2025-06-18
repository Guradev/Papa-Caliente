package net.gura.papaCaliente.nms;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Particles {

    public static void spawnParticles(Player player, ParticleOptions particle, float size, boolean playSound, boolean showToAll) {
        Location loc = player.getLocation().add(0, 1, 0);
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        ClientboundLevelParticlesPacket packet = new ClientboundLevelParticlesPacket(
                particle,
                true,
                true,
                x, y, z,
                size, size, size,
                0.01f,
                30
        );

        if (showToAll) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                ServerPlayer nmsPlayer = ((CraftPlayer) online).getHandle();
                nmsPlayer.connection.send(packet);
                if (playSound) {
                    online.playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2f, 1f);
                }
            }
        } else {
            ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
            nmsPlayer.connection.send(packet);
            if (playSound) {
                player.playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2f, 1f);
            }
        }
    }
}
