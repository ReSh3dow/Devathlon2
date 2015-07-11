package net.sh3dow.craftbattle.commands;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import net.sh3dow.craftbattle.utils.LocationUtil;
import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class DefaultCommand implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player))return false;

        Player p = (Player)sender;

        if(args.length == 0)
        {
            p.sendMessage("");
            p.sendMessage("");

            TextComponent infoMsg = new TextComponent();
            infoMsg.setText(Settings.PREFIX + "§7Devathlon 2015 - §aCraftBattle §7by ReSh3dow");
            infoMsg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://github.com/ReSh3dow"));
            infoMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bTwitch: http://twitch.tv/§6ReSh3dow").create()));

            p.spigot().sendMessage(infoMsg);

            if(!p.hasPermission("craftbattle.admin"))return false;

            p.sendMessage("");
            p.sendMessage(Settings.PREFIX+"§c========= §bCommandsInfo §c==========");
            p.sendMessage(Settings.PREFIX+"§e/craftbattle setlobby");
            p.sendMessage(Settings.PREFIX+"§e/craftbattle setgamespawn");
            p.sendMessage(Settings.PREFIX+"§e/craftbattle setcrafter <1-4>");
            p.sendMessage(Settings.PREFIX+"§e/craftbattle setdmspawn");
            p.sendMessage(Settings.PREFIX+"§c========= §bCommandsInfo §c==========");

            return false;
        }

        if(!p.hasPermission("craftbattle.admin")){
                p.sendMessage(Settings.ERROR+" §cYou don't have permissions to execute this command!");
            return false;
        }

        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("setlobby")) {
                LocationUtil.setLocation(p.getLocation(),"lobby");
                p.sendMessage(Settings.PREFIX+"§bSuccessfully set the lobby");
                return false;
            }

            if(args[0].equalsIgnoreCase("setgamespawn"))
            {
                LocationUtil.setLocation(p.getLocation(),"gamespawn");
                p.sendMessage(Settings.PREFIX+"§bSuccessfully set the gamespawn");
                return false;
            }


            if(args[0].equalsIgnoreCase("setdmspawn"))
            {
                LocationUtil.setLocation(p.getLocation(),"dm");
                p.sendMessage(Settings.PREFIX + "§bSuccessfully set the dmspawn");
                return false;
            }

            return false;
        }
        if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("setcrafter"));
            int locId = Integer.parseInt(args[1]);

            LocationUtil.setLocation(p.getLocation(),"crafter."+locId);
            p.sendMessage(Settings.PREFIX+"§bSuccessfully set the crafter's spawn number "+locId);
        }


        return false;
    }
}
