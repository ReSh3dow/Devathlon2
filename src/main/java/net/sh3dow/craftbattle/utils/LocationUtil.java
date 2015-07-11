package net.sh3dow.craftbattle.utils;

import net.sh3dow.craftbattle.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public final class LocationUtil {

    private static File f = new File("plugins"+File.separator+Settings.NAME+File.separator+"locations.yml");
    private static FileConfiguration conf = YamlConfiguration.loadConfiguration(f);

    /**
     * returns the callback which contains the location | using an external thread to improve the performance and against laggs
     * @param key key to find the location
     * @param iCallback an interface which gives the callback over external threads
     * @return the callback
     */
    public static ICallback<Location> getLocation(final String key, final ICallback<Location> iCallback)
    {
        new Thread(new Runnable() {
            public void run() {
                String worldName = conf.getString(key+".world");

                double x = conf.getDouble(key + ".x");
                double y = conf.getDouble(key + ".y");
                double z = conf.getDouble(key + ".z");

                float yaw = (float)conf.getDouble(key+".yaw");
                float pitch = (float)conf.getDouble(key+".pitch");

                if(worldName == null || worldName.equalsIgnoreCase(""))
                {
                    Main.getPluginLogger().log(Level.WARNING,"First you have to set the location: "+key.toUpperCase());
                    return;
                }

                Location resultLoc = new Location(Bukkit.getWorld(worldName),x,y,z,yaw,pitch);

                iCallback.set(resultLoc);
            }
        }).start();

        return iCallback;
    }

    /**
     * saving the location in a config file | using an external thread to improve the performance and against laggs
     * @param loc location to save
     * @param key to find the location later
     */
    public static void setLocation(final Location loc, final String key)
    {
        new Thread(new Runnable() {
            public void run() {
                String worldName = loc.getWorld().getName();

                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();

                float yaw = (float)loc.getYaw();
                float pitch = (float)loc.getPitch();

                conf.set(key+".world",worldName);
                conf.set(key+".x",x);
                conf.set(key+".y",y);
                conf.set(key+".z",z);

                conf.set(key+".yaw",yaw);
                conf.set(key+".pitch",pitch);

                try {
                    conf.save(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
