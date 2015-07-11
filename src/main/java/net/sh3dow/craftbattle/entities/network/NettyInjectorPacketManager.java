package net.sh3dow.craftbattle.entities.network;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.Packet;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class NettyInjectorPacketManager extends ChannelDuplexHandler{
    public

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        msg = packetIn((Packet)msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        msg = packetOut((Packet)msg);
        super.write(ctx, msg, promise);
    }

}
