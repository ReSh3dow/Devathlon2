package net.sh3dow.craftbattle.items.gearitems;

import java.util.Random;

/**
 * Created by Sh3dow on 11.07.2015.
 */
public enum GearItems {
    IRON_SWORD(267),
    BREAD(297),
    GOLDEN_APPLE(322),
    IRON_AXE(258),
    IRON_CHESTPLATE(307);

    private int itemID;

    private GearItems(int id)
    {
        this.itemID = id;
    }

    private int getID()
    {
        return itemID;
    }

    public static int getRandomItemID()
    {
        int r = new Random().nextInt(GearItems.values().length-1);
        return GearItems.values()[r].getID();
    }

}
