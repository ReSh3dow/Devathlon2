package net.sh3dow.craftbattle.runnable;

import net.sh3dow.craftbattle.Main;
import org.bukkit.Bukkit;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public abstract class GameRunnable {

    public abstract void countdownOver();
    public abstract void countdownStart();
    public abstract void countdownTick(int curTick);

    protected int maxTime;
    protected int curTime;

    protected String defaultMessage;

    private int schedulerID;

    public GameRunnable(int maxTime, String defaultMessage)
    {
        this.maxTime = maxTime;
        this.curTime = maxTime;

        this.defaultMessage = defaultMessage;
    }

    public void start()
    {
        countdownStart();

        schedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                curTime--;

                if(curTime == 0)
                {
                    Bukkit.getScheduler().cancelTask(schedulerID);
                    countdownOver();
                }

                countdownTick(curTime);
            }
        },20L , 20L);
    }

    public void stop()
    {
        Bukkit.getScheduler().cancelTask(schedulerID);
    }
}
