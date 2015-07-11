package net.sh3dow.craftbattle.runnable;

import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class GameOverTimer extends GameRunnable{
    private Player winner;

    public GameOverTimer(int maxTime, String defaultMessage,Player winner) {
        super(maxTime, defaultMessage);
        this.winner = winner;
    }

    @Override
    public void countdownOver() {
        Bukkit.shutdown();
    }

    @Override
    public void countdownStart() {
        Bukkit.broadcastMessage(Settings.PREFIX+"§aThe game ist over the player $b"+winner.getName()+" $ahas won the game!");
    }

    @Override
    public void countdownTick(int curTick) {
        if(curTick == 10 ||(curTick <= 5 && curTick > 0))
        {
            Bukkit.broadcastMessage(Settings.PREFIX+defaultMessage.replace("%time%",curTick+""));
        }
    }
}
