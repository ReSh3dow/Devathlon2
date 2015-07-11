package net.sh3dow.craftbattle.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import net.sh3dow.craftbattle.entities.network.NettyInjector;
import net.sh3dow.craftbattle.entities.network.NettyInjectorHandler;
import net.sh3dow.craftbattle.items.gearitems.GearItems;
import net.sh3dow.craftbattle.manager.GameManager;
import net.sh3dow.craftbattle.runnable.CraftRoundTimer;
import net.sh3dow.craftbattle.utils.ReflectionUtils;
import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.*;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class CustomCrafter implements NettyInjector{

    private static Random random = new Random();

    private String name;
    private UUID uuid;
    private int id = random.nextInt(99999);

    private Player player;

    private int itemId = 0;
    private DataWatcher dataWatcher;


    /**
     *
     * @param name Customentity name
     * @param uuid uuid of costom entity
     * @param player which should see the entity
     */
    public CustomCrafter(String name,UUID uuid, Player player)
    {
        this.name = name;
        this.uuid = uuid;
        this.player = player;
    }
    public void spawn(Location loc){
        injectPlayer(player);

        // With value and signature of GommeHD
        addToTabList("eyJ0aW1lc3RhbXAiOjE0MzY1NTY2MDM0MzUsIn" +
                "Byb2ZpbGVJZCI6ImU5MDEzYzJmZGEwMTQyNWZhNDhiNTE2ZjU1ZTk0Mzg2IiwicHJvZmlsZU5hbWUiOiJHb21tZUhEIiwiaXNQdWJsaWMiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84NjNiZGFhMWVhOTNiM2Q5YTM0YzZjMzEyZjdjNTc5Mzg1Mjk5ZDMyZGNhOTJmOWNhNGY2MzIyYTU1NTlkNCJ9LCJDQVBFIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTVhMmQyZDk0OTQyOTY2Zjc0M2I4NGU0YzI2MjYzMTk3ODI1Mzk3OWRiNjczYzJmYmNjMjdkYzNkMmRjYzdhNyJ9fX0=","OIjd9kS5gU/2hCOUvF11SjyJ9+woqUNRd2hPSPQX7ulDQBkhfkNYl8H0jhJsx1nBt83mKUe43qEqfm/NPwXzcfM3tWG/Clw9wD1aC/KkzdFBfeHDZRwO7MM3TrmlBre1fuFXHy6Hf0Aav9DSoREoZT8KUfBJb3BbHggYVOthPxqW+7CmQIasgZT7G6iI9OnnBOatHvv4VTnEDWBMJh1c3rsniWrGV0JfPAoiu8L3heAzauLRWERT61v7+knIOQU0zHdWwBtPgpX12lxpfop63LbZLuqAp31NGg7gxsxvNZHYfi9F7dL894EF/W5wdtxYzcUpoZ1KqOSPfngw/29L3/EGgfvj7x+hm4fpmAQ7yQLWN8xTfhNcONapIGGpm9Wpy+Ae817uXBvNH03HVTvkHMJ77OzjD+O/glO/DEkvB2M7GRVFlQvh2NSInOGoGyxeFE/ANAUphh2tjatuEgmJqvNMXamX9u5q+Q34NQSjck6iXyJqGr1G/wNopcM4vjahHpfIQtzACntAk8UnL96k2K7juTxL/y5pTBVgHdd8Jcfra01nldtlepZOaWeqO/JR2DaYLNn9O6kIxH2AHHMrzjB+YOltFhRwiW/9PL2gUDF8bwXObJ6UmqHa4yk55RkF0AB4QUTAFaV1SBlj3npBdjr2svKsh46OEluMQAOAvZ4=");

        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();

        ReflectionUtils.setObject(packet.getClass(),"a",packet,id);
        ReflectionUtils.setObject(packet.getClass(),"b",packet,uuid);

        ReflectionUtils.setObject(packet.getClass(),"c",packet,(int)(loc.getX() * 32.0D));
        ReflectionUtils.setObject(packet.getClass(),"d",packet,(int)(loc.getY() * 32.0D));
        ReflectionUtils.setObject(packet.getClass(),"e",packet,(int)(loc.getZ() * 32.0D));

        ReflectionUtils.setObject(packet.getClass(),"f",packet,(byte)((int)loc.getYaw() * 256.0F/360.0F));
        ReflectionUtils.setObject(packet.getClass(),"g", packet, (byte) ((int) loc.getPitch() * 256.0F / 360.0F));

        ReflectionUtils.setObject(packet.getClass(), "h", packet, 58);


        DataWatcher dataWatcher = new DataWatcher(null);
        dataWatcher.a(6, 20F);
        dataWatcher.a(10, (byte) 127);


        ReflectionUtils.setObject(packet.getClass(),"i",packet,dataWatcher);

        sendPacket(packet,player);

        // Head Yaw
        PacketPlayOutEntityHeadRotation headPacket = new PacketPlayOutEntityHeadRotation();

        ReflectionUtils.setObject(headPacket.getClass(),"a",headPacket,id);
        ReflectionUtils.setObject(headPacket.getClass(),"b",headPacket,(byte)((int)loc.getYaw() * 256.0F/360.0F));
        sendPacket(headPacket,player);

    }

    public void destroy()
    {
        removeFromTabList();

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy();
        ReflectionUtils.setObject(packet.getClass(), "a", packet, new int[]{id});

        sendPacket(packet, player);

        uninjectPlayer(player);
    }

    private void addToTabList(String value, String signature) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();

        ReflectionUtils.setObject(packet.getClass(), "a", packet, EnumPlayerInfoAction.ADD_PLAYER);
        GameProfile profile = new GameProfile(uuid,name);

        // change property to name value signature
        profile.getProperties().put("textures", new Property("textures", value, signature));

        ReflectionUtils.setObject(packet.getClass(), "b", packet, Arrays.asList(packet.new PlayerInfoData(profile, 0, WorldSettings.EnumGamemode.NOT_SET, IChatBaseComponent.ChatSerializer.a(name))));

        sendPacket(packet,player);

    }

    private void removeFromTabList()
    {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();

        ReflectionUtils.setObject(packet.getClass(), "a", packet, EnumPlayerInfoAction.ADD_PLAYER);
        ReflectionUtils.setObject(packet.getClass(),"b",packet,Arrays.asList(packet.new PlayerInfoData(new GameProfile(uuid,name),0, WorldSettings.EnumGamemode.NOT_SET, IChatBaseComponent.ChatSerializer.a(name))));

        sendPacket(packet,player);

    }

    private void sendPacket(Packet p)
    {
        for(Player all : Bukkit.getOnlinePlayers())
        {
            ((CraftPlayer)all).getHandle().playerConnection.sendPacket(p);
        }
    }

    private void sendPacket(Packet p,Player player)
    {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(p);
    }

    private void injectPlayer()
    {
        for(Player all : Bukkit.getOnlinePlayers())
        {
            NettyInjectorHandler.injectChannel(all, this);
        }
    }

    private void injectPlayer(Player p)
    {
        NettyInjectorHandler.injectChannel(p, this);
    }

    private void uninjectPlayer()
    {
        for(Player all : Bukkit.getOnlinePlayers())
        {
            NettyInjectorHandler.unject(all);
        }
    }

    private void uninjectPlayer(Player p)
    {
        NettyInjectorHandler.unject(p);
    }

    public Packet packetIn(Packet packet,Player p) {
        if(packet instanceof PacketPlayInUseEntity)
        {
            PacketPlayInUseEntity entityPacket = (PacketPlayInUseEntity)packet;
            int targetId = (Integer) ReflectionUtils.getObject(entityPacket.getClass(),"a",entityPacket);
            PacketPlayInUseEntity.EnumEntityUseAction action = (PacketPlayInUseEntity.EnumEntityUseAction) ReflectionUtils.getObject(entityPacket.getClass(), "action", entityPacket);

            if(action == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK)

            {
                if(targetId == id)
                {
                    if(p.getInventory().getItemInHand().getTypeId() == CraftRoundTimer.getCurItem())
                    {
                        if(p.getInventory().getItemInHand().hasItemMeta()) {
                            if (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains("Crafted")) {
                                Bukkit.broadcastMessage(Settings.PREFIX + "§7The winner of this round is §b" + p.getName());
                                CraftRoundTimer.getInstance().restartTimer();

                                p.getInventory().setItem(p.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                                GameManager.addItem(p.getName(),new ItemStack(Material.getMaterial(GearItems.getRandomItemID())));

                                return packet;
                            }
                        }
                    }

                    p.sendMessage(Settings.ERROR+"§cFalse Item!");
                }
            }
        }
        return packet;
    }

    public Packet packetOut(Packet packet,Player p) {
        return packet;
    }
}
