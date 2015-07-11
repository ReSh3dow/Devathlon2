package net.sh3dow.craftbattle.manager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sh3dow on 11.07.2015.
 */
public final class GameManager {

    private static ArrayList<String> alive = new ArrayList<String>();

    private static HashMap<String,String> items = new HashMap<String,String>();

    public static void addPlayer(String name)
    {
        if(alive.contains(name))return;
        alive.add(name);
    }

    public static void removePlayer(String name)
    {
        if(!alive.contains(name))return;
        alive.remove(name);
    }

    public static int getSize()
    {
        return alive.size();
    }

    public static String getLastPlayer()
    {
        if(alive.size() >= 1)
        {
            return alive.get(0);
        }

        return null;
    }

    public static boolean isAlive(String name)
    {
        return alive.contains(name);
    }

    public static void addItem(String name, ItemStack item)
    {
        if(!items.containsKey(name)) {
            items.put(name, item.getType().toString());
            return;
        }

        String itemsString = items.get(name+";"+item.getType().toString());
        items.remove(name);
        items.put(name,itemsString);
    }

    public static ArrayList<ItemStack> getItems(String name)
    {
        ArrayList<ItemStack> curItems = new ArrayList<ItemStack>();

        if(!items.containsKey(name))
        {

            curItems.add(new ItemStack(Material.WOOD_SWORD));
            return curItems;
        }else
        {
            String[] arr = items.get(name).split(";");
            for(String s : arr)
            {
                curItems.add(new ItemStack(Material.getMaterial(s)));
            }
        }


        return curItems;
    }
}
