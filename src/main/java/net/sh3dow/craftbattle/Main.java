package net.sh3dow.craftbattle;

import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.sh3dow.craftbattle.commands.DefaultCommand;
import net.sh3dow.craftbattle.listener.normallistener.BlockListener;
import net.sh3dow.craftbattle.listener.normallistener.ProtectListener;
import net.sh3dow.craftbattle.listener.normallistener.ServerListener;
import net.sh3dow.craftbattle.listener.playerlistener.PlayerConnectionListener;
import net.sh3dow.craftbattle.listener.playerlistener.PlayerDamageListener;
import net.sh3dow.craftbattle.listener.playerlistener.PlayerGameListener;
import net.sh3dow.craftbattle.manager.GameState;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class Main extends JavaPlugin {

    private static Main instance;
    private static Logger logger = Logger.getLogger("CraftBattlePlugin");

    private GameState state;

    @Override
    public void onEnable() {
        instance = this;

        this.state = GameState.LOBBY;

        registerListener();
        registerCommands();
    }

    @Override
    public void onDisable() {}

    private void registerListener()
    {
        new PlayerConnectionListener();
        new BlockListener();
        new ProtectListener();
        new PlayerGameListener();
        new ServerListener();
        new PlayerDamageListener();

    }

    private void registerCommands()
    {
        this.getCommand("craftbattle").setExecutor(new DefaultCommand());
    }

    // Logger
    public static Logger getPluginLogger()
    {
        return logger;
    }

    // Instance
    public static Main getInstance()
    {
        return instance;
    }

    // GameState
    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }
}
