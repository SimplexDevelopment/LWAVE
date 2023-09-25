package app.simplexdev.lwave;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LWAVE extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
