package net.awesomepowered.bungee.cerulean;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;

/**
 * Created by John on 10/1/2014.
 */
public class Cerulean extends Plugin implements Listener {


    String[] bcmds = {
            "/stop",
            "/reload",
    };

    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new CeruleanCommand(this));
        getProxy().getPluginManager().registerListener(this, this);
    }

    @net.md_5.bungee.event.EventHandler
    public void onChat(ChatEvent ev) {
        String message = ev.getMessage();
        ProxiedPlayer sender = (ProxiedPlayer) ev.getSender();
        System.out.println(sender.getName() + "> " + message);
        for (String x : bcmds) {
            if (message.contains(x)) {
                ev.setCancelled(true);
            }
        }
    }

}
