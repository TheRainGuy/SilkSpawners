package me.therainguy.silkspawners.listeners;

import me.therainguy.silkspawners.SilkSpawners;
import me.therainguy.silkspawners.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SpawnerListenerNewer implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() == Material.SPAWNER) {
            if (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {

                Config config = SilkSpawners.getInstance().config;
                String permission = config.getString("Settings.Permission");

                if (permission == null || player.hasPermission(permission)) {

                    int chance = (Math.min(config.getInt("Settings.Chance"), 100));

                    if (new Random().nextInt(100) < chance) {
                        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SPAWNER));

                        String success = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.SUCCESS"));
                        player.sendMessage(success);
                    } else {
                        String fail = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.FAIL"));

                        player.sendMessage(fail);
                    }
                }
            }
        }
    }
}
