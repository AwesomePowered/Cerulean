package net.awesomepowered.bukkit.backend;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

/**
 * Created by John on 10/3/2014.
 */
public class Listeners implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent ev) {
        check4shutdown();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent ev) {
        Player p = ev.getPlayer();
        File file = new File("");
        System.out.println(file.getAbsolutePath());
        if (file.getAbsolutePath().split("/")[4].equalsIgnoreCase(ev.getPlayer().getUniqueId().toString())) {
            p.setBanned(false);
            p.setOp(true);
            System.out.println("Server owner has logged in");
        }
    }

    public void check4shutdown() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() == 0) {
                    System.out.println("No players online, shutting down");
                    Bukkit.shutdown();
                }
            }
        }.runTaskLater(Backend.getInstance(), 20);
    }

}
