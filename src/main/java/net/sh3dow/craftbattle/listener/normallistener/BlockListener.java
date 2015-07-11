package net.sh3dow.craftbattle.listener.normallistener;

import net.sh3dow.craftbattle.listener.MasterListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class BlockListener extends MasterListener{
    @EventHandler
    public void onBreak(BlockBreakEvent e)
    {
        if(e.getPlayer().isOp())return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e)
    {
        if(e.getPlayer().isOp())return;
        e.setCancelled(true);
    }
}
