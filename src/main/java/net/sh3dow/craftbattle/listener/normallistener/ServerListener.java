package net.sh3dow.craftbattle.listener.normallistener;

import net.sh3dow.craftbattle.Main;
import net.sh3dow.craftbattle.listener.MasterListener;
import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class ServerListener extends MasterListener {
    @EventHandler
    public void onPing(ServerListPingEvent e)
    {
        e.setMaxPlayers(Settings.maxPlayer);
        e.setMotd("§a"+Main.getInstance().getState().toString());
    }

}
