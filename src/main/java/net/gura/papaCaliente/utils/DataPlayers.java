package net.gura.papaCaliente.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DataPlayers {

    private final ItemStack[] contents;
    private final ItemStack[] armor;

    public SaveDataInventory(Player player) {
        this.contents = player.getInventory().getContents();
        this.armor = player.getInventory().getArmorContents();
    }

    public DataPlayers(ItemStack[] contents, ItemStack[] armor) {
        this.contents = contents;
        this.armor = armor;
    }

    public void restore(Player player) {
        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armor);
    }
}
