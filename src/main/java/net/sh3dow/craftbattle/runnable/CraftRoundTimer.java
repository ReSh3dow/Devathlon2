package net.sh3dow.craftbattle.runnable;

import net.sh3dow.craftbattle.Main;
import net.sh3dow.craftbattle.items.craftitems.CraftableItems;
import net.sh3dow.craftbattle.entities.CustomCrafter;
import net.sh3dow.craftbattle.manager.GameState;
import net.sh3dow.craftbattle.utils.ICallback;
import net.sh3dow.craftbattle.utils.LocationUtil;
import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class CraftRoundTimer extends GameRunnable{
    private static int rounds;

    private static int curRound;
    private static int curItem;

    // Singelton
    private static CraftRoundTimer instance;

    public CraftRoundTimer(int maxTime, String defaultMessage) {
        super(maxTime, defaultMessage);
        rounds = 7;
        curRound = rounds;
        instance = this;
    }

    @Override
    public void countdownOver() {
        curRound--;
        if(curRound <= 0)
        {
            new DeathMatchTimer(16,"§cDm starts in §c%time%").start();
            return;
        }

        this.curItem = CraftableItems.getRandomItemID();
        Bukkit.broadcastMessage(Settings.PREFIX+"§bYou have to craft a(n) "+Material.getMaterial(curItem).toString().toUpperCase());
        Main.getInstance().setState(GameState.CRAFTING);

        if(curRound == rounds-1) {
            int i = 0;
            for (final Player all : Bukkit.getOnlinePlayers()) {
                i++;
                LocationUtil.getLocation("crafter." + i, new ICallback<Location>() {
                    public void set(Location obj, Object... objs) {
                        CustomCrafter crafter = new CustomCrafter("§cCrafter", UUID.fromString("e9013c2f-da01-425f-a48b-516f55e94386" + ""), all);
                        crafter.spawn(obj);
                    }
                });

            }
        }
    }

    @Override
    public void countdownStart() {
        Main.getInstance().setState(GameState.WAITING);

        for(Player all : Bukkit.getOnlinePlayers())
        {
            all.setGameMode(GameMode.SURVIVAL);
        }
    }

    @Override
    public void countdownTick(int curTick) {
        if(curTick == 90 || curTick == 60 || curTick == 30 || curTick == 15 || curTick == 10 || curTick == 5 || (curTick <= 3 && curTick> 0))
        {
            Bukkit.broadcastMessage(Settings.PREFIX + defaultMessage.replace("%time%", "" + curTick));
        }
    }



    public void restartTimer()
    {
        this.curItem = 0;
        this.curTime = this.maxTime;

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            public void run() {
                start();
            }
        },20L
        );

    }

    public static int getCurItem() {
        return curItem;
    }

    public static CraftRoundTimer getInstance() {
        return instance;
    }
}
