package net.sh3dow.craftbattle.listener.playerlistener;

import net.sh3dow.craftbattle.Main;
import net.sh3dow.craftbattle.listener.MasterListener;
import net.sh3dow.craftbattle.manager.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class PlayerDamageListener extends MasterListener {

    @EventHandler
    public void onDamage(EntityDamageEvent e)
    {
        if(Main.getInstance().getState() != GameState.DM)e.setCancelled(true);
    }

    @EventHandler
    public void onDamagebyEntity(EntityDamageByEntityEvent e)
    {
        if(Main.getInstance().getState() != GameState.DM)e.setCancelled(true);
    }
}
