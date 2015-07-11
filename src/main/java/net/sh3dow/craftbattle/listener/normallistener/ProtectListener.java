package net.sh3dow.craftbattle.listener.normallistener;

import net.sh3dow.craftbattle.listener.MasterListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import sun.font.CreatedFontTracker;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class ProtectListener extends MasterListener{

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e)
    {

        e.setCancelled(true);
    }

    @EventHandler
    public void EntityExplode(EntityExplodeEvent e)
    {
        e.setCancelled(true);
    }
}
