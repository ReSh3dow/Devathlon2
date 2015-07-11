package net.sh3dow.craftbattle.runnable;

import net.sh3dow.craftbattle.Main;
import net.sh3dow.craftbattle.manager.GameManager;
import net.sh3dow.craftbattle.manager.GameState;
import net.sh3dow.craftbattle.utils.ICallback;
import net.sh3dow.craftbattle.utils.LocationUtil;
import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Sh3dow on 11.07.2015.
 */
public class DeathMatchTimer extends GameRunnable{

    public DeathMatchTimer(int maxTime, String defaultMessage) {
        super(maxTime, defaultMessage);
    }

    @Override
    public void countdownOver() {

        LocationUtil.getLocation("dmspawn", new ICallback<Location>() {
            public void set(Location obj, Object... objs) {
                for(Player all : Bukkit.getOnlinePlayers())
                {
                    all.teleport(obj);
                    if(GameManager.isAlive(all.getName()))
                    {
                        for(ItemStack item : GameManager.getItems(all.getName()))
                        {
                            all.getInventory().addItem(item);

                        }
                    }
                }
            }
        });

        Main.getInstance().setState(GameState.DM);
    }

    @Override
    public void countdownStart() {
        Bukkit.broadcastMessage(Settings.PREFIX+"§cThe DeathMatch is going to start now ...");
        Main.getInstance().setState(GameState.WAITING);
    }

    @Override
    public void countdownTick(int curTick) {
        if(curTick == 15 || curTick == 10 || curTick == 5 || (curTick <= 3 && curTick > 0))
        {
            Bukkit.broadcastMessage(Settings.PREFIX+defaultMessage.replace("%time%",""+curTick));
        }
    }
}
