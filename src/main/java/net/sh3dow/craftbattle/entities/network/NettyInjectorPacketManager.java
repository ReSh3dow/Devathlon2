package net.sh3dow.craftbattle.entities.network;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class NettyInjectorPacketManager extends ChannelDuplexHandler{

    NettyInjector injector;
    Player player;

    public NettyInjectorPacketManager(NettyInjector injector,Player player)
    {
        this.injector = injector;
        this.player = player;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        msg = injector.packetIn((Packet) msg,player);
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        msg = injector.packetOut((Packet) msg,player);
        super.write(ctx, msg, promise);
    }

}
