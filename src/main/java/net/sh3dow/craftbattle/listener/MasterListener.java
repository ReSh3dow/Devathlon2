package net.sh3dow.craftbattle.listener;

import net.sh3dow.craftbattle.Main;
import org.bukkit.event.Listener;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public abstract class MasterListener implements Listener {

    public MasterListener()
    {
        Main.getInstance().getServer().getPluginManager().registerEvents(this,Main.getInstance());
    }
}
