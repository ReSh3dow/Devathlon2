package net.sh3dow.craftbattle.entities.network;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public interface NettyInjector {

    public Packet packetIn(Packet packet,Player p);
    public Packet packetOut(Packet packet, Player p);
}
