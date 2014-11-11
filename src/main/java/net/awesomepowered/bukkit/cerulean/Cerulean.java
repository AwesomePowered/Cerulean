package net.awesomepowered.bukkit.cerulean;

import com.google.common.base.Charsets;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by John on 10/1/2014.
 */
public class Cerulean extends JavaPlugin {

    public static int currentPort;
    public static String prefix = "[Cerulean] ";
    public static Cerulean instance;
    //public BlueUtils utils = new BlueUtils(this);

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        saveConfig();
        currentPort = getConfig().getInt("CurrentPort");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "ValhallaCC");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getCommand("cerulean").setExecutor(new CeruleanCommand(this));
        getCommand("blue").setExecutor(new BlueCommand(this));
    }

    public void onDisable() {
        getConfig().set("CurrentPort", currentPort);
        saveConfig();
    }

    public void reloadBungee() {
        Player p = getServer().getOnlinePlayers().iterator().next();
        p.sendPluginMessage(this, "ValhallaCC", "greload".getBytes(Charsets.UTF_8));
    }

    public void sendPlayer(final Player p, final String server, final int ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(server);
                p.sendPluginMessage(getInstance(), "BungeeCord", out.toByteArray());
            }
        }.runTaskLater(this, ticks);
    }

    public static Cerulean getInstance() {
        return instance;
    }

    public static int getPort() {
        currentPort = currentPort + 1;
        getInstance().getConfig().set("CurrentPort", currentPort);
        return currentPort;
    }
}
