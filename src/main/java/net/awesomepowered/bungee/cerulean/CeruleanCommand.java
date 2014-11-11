package net.awesomepowered.bungee.cerulean;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.File;


/**
 * Created by John on 10/1/2014.
 */
public class CeruleanCommand extends Command {

    Cerulean plugin;

    public CeruleanCommand(Cerulean plugin) {
        super("gcerulean");
        this.plugin = plugin;
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("cerulean.admin")) {
            ProxiedPlayer theSender = (ProxiedPlayer) sender;
            if (args[0].equalsIgnoreCase("join")) {
                ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
                if (p != null) {
                    theSender.connect(p.getServer().getInfo());
                } else {
                    sender.sendMessage("Player is not online");
                }
            }

            if (args[0].equalsIgnoreCase("kick")) {
                ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(args[1]);
                if (targetPlayer != null) {
                    targetPlayer.disconnect("You have been kicked!");
                } else {
                    theSender.sendMessage("Player is not online");
                }
            }

            if (args[0].equalsIgnoreCase("bye")) {
                theSender.disconnect("Till next time!");
            }

        }
    }
}
