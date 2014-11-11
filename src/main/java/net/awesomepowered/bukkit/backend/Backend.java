package net.awesomepowered.bukkit.backend;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by John on 10/3/2014.
 */
public class Backend extends JavaPlugin {

    public static Backend instance;

    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        getCommand("cerulean").setExecutor(new CeruleanCommand());
        wait4Player();
    }

    public static Backend getInstance() {
        return instance;
    }


    public void wait4Player () {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() == 0) {
                    System.out.println("No players logged in for 90 seconds, shutting down.");
                    Bukkit.shutdown();
                }
            }
        }.runTaskLater(this, 1800);
    }
}
