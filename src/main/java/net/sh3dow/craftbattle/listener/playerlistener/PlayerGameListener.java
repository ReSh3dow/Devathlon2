package net.sh3dow.craftbattle.listener.playerlistener;

import net.sh3dow.craftbattle.Main;
import net.sh3dow.craftbattle.entities.CustomCrafter;
import net.sh3dow.craftbattle.listener.MasterListener;
import net.sh3dow.craftbattle.manager.GameManager;
import net.sh3dow.craftbattle.manager.GameState;
import net.sh3dow.craftbattle.runnable.CraftRoundTimer;
import net.sh3dow.craftbattle.runnable.GameOverTimer;
import net.sh3dow.craftbattle.utils.ICallback;
import net.sh3dow.craftbattle.utils.LocationUtil;
import net.sh3dow.craftbattle.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public class PlayerGameListener extends MasterListener{

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e)
    {
        if(Main.getInstance().getState() != GameState.DM)e.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        final Player p = e.getPlayer();

        // Das man sich nur auf Diamond-Blöcken bewegen kann
        if(Main.getInstance().getState() == GameState.WAITING)
        {
            Location loc = e.getPlayer().getLocation();
            loc.setY(loc.getY() -1);
            if(loc.getBlock().getType() != Material.DIAMOND_BLOCK){
                //p.teleport(e.getFrom());

                LocationUtil.getLocation("gamespawn", new ICallback<Location>() {
                    public void set(Location obj, Object... objs) {
                        Vector res = new Vector(obj.getX()-p.getLocation().getX(),obj.getY()-p.getLocation().getY(),obj.getZ()-p.getLocation().getZ());
                        p.setVelocity(res.multiply(0.2D));
                    }
                });
            }
        }

        if(Main.getInstance().getState() == GameState.CRAFTING)
        {
            Location loc = e.getPlayer().getLocation();
            loc.setY(loc.getY() -1);
            if(loc.getBlock().getType() != Material.WORKBENCH){
                if(p.getGameMode() == GameMode.CREATIVE)return;
                p.setGameMode(GameMode.CREATIVE);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
    }

    @EventHandler
    public void onCraft(CraftItemEvent e)
    {
        // Import anti cheat if there is time :)


        ItemStack i = e.getInventory().getResult();

        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName("§cCrafted Item");
        meta.spigot().setUnbreakable(true);
        meta.setLore(Arrays.asList("§bby "+e.getWhoClicked().getName()));

        i.setItemMeta(meta);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        if(Main.getInstance().getState() == GameState.DM)
        {
            Player p = e.getEntity();
            if(p.getKiller() != null)
            {
                Player k = p.getKiller();
                e.setDeathMessage(Settings.PREFIX + "§7" + p.getName() + " §bwas killed by §c" + k.getName());

                // stats
                p.setHealth(20);
                for(Player all : Bukkit.getOnlinePlayers())
                {
                    all.hidePlayer(p);
                }

                p.setVelocity(new Vector(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()).setY(8).multiply(5));
                p.setFlying(true);
                p.setAllowFlight(true);

                GameManager.removePlayer(p.getName());

                if(GameManager.getSize() == 1)
                {
                    Main.getInstance().setState(GameState.OVER);

                    new GameOverTimer(15,"§aThe server shuts down in §a%time%",Bukkit.getPlayer(GameManager.getLastPlayer())).start();
                }
            }
        }
    }

}
