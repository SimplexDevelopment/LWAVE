package app.simplexdev.lwave;

import app.simplexdev.lwave.listener.BlockListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LWAVE extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        if (this.getDataFolder().mkdirs()) {
            this.getLogger().info("Created data folder.");
            this.saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);

        this.reloadConfig();
    }

    @Override
    public void onDisable()
    {
        this.saveConfig();
    }
}
