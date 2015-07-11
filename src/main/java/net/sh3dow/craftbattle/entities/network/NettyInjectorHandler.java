package net.sh3dow.craftbattle.entities.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.sh3dow.craftbattle.utils.ReflectionUtils;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class NettyInjectorHandler {

    private static Map<String,Channel> channels = new HashMap<String,Channel>();

    public static void injectChannel(Player p, NettyInjector injector)
    {
        if(channels.containsKey(p.getName()))return;

        NetworkManager manager = ((CraftPlayer)p).getHandle().playerConnection.networkManager;

        Channel channel = (Channel)ReflectionUtils.getObject(manager.getClass(),"channel",manager);
        channel.pipeline().addBefore("packet_handler","injection_handler",new NettyInjectorPacketManager(injector,p));

        channels.put(p.getName(),channel);
    }

    public static void unject(Player p)
    {
        if(channels.containsKey(p.getName())) {
            channels.get(p.getName()).pipeline().remove("injection_handler");
            channels.remove(p.getName());
        }
    }

}
