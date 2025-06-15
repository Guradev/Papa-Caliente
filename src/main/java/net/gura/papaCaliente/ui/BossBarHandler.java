package net.gura.papaCaliente.ui;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class BossBarHandler {
    private final BossBar bar;
    private final Set<Player> viewers = new HashSet<>();

    public BossBarHandler(String title, float progress, BossBar.Color color, BossBar.Overlay overlay) {
        Component name = Component.text(title, NamedTextColor.WHITE);
        this.bar = BossBar.bossBar(name, progress, color, overlay);
    }

    public void show(Audience audience, Player player) {
        audience.showBossBar(bar);
        viewers.add(player);
    }

    public void hide(Audience audience, Player player) {
        audience.hideBossBar(bar);
        viewers.remove(player);
    }
    public void updateTitle(String title) {
        bar.name(Component.text(title, NamedTextColor.WHITE));
    }

    public void updateProgress(float progress) {
        bar.progress(progress);
    }

    public void updateColor(BossBar.Color color) {
        bar.color(color);
    }

    public void updateOverlay(BossBar.Overlay overlay) {
        bar.overlay(overlay);
    }

    public void hideAll(Audience audience) {
        audience.hideBossBar(bar);
        viewers.clear();
    }
}
