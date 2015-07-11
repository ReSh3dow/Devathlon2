package net.sh3dow.craftbattle.items.craftitems;

import java.util.Random;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public enum CraftableItems {
    DIAMOND_SWORD(276),
    IRON_SWORD(267),
    BREAD(297),
    GOLDEN_APPLE(322),
    IRON_AXE(258),
    BOOK(340),
    COMPASS(345),
    TNT(46),
    STICK(280);


    private int itemID;

    private CraftableItems(int id)
    {
        this.itemID = id;
    }

    private int getID()
    {
        return itemID;
    }

    public static int getRandomItemID()
    {
        int r = new Random().nextInt(CraftableItems.values().length-1);
        return CraftableItems.values()[r].getID();
    }

}
