package net.sh3dow.craftbattle.runnable;

import net.sh3dow.craftbattle.Main;
import net.sh3dow.craftbattle.manager.GameState;
import net.sh3dow.craftbattle.utils.ICallback;
import net.sh3dow.craftbattle.utils.LocationUtil;
import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class LobbyTimer extends GameRunnable{


    public LobbyTimer(int maxTime, String defaultMessage) {
        super(maxTime, defaultMessage);
    }

    @Override
    public void countdownOver() {
        Bukkit.broadcastMessage(Settings.PREFIX + "§7The Game is going to start now!");

        Main.getInstance().setState(GameState.WAITING);

        LocationUtil.getLocation("gamespawn", new ICallback<Location>() {
            public void set(Location obj, Object... objs) {
                for(Player all : Bukkit.getOnlinePlayers()){
                    all.teleport(obj);
                }
            }
        });


        new CraftRoundTimer(15,"§bStarting in §7%time% §bsecond(s)").start();
    }

    @Override
    public void countdownStart() {

    }

    @Override
    public void countdownTick(int curTick) {
        if(curTick == 90 || curTick == 60 || curTick == 30 || curTick == 15 || curTick == 10 || curTick == 5 || (curTick <= 3 && curTick> 0))
        {
            Bukkit.broadcastMessage(Settings.PREFIX+defaultMessage.replace("%time%",""+curTick));
        }
    }
}
