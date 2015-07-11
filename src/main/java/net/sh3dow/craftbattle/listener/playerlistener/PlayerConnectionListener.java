package net.sh3dow.craftbattle.listener.playerlistener;

import net.sh3dow.craftbattle.Main;
import net.sh3dow.craftbattle.listener.MasterListener;
import net.sh3dow.craftbattle.runnable.LobbyTimer;
import net.sh3dow.craftbattle.utils.ICallback;
import net.sh3dow.craftbattle.utils.LocationUtil;
import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Level;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class PlayerConnectionListener extends MasterListener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();

        e.setJoinMessage(Settings.PREFIX+"§c"+p.getName()+" §bhas joined the game!");

        p.setAllowFlight(false);
        p.getInventory().clear();
        p.setExp(0);
        p.setFireTicks(0);
        p.setFoodLevel(20);
        p.setHealth(20D);

        teleportToLobby(p);

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
