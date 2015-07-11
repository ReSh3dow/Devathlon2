package net.sh3dow.craftbattle.listener.playerlistener;

import net.sh3dow.craftbattle.Main;
import net.sh3dow.craftbattle.entities.CustomCrafter;
import net.sh3dow.craftbattle.listener.MasterListener;
import net.sh3dow.craftbattle.manager.GameManager;
import net.sh3dow.craftbattle.manager.GameState;
import net.sh3dow.craftbattle.runnable.CraftRoundTimer;
import net.sh3dow.craftbattle.runnable.GameOverTimer;
import net.sh3dow.craftbattle.runnable.LobbyTimer;
import net.sh3dow.craftbattle.utils.ICallback;
import net.sh3dow.craftbattle.utils.LocationUtil;
import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class PlayerConnectionListener extends MasterListener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();

        e.setJoinMessage(Settings.PREFIX + "§c" + p.getName() + " §bhas joined the game!");

        p.setAllowFlight(false);
        p.getInventory().clear();
        p.setExp(0);
        p.setFireTicks(0);
        p.setFoodLevel(20);
        p.setHealth(20D);
        p.setGameMode(GameMode.ADVENTURE);

        teleportToLobby(p);


        if(Bukkit.getOnlinePlayers().size() >= 2)
        {
            new LobbyTimer(10,"The Game ist starting in %time%").start();

        }
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();

        e.setQuitMessage(Settings.PREFIX+"§c"+p.getName()+" §bleft the game");

        if(Main.getInstance().getState() != GameState.LOBBY)
        {
            if(Bukkit.getOnlinePlayers().size() <= 1)
            {
                Main.getInstance().getLogger().log(Level.INFO,"Restarting the server");

                Bukkit.getScheduler().cancelAllTasks();
                new GameOverTimer(16,"§cThe server is shuts down in §7%time% §csecond(s)",p).start();
            }
        }

        GameManager.removePlayer(p.getName());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e)
    {
        Player p = e.getPlayer();
        if(Main.getInstance().getState() != GameState.LOBBY)
        {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER,"§aAlready ingame!");
        }

        if(Bukkit.getOnlinePlayers().size() >= Settings.maxPlayer)
        {
            e.disallow(PlayerLoginEvent.Result.KICK_FULL,"§cThe server is already full");
        }

        GameManager.addPlayer(p.getName());
    }


    private void teleportToLobby(final Player p)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            public void run() {
                LocationUtil.getLocation("lobby", new ICallback<Location>() {
                    public void set(Location obj, Object... objs) {
                        p.teleport(obj);
                    }
                });
            }
        },20L);
    }
}
