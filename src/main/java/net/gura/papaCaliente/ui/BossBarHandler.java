package net.gura.papaCaliente.ui;

import net.kyori.adventure.Adventure;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class BossBarHandler {
    private final Adventure adventure;
    private final BossBar bar;
    private final Set<Player> viewers = new HashSet<>();

    public BossBarHandler() {
        this.adventure = adventure;
        this.bar = BossBar.bossBar(Component.text("Esperando Jugadores..."), 1.0f, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS)
    }

}
