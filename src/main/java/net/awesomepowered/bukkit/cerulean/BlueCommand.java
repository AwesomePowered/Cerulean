package net.awesomepowered.bukkit.cerulean;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/12/2014.
 */
public class BlueCommand implements CommandExecutor {

    Cerulean plugin;

    public BlueCommand(Cerulean plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("cerulean.admin")) {
            if (args[0].equalsIgnoreCase("join")) {
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                if (p.hasPlayedBefore()) {
                    Cerulean.getInstance().sendPlayer((Player)sender, p.getUniqueId().toString(), 20);
                }
            }

            if (args[0].equalsIgnoreCase("start")) {
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                Player theSender = (Player) sender;
                if (BlueUtils.getInstance().hasServer(p.getUniqueId())) {
                    BlueUtils.getInstance().startServer(p.getUniqueId(), theSender);
                    BlueUtils.getInstance().sM(theSender, "&3Starting server of " + args[1]);
                } else {
                    sender.sendMessage(args[1] + " has no server");
                }
            }
        }
        return false;
    }
}
