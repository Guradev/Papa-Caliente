package net.gura.papaCaliente.logics;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

import static net.gura.papaCaliente.PapaCaliente.plugin;

public class Countdown {

    public void start(int seconds, Consumer<Integer> onTick, Runnable onFinish) {
        new BukkitRunnable() {
            int TimeLeft = seconds;

            @Override
            public void run() {
                if (TimeLeft <= 0) {
                    onFinish.run();
                    cancel();
                    return;
                }

                onTick.accept(TimeLeft);
                TimeLeft--;
            }
        }.runTaskTimer(plugin, 0,20);
    }
}
