package net.awesomepowered.bukkit.cerulean;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;


/**
 * Created by John on 10/1/2014.
 */
public class CeruleanCommand implements CommandExecutor {

    public ArrayList<Player> coolDown = new ArrayList(); //m8 don't judge me

    Cerulean plugin;

    public CeruleanCommand(Cerulean plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length == 0) {
                sender.sendMessage(ChatColor.AQUA + "/cerulean new");
                sender.sendMessage(ChatColor.AQUA + "/cerulean home");
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("new")) {
                    if (!BlueUtils.getInstance().hasServer(p.getUniqueId())) {
                        BlueUtils.getInstance().addBungeeServer(p);
                    } else {
                        sender.sendMessage(ChatColor.AQUA + Cerulean.prefix + ChatColor.RED + "You already have a server!");
                        sender.sendMessage(ChatColor.AQUA + Cerulean.prefix + ChatColor.RED + "Please do /cerulean home");
                    }
                }

                if (args[0].equalsIgnoreCase("home")) {
                    if (!coolDown.contains(p)) {
                        if (BlueUtils.getInstance().isDone(p.getUniqueId())) {
                            BlueUtils.getInstance().sM(p, "&4Unable to start server");
                            BlueUtils.getInstance().sM(p, "&4Error: &cYou have marked your entry as done.");
                        } else {
                            if (BlueUtils.getInstance().hasServer(p.getUniqueId())) {
                                BlueUtils.getInstance().sM(p, "&3Starting your server.");
                                coolDown.add(p);
                                if (BlueUtils.getInstance().checkPortAvailablity(BlueUtils.getInstance().getPlayerPort(p))) {
                                    BlueUtils.getInstance().startServer(p);
                                } else {
                                    BlueUtils.getInstance().sM(p, "&3Seems like your server is already online, teleporting you..");
                                    Cerulean.getInstance().sendPlayer(p, p.getUniqueId().toString(), 20);
                                }
                            } else {
                                p.sendMessage("You have no server. Please create one first!");
                            }
                        }
                    } else {
                        BlueUtils.getInstance().sM(p, "&aYou have recently started your server, please wait for it to start.");
                    }
                }

            }

            } else {
            sender.sendMessage("Not a player!");
        }

        return false;
    }

}
