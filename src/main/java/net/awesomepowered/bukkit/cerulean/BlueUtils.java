package net.awesomepowered.bukkit.cerulean;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by John on 10/2/2014.
 */
public class BlueUtils {

    public static BlueUtils instance = new BlueUtils();
    private File bungeeFile;
    private FileConfiguration bungeeConfig;
    private File LoggerFile;
    String prefix = "&a[&bCerulean&a]&r ";

    public static BlueUtils getInstance() {
        return instance;
    }

    public void sM(Player p, String message) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public void setPlayerInfo(Player p, String target, Object payload) {
        Cerulean.getInstance().getConfig().set("Players."+p.getUniqueId()+"."+target, payload);
        Cerulean.getInstance().saveConfig();
    }

    public int getPlayerPort(Player p) {
        Integer port = Cerulean.getInstance().getConfig().getInt("Players."+p.getUniqueId()+".Port");
        return port;
    }

    public boolean hasServer(UUID uuid) {
        Boolean booo = Cerulean.getInstance().getConfig().getBoolean("Players."+uuid+".Active");
        return booo;
    }

    public boolean isDone(UUID uuid) {
        Boolean booo = Cerulean.getInstance().getConfig().getBoolean("Players."+uuid+".Done");
        return booo;
    }

    public void addBungeeServer(Player p) {
        UUID playerID = p.getUniqueId();
        bungeeFile = new File(Cerulean.getInstance().bungeeConfigLocation);
        bungeeConfig = new YamlConfiguration();
        try {
            int port = Cerulean.getPort();
            bungeeConfig.load(bungeeFile);
            bungeeConfig.set("servers."+playerID+".address", "localhost:"+port);
            bungeeConfig.set("servers."+playerID+".motd", p.getName()+"\'s server");
            bungeeConfig.save(bungeeFile);
            System.out.println("Successful created "+ p.getName() +"\'s server");
            sM(p, "&3Setting up global server config");
            copyNewServer(p);
            Cerulean.getInstance().reloadBungee();
            setupServer(p, port);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void copyNewServer(Player p) {
        try {
            sM(p, "&3Copying server from template");
            Runtime.getRuntime().exec(Cerulean.getInstance().scriptsDirectory+ "newserver " + p.getUniqueId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupServer(final Player p, final int port) {
        new BukkitRunnable() {
            public void run() {
                sM(p, "&3Setting up your server config");
                try {
                    //load the file
                    FileInputStream in = new FileInputStream(Cerulean.getInstance().playerServerDirectory.replace("%PLAYER", p.getUniqueId().toString()));
                    Properties prop = new Properties();
                    prop.load(in);
                    in.close();
                    // set file properties and save
                    FileOutputStream out = new FileOutputStream(Cerulean.getInstance().playerServerDirectory.replace("%PLAYER", p.getUniqueId().toString()));
                    prop.setProperty("server-port", String.valueOf(port));
                    prop.store(out, null);
                    out.close();
                    setPlayerInfo(p,"Name", p.getName());
                    setPlayerInfo(p,"Active", true);
                    setPlayerInfo(p,"Port", port);
                    log2File("ServerSetups.txt", p.getName()+" "+System.currentTimeMillis()/1000);
                    sM(p, "&cS&6e&er&av&be&9r&5 &cs&6e&et&au&bp&9 &5d&co&6n&ee&a!");
                    sM(p, "&3Please do &6/cerulean home &3to start your server.");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskLater(Cerulean.getInstance(), 100);
    }

    public boolean checkPortAvailablity(int port) {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            return true; // The port is available
        } catch (IOException e) {
            return false; //The port is being used.
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startServer(final Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String playerID = p.getUniqueId().toString();
                    Runtime.getRuntime().exec(Cerulean.getInstance().scriptsDirectory + "startserver " + playerID);
                    System.out.println("Started server of " + p.getName());
                    log2File("ServerStarts.txt", p.getName()+" "+System.currentTimeMillis()/1000);
                    Cerulean.getInstance().sendPlayer(p, p.getUniqueId().toString(), 200);
                    sM(p, "&3Server started. Teleporting in 10 seconds..");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskLater(Cerulean.getInstance(), 20);
    }

    public void startServer(final UUID uuid, final Player p) {
        try {
            String target = Cerulean.getInstance().getConfig().getString("Players."+uuid+".Name");
            Runtime.getRuntime().exec(Cerulean.getInstance().scriptsDirectory + "startserver " + uuid);
            System.out.println("Started server of " + target + " for " + p.getName());
            log2File("ServerStarts.txt", target + " " + p.getName() + " " + System.currentTimeMillis()/1000);
            Cerulean.getInstance().sendPlayer(p, uuid.toString(), 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log2File(String fileName, Object logPayload) {
        try {
            LoggerFile = new File(Bukkit.getPluginManager().getPlugin("Cerulean").getDataFolder(), fileName);
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(LoggerFile, true)));
            out.println(logPayload);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
