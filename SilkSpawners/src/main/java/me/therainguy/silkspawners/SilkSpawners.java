package me.therainguy.silkspawners;

import me.therainguy.silkspawners.config.Config;
import me.therainguy.silkspawners.listeners.SpawnerListenerNewer;
import me.therainguy.silkspawners.listeners.SpawnerListenerOld;
import me.therainguy.silkspawners.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class SilkSpawners extends JavaPlugin {

    private static SilkSpawners instance;
    public Config config = new Config(this, "config.yml");

    @Override
    public void onEnable() {
        instance = this;

        if (!config.existConfig()) {
            config.getConfig().options().copyDefaults(false);
            config.saveDefaultConfig();
            config.reloadConfig();
        }

        if (Version.getCurrentVersion().isOlder(Version.v1_13_R2)) {
            Bukkit.getPluginManager().registerEvents(new SpawnerListenerOld(), this);
        } else {
            Bukkit.getPluginManager().registerEvents(new SpawnerListenerNewer(), this);
        }

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public static SilkSpawners getInstance() {
        return instance;
    }
}